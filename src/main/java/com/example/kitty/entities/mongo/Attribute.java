package com.example.kitty.entities.mongo;

import com.example.kitty.entities.enums.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {
    private AttributeType attributeType;
}
