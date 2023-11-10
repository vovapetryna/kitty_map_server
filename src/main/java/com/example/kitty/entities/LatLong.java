package com.example.kitty.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(chain = true)
@Data
public class LatLong {
    private Double latitude;
    private Double longitude;
}
