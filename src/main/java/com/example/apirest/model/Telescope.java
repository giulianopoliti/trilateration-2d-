package com.example.apirest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@AllArgsConstructor
@Getter
@Setter
@Component
public class Telescope {
    private String name;
    private Point location;
    private double distance;
    private String [] message;

    public Telescope() {
        // Constructor por defecto
    }
}
