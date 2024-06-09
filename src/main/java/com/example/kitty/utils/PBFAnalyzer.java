package com.example.kitty.utils;

import com.example.kitty.repositories.PgRoutingRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PBFAnalyzer {

    private final PgRoutingRepository pgRoutingRepository;

    public void init() {
//        pgRoutingRepository.reweigh();
    }
}
