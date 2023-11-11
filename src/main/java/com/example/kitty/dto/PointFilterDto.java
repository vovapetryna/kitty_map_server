package com.example.kitty.dto;

import com.example.kitty.entities.enums.Category;
import com.example.kitty.entities.mongo.Attribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointFilterDto {
    private List<Category> categories;
    private List<Attribute> attributes;
}
