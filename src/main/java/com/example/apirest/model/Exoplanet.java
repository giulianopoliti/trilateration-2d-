package com.example.apirest.model;

import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Scope("singleton")
@Component
public class Exoplanet {
    private static Exoplanet exoplanet;
    private Point location;
    private String name;
    private double radius;
    private double weight;
    private final String defaultName = "Pluton";
    private final double defaultRadius = 10.0;
    private final double defaultWeight = 100.0;
    private String [] message;
    private Exoplanet(Point location) {
        this.location = location;
        this.name = defaultName;
        this.radius = defaultRadius;
        this.weight = defaultWeight;
    }

    public static Exoplanet getExoplanet(Point location) {
        if (exoplanet == null) {
            exoplanet = new Exoplanet(location);
        }
        return exoplanet;
    }

    public String[] getMessage() {
        return message;
    }
    // Método público para obtener la instancia única

    public void setMessage(String[] message) {
        this.message = message;
    }



    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
