package com.example.kitty.entities.mongo;

import com.example.kitty.entities.enums.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Attribute {
    private AttributeType attributeType;
}
