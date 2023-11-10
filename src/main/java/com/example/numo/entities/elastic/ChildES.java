package com.example.numo.entities.elastic;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ChildES {
    Long id;
    Long age;
    Boolean is_preschool;
    String name;
    String preschool_status;
}
