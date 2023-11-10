package com.example.kitty.entities.mongo;

import com.example.kitty.entities.enums.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("points")
public class Point {
    @Id
    private String id;
    private String name;
    private Category category;

    public Point(String id, String name, Category category) {
        super();
        this.id = id;
        this.name = name;
        this.category = category;
    }
}