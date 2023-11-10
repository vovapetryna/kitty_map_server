package com.example.kitty.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class LatLong {
    private Double latitude;
    private Double longitude;
}
