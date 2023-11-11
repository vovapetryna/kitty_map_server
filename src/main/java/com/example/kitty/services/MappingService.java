package com.example.kitty.services;

import com.example.kitty.entities.enums.AttributeType;
import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.example.kitty.entities.mongo.Point;
import com.example.kitty.repositories.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MappingService {

    private final PointRepository pointRepository;
    private final Map<String, Category> fromAmenityMap = Map.ofEntries(
            Map.entry("shop", Category.shop),
            Map.entry("cafe", Category.food),
            Map.entry("pharmacy", Category.pharmacy),
            Map.entry("atm", Category.finances),
            Map.entry("bank", Category.finances),
            Map.entry("restaurant", Category.food),
            Map.entry("post_office", Category.post),
            Map.entry("dentist", Category.hospital),
            Map.entry("parking", Category.parking),
            Map.entry("toilets", Category.toilets),
            Map.entry("payment_terminal", Category.finances),
            Map.entry("clinic", Category.hospital),
            Map.entry("bar", Category.food),
            Map.entry("post_box", Category.post),
            Map.entry("shelter", Category.shelter),
            Map.entry("pub", Category.food),
            Map.entry("parking_entrance", Category.parking),
            Map.entry("library", Category.education),
            Map.entry("doctors", Category.hospital),
            Map.entry("veterinary", Category.hospital),
            Map.entry("school", Category.education),
            Map.entry("kindergarten", Category.education),
            Map.entry("police", Category.government),
            Map.entry("community_centre", Category.spirit),
            Map.entry("language_school", Category.education),
            Map.entry("cinema", Category.entertainment),
            Map.entry("theatre", Category.entertainment),
            Map.entry("nightclub", Category.entertainment),
            Map.entry("driving_school", Category.education),
            Map.entry("arts_centre", Category.entertainment),
            Map.entry("hospital", Category.hospital),
            Map.entry("university", Category.education),
            Map.entry("courthouse", Category.government),
            Map.entry("college", Category.education),
            Map.entry("hookah_lounge", Category.entertainment),
            Map.entry("prep_school", Category.education),
            Map.entry("music_school", Category.education));

    public void updatePointsFeatures() {
        pointRepository.findAll().parallelStream().forEach(point -> {
            System.out.println("Updating point");
            pointRepository.save(mapInternalPointParameters(point));
        });
    }

    private Point mapInternalPointParameters(Point point) {
        point.setCategory(getCategoryFromPoint(point));
        point.setAttributes(getAttributesFromPoint(point));
        point.setSubCategory(point.getAmenity());
        return point;
    }

    private Category getCategoryFromPoint(Point point) {
        if (point.getBus_stop()) {
            return Category.busStop;
        }

        return fromAmenityMap.getOrDefault(
                Optional.ofNullable(point.getAmenity()).orElse(""),
                Category.unknown
        );
    }

    private List<Attribute> getAttributesFromPoint(Point point) {
        List<Attribute> attributes = new ArrayList<>();

        getWheelchairAttribute(point)
                .ifPresent(attributes::add);

        return attributes;
    }

    private Optional<Attribute> getWheelchairAttribute(Point point) {
        return Optional.ofNullable(point.getWheelchair())
                .filter(w -> !w.isBlank())
                .map(w -> switch (w) {
                    case "yes", "limited" -> AttributeType.wheelchairYes;
                    case "no" -> AttributeType.wheelchairNo;
                    case "assisted" -> AttributeType.wheelchairAssistance;
                    default -> AttributeType.unknown;
                })
                .filter(attr -> attr != AttributeType.unknown)
                .map(Attribute::new);
    }
}
