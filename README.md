# kitty_map_server

# convert pdf to osm
```shell
osmium cat src/main/resources/kiev.pbf -o src/main/resources/kiev.osm 
```

# create DB with some tools like pg admin etc.

# setup db
```shell
psql routing -c 'CREATE EXTENSION PostGIS'
psql routing -c 'CREATE EXTENSION pgRouting'
```

# import graph data to the DB
```shell
osm2pgrouting --f src/main/resources/kiev.osm --conf src/main/resources/mapcfg.xml --dbname kitty --username petryna --clean
```
