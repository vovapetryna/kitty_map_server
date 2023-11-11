package com.example.kitty.services;

import com.example.kitty.entities.enums.AttributeType;
import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.example.kitty.entities.mongo.RawPoint;
import com.example.kitty.repositories.PointRepository;
import com.example.kitty.repositories.RawPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RawPointMappingService {

    private final RawPointRepository rawPointRepository;

    private final PointRepository pointRepository;

    private final Map<String, Category> fromAICategory = Map.ofEntries(
            Map.entry("food", Category.food),
            Map.entry("education", Category.education),
            Map.entry("shop", Category.shop),
            Map.entry("finances", Category.finances),
            Map.entry("post", Category.post),
            Map.entry("barrier-free-infrastructure", Category.infrastructure),
            Map.entry("barrier-free-buildings", Category.infrastructure),
            Map.entry("shelter", Category.shelter),
            Map.entry("beauty", Category.entertainment),
            Map.entry("barrier-free-parks", Category.parks),
            Map.entry("health", Category.hospital),
            Map.entry("government", Category.government),
            Map.entry("pharmacy", Category.pharmacy),
            Map.entry("barrier-free-education", Category.education),
            Map.entry("hospital", Category.hospital),
            Map.entry("entertainment", Category.entertainment),
            Map.entry("barrier-free-transport", Category.busStop),
            Map.entry("infrastructure", Category.infrastructure),
            Map.entry("subway", Category.busStop),
            Map.entry("parking", Category.parking)
    );

    private final Map<String, AttributeType> fromAIAttribute = Map.ofEntries(
            Map.entry("workingWithoutLight", AttributeType.workingWithoutLight),
            Map.entry("parking", AttributeType.parking),
            Map.entry("ramp", AttributeType.ramp),
            Map.entry("wiFi", AttributeType.wiFi),
            Map.entry("cargoLift", AttributeType.cargoLift),
            Map.entry("soundSignalsOfTrafficLights", AttributeType.soundSignalsOfTrafficLights),
            Map.entry("shelter", AttributeType.shelter),
            Map.entry("sanitaryAndHygienicPremises", AttributeType.sanitaryAndHygienicPremises),
            Map.entry("generator", AttributeType.workingWithoutLight),
            Map.entry("tactileElementsOfAccessibility", AttributeType.tactileElementsOfAccessibility),
            Map.entry("childrenRoom", AttributeType.childrenRoom),
            Map.entry("visualElementsOfAccessibility", AttributeType.visualElementsOfAccessibility),
            Map.entry("obstacleMarking", AttributeType.obstacleMarking),
            Map.entry("tactileRoute", AttributeType.tactileRoute),
            Map.entry("accompanying", AttributeType.accompanying),
            Map.entry("adaptedNotificationSystems", AttributeType.adaptedNotificationSystems),
            Map.entry("roomForCare", AttributeType.roomForCare),
            Map.entry("freightElevator", AttributeType.freightElevator),
            Map.entry("liftingEquipment", AttributeType.liftingEquipment),
            Map.entry("wifi", AttributeType.wiFi),
            Map.entry("wheelchairAssistance", AttributeType.accompanying),
            Map.entry("signLanguageTranslation", AttributeType.signLanguageTranslation),
            Map.entry("device charging", AttributeType.workingWithoutLight),
            Map.entry("publicWiFi", AttributeType.wiFi),
            Map.entry("тактильна плитка", AttributeType.tactileElementsOfAccessibility),
            Map.entry("public WiFi during power outages", AttributeType.wiFi),
            Map.entry("понижений бордюр", AttributeType.tactileElementsOfAccessibility),
            Map.entry("WiFi", AttributeType.wiFi),
            Map.entry("taktilna plitka", AttributeType.tactileElementsOfAccessibility),
            Map.entry("Понижений бордюр", AttributeType.tactileElementsOfAccessibility),
            Map.entry("Тактильна плитка", AttributeType.tactileElementsOfAccessibility),
            Map.entry("accompanied", AttributeType.accompanying),
            Map.entry("public WiFi", AttributeType.wiFi)
    );

    public void updatePointsFeatures() {
        rawPointRepository.findAll().parallelStream().forEach(point -> {
            System.out.println("Updating point");

//            var nearestPoints = pointRepository.findNearest(point.getLocation().getX(), point.getLocation().getY());
//            if (nearestPoints.size() > 0) {
//                var nearestPoint = nearestPoints.get(0);
//                nearestPoint.setAttributes(nearestPoint.getAttributes().addAll(point.))
//            }

//            pointRepository.save(mapInternalPointParameters(point));
        });
    }

    public void groupPointFeatures() {
        rawPointRepository.findAll().parallelStream().forEach(point -> {
            point.setReadyCategory(getPointCategory(point));
            point.setFilteredAttributes(getFilteredCategories(point));
            point.setReadyAttributes(point.getFilteredAttributes().stream().map(Attribute::new).toList());
            rawPointRepository.save(point);
        });
    }

    private Category getPointCategory(RawPoint point) {
        return Optional.ofNullable(point.getCategory())
                .filter(c -> !c.isBlank())
                .map(c -> fromAICategory.getOrDefault(c, Category.unknown))
                .orElse(Category.unknown);
    }

    private List<AttributeType> getFilteredCategories(RawPoint point) {
        if (point.getAttributes() == null) {
            return List.of();
        }

        return point.getAttributes().stream()
                .map(attr -> fromAIAttribute.getOrDefault(attr, AttributeType.unknown))
                .filter(attr -> attr != AttributeType.unknown).toList();
    }

}
