package com.example.apirest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Getter
@Setter
public class ExoplanetUpdate {
    private String newName;
    private Double newRadius;
    private Double newWeight;
}
