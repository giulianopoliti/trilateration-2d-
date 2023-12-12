package com.example.apirest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

@Getter
@Setter
@Component
public class TelescopeHandler {
    private List<Telescope> telescopes;
    private Exoplanet exoplanet;

    public TelescopeHandler(List<Telescope> telescopes, Exoplanet exoplanet) {
        this.telescopes = telescopes;
        this.exoplanet = createDefaultExoplanet();
    }
    public Map<String, Object> putData (Exoplanet exoplanet){
        Map<String, Object> result = new HashMap<>();
        result.put("name", exoplanet.getName());
        result.put("x", exoplanet.getLocation().getX());
        result.put("y", exoplanet.getLocation().getY());
        if (exoplanet.getMessage() == null){
            result.put("message:", "");
        }else {
            result.put("message:", exoplanet.getMessage());
        }
        result.put("radius", exoplanet.getRadius());
        result.put("weight", exoplanet.getWeight());
        return result;
    }

    public Exoplanet createDefaultExoplanet (){
        Point defaultLocation = new Point(0, 0);
        return exoplanet.getExoplanet(defaultLocation);
    }

    public void updateExoplanet(Exoplanet exoplanet, ExoplanetUpdate updateRequest) {
        if (exoplanet != null) {
            exoplanet.setName(updateRequest.getNewName());
            exoplanet.setRadius(updateRequest.getNewRadius());
            exoplanet.setWeight(updateRequest.getNewWeight());
        } else {
            throw new IllegalArgumentException("El exoplaneta no ha sido inicializado.");
        }
    }


    public String [] phaseShift (String [] message, int size){
        if (message.length > size && message[0].isEmpty()){
            String [] aux = new String[size];
            for (int i = 1; i < message.length; i++){
                aux [i-1] = message[i];
            }return aux;
        } return message;
    }

    public String[] compareMessage(List<Telescope> telescopes) {
        int size = setSize(telescopes);
        String[] messageFinal = new String[size];

        for (Telescope telescope : telescopes) {
            String[] message = phaseShift(telescope.getMessage(), size);

            for (int i = 0; i < size && i < message.length; i++) {
                if (!message[i].isEmpty()) {
                    messageFinal[i] = message[i];
                }
            }
        }

        return messageFinal;
    }


    public int setSize(List<Telescope> telescopes) {
        int size = 0;
        for (Telescope telescope : telescopes) {
            int length = telescope.getMessage().length;
            size += length;
        }
        return size / 3;
    }


    public Point[] findCircleIntersection(double x1, double y1, double distance1, double x2, double y2, double distance2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));

        if (distance > distance1 + distance2 || distance < Math.abs(distance1 - distance2)) {
            // Si los círculos no se chocan o están uno dentro de otro, devolvemos que no hay intersección
            return new Point[0];
        } else if (distance == 0 && distance1 == distance2) {
            // Si los círculos son idénticos, se devuelve un array con un punto en el perímetro del círculo
            return new Point[]{new Point(x1 + distance1, y1)};
        } else {
            // Intersección
            double a = (Math.pow(distance1, 2) - Math.pow(distance2, 2) + Math.pow(distance, 2)) / (2 * distance);
            double h = Math.sqrt(Math.pow(distance1, 2) - Math.pow(a, 2));
            double x3 = x1 + a * (x2 - x1) / distance;
            double y3 = y1 + a * (y2 - y1) / distance;

            // Simplificación de las fórmulas para mejorar la legibilidad
            double factor = h / distance;
            double x4 = x3 + factor * (y2 - y1);
            double y4 = y3 - factor * (x2 - x1);

            Point intersection1 = new Point(x4, y4);
            Point intersection2 = new Point(x3 - factor * (y2 - y1), y3 + factor * (x2 - x1));

            return new Point[]{intersection1, intersection2};
        }
    }



    public Exoplanet trilateration(Point point1, double distance1,
                                    Point point2, double distance2,
                                    Point point3, double distance3) {
        double x1 = point1.getX();
        double y1 = point1.getY();

        double x2 = point2.getX();
        double y2 = point2.getY();

        double x3 = point3.getX();
        double y3 = point3.getY();
        Point[] intersections1 = findCircleIntersection(x1,y1,distance1,x2,y2,distance2);
        // Verificar intersección de círculos
        if (intersections1.length == 0) {
            throw new IllegalArgumentException("Los círculos de los primeros dos satélites no se intersectan.");
        }

        Point[] intersections2 = findCircleIntersection(x1,y1,distance1,x3,y3,distance3);
        if (intersections2.length == 0) {
            throw new IllegalArgumentException("Los círculos del primer y tercer satélite no se intersectan.");
        }

        Point[] intersections3 = findCircleIntersection(x2, y2, distance2,
                x3, y3, distance3);
        if (intersections3.length == 0) {
            throw new IllegalArgumentException("Los círculos del segundo y tercer satélite no se intersectan.");
        }




        // Obtener las posiciones de los satélites
        // Calcular los coeficientes del sistema de ecuaciones
        double a = 2 * (x2 - x1);
        double b = 2 * (y2 - y1);
        double c = distance1 * distance1 - distance2 * distance2 - x1 * x1 + x2 * x2 - y1 * y1 + y2 * y2;

        double d = 2 * (x3 - x2);
        double e = 2 * (y3 - y2);
        double f = distance2 * distance2 - distance3 * distance3 - x2 * x2 + x3 * x3 - y2 * y2 + y3 * y3;

        // Resolver el sistema de ecuaciones
        double exoplanetX = (c * e - f * b) / (e * a - b * d);
        double exoplanetY = (c * d - f * a) / (b * d - a * e);
        Point locationExoplanet = new Point(exoplanetX,exoplanetY);
        this.exoplanet.setLocation(locationExoplanet);
        return exoplanet;
    }

}
