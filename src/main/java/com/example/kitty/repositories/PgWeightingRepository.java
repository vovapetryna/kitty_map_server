package com.example.kitty.repositories;

import com.example.kitty.entities.PgEdge;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
@Slf4j
public class PgWeightingRepository {
    private final EntityManager entityManager;
    private final EntityManagerFactory factory;

    public PgEdge applyReweighing(Long gId, Double multiplication, Boolean reverse) {
        var manager = factory.createEntityManager();
        var txn = manager.getTransaction();
        txn.begin();
        Query update = manager.createNativeQuery("UPDATE ways SET length = length * :w WHERE gid=:gid ");
        update.setParameter("w", reverse ? 1 / multiplication : multiplication);
        update.setParameter("gid", gId);
        update.executeUpdate();
        txn.commit();

        reweigh(Optional.of(gId), false);
        return getEdgeById(gId);
    }

    public void reweigh(Optional<Long> gid, Boolean doFull) {
        if (doFull) {
            reweighSingle(e -> {
                if (e.tagId == 100 || e.tagId == 106 || e.tagId == 108 || e.tagId == 110) {
                    log.info("reweighing gid: {} for tagId: {}, change length: {} by 10x", e.gid, e.tagId, e.length);
                    e.length *= 10;
                }
                return e;
            }, "ways_no_roads", gid);

            reweighSingle(e -> {
                if (e.tagId == 122) {
                    log.info("reweighing gid: {} for tagId: {}, change length: {} by 20x", e.gid, e.tagId, e.length);
                    e.length *= 20;
                }
                return e;
            }, "ways_no_steps", gid);

            reweighSingle(e -> {
                // dangerous crossing
                if (e.tagId == 107 || e.tagId == 124 || e.tagId == 125) {
                    log.info("reweighing gid: {} for tagId: {}, change length: {} by 20x", e.gid, e.tagId, e.length);
                    e.length *= 10;
                }

                // safe crossing
                if (e.tagId == 201) {
                    log.info("reweighing gid: {} for tagId: {}, change length: {} by 20x", e.gid, e.tagId, e.length);
                    e.length /= 10;
                }
                return e;
            }, "ways_no_dangerous_crossings", gid);

            reweighSingle(e -> {
                if (e.tagId == 114 || e.tagId == 115) {
                    log.info("reweighing gid: {} for tagId: {}, change length: {} by 20x", e.gid, e.tagId, e.length);
                    e.length /= 10;
                }

                return e;
            }, "ways_good_foot_walks", gid);

            reweighSingle(e -> {
                if (e.tagId == 118 || e.tagId == 119 || e.tagId == 120) {
                    log.info("reweighing gid: {} for tagId: {}, change length: {} by 20x", e.gid, e.tagId, e.length);
                    e.length *= 10;
                }

                return e;
            }, "ways_no_bad_foot_walks", gid);
        }

        reweighSingle(e -> {
            if (e.tagId == 100 || e.tagId == 106 || e.tagId == 108 || e.tagId == 110) {
                e.length *= 10;
            }
            if (e.tagId == 122) {
                e.length *= 20;
            }
            if (e.tagId == 107 || e.tagId == 124 || e.tagId == 125) {
                e.length *= 10;
            }
            if (e.tagId == 201) {
                e.length /= 10;
            }
            if (e.tagId == 114 || e.tagId == 115) {
                e.length /= 10;
            }
            if (e.tagId == 118 || e.tagId == 119 || e.tagId == 120) {
                e.length *= 10;
            }
            return e;
        }, "optimal_ways", gid);
    }

    private void reweighSingle(Function<PgEdge, PgEdge> transformer, String newTableName, Optional<Long> gid) {
        var manager = factory.createEntityManager();

        gid.ifPresentOrElse($ -> {
        }, () -> {
            var txn = manager.getTransaction();
            txn.begin();
            Query ddl = manager.createNativeQuery("CREATE TABLE " + newTableName + " (gid bigserial primary key, source bigint references ways_vertices_pgr, target bigint references ways_vertices_pgr, length double precision)");
            ddl.executeUpdate();
            txn.commit();
        });

        Query q = gid.map(edgeId -> {
            var t = entityManager.createNativeQuery("SELECT gid, tag_id, source, target, length, x1, y1, x2, y2 FROM ways WHERE gid=:edgeId");
            t.setParameter("edgeId", edgeId);
            return t;
        }).orElse(entityManager.createNativeQuery("SELECT gid, tag_id, source, target, length, x1, y1, x2, y2 FROM ways"));
        List<Object[]> results = q.getResultList();
        log.info("reweigh candidates: {}", results.size());

        results.forEach(r -> {
            var edge = transformer.apply(new PgEdge(r, 0));
            var iTxn = manager.getTransaction();
            iTxn.begin();
            Query insert = manager.createNativeQuery("INSERT INTO " + newTableName + " VALUES (:gid, :source, :target, :length) " +
                    "ON CONFLICT (gid) " +
                    "DO UPDATE SET length=:length");
            insert.setParameter("gid", edge.gid);
            insert.setParameter("source", edge.source);
            insert.setParameter("target", edge.target);
            insert.setParameter("length", edge.length);
            insert.executeUpdate();
            iTxn.commit();
        });

        log.info("done reweighing");
    }

    public PgEdge getEdgeById(Long gid) {
        Query q = entityManager.createNativeQuery("SELECT gid, tag_id, source, target, length, x1, y1, x2, y2 FROM ways WHERE gid=:gid");
        q.setParameter("gid", gid);
        List<Object[]> res = q.getResultList();
        return new PgEdge(res.get(0), 0);
    }
}
