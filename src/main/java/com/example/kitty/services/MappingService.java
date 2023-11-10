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
            Map.entry("shop", Category.SHOP),
            Map.entry("cafe", Category.FOOD),
            Map.entry("pharmacy", Category.PHARMACY),
            Map.entry("atm", Category.FINANCES),
            Map.entry("bank", Category.FINANCES),
            Map.entry("restaurant", Category.FOOD),
            Map.entry("post_office", Category.POST),
            Map.entry("dentist", Category.HOSPITAL),
            Map.entry("parking", Category.PARKING),
            Map.entry("toilets", Category.TOILETS),
            Map.entry("payment_terminal", Category.FINANCES),
            Map.entry("clinic", Category.HOSPITAL),
            Map.entry("bar", Category.FOOD),
            Map.entry("post_box", Category.POST),
            Map.entry("shelter", Category.SHELTER),
            Map.entry("pub", Category.FOOD),
            Map.entry("parking_entrance", Category.PARKING),
            Map.entry("library", Category.EDUCATION),
            Map.entry("doctors", Category.HOSPITAL),
            Map.entry("veterinary", Category.HOSPITAL),
            Map.entry("school", Category.EDUCATION),
            Map.entry("kindergarten", Category.EDUCATION),
            Map.entry("police", Category.GOVERNMENT),
            Map.entry("community_centre", Category.SPIRIT),
            Map.entry("language_school", Category.EDUCATION),
            Map.entry("cinema", Category.ENTERTAINMENT),
            Map.entry("theatre", Category.ENTERTAINMENT),
            Map.entry("nightclub", Category.ENTERTAINMENT),
            Map.entry("driving_school", Category.EDUCATION),
            Map.entry("arts_centre", Category.ENTERTAINMENT),
            Map.entry("hospital", Category.HOSPITAL),
            Map.entry("university", Category.EDUCATION),
            Map.entry("courthouse", Category.GOVERNMENT),
            Map.entry("college", Category.EDUCATION),
            Map.entry("hookah_lounge", Category.ENTERTAINMENT),
            Map.entry("prep_school", Category.EDUCATION),
            Map.entry("music_school", Category.EDUCATION));

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
            return Category.BUS_STOP;
        }

        return fromAmenityMap.getOrDefault(
                Optional.ofNullable(point.getAmenity()).orElse(""),
                Category.UNKNOWN
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
                    case "yes", "limited" -> AttributeType.WHEELCHAIR_YES;
                    case "no" -> AttributeType.WHEELCHAIR_NO;
                    case "assisted" -> AttributeType.WHEELCHAIR_ASSISTANCE;
                    default -> AttributeType.UNKNOWN;
                })
                .filter(attr -> attr != AttributeType.UNKNOWN)
                .map(Attribute::new);
    }
}
