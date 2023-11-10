package com.example.kitty.dto;

import com.example.kitty.entities.enums.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PointDto {
    private String id;
    private String name;
    private Category category;
}
