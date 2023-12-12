package com.example.apirest.controller;
import com.example.apirest.model.*;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;

@RestController
@RequestMapping("/exoplanet")
public class TelescopeController {
    @Autowired
    private TelescopeHandler telescopeHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(TelescopeController.class);
    @PatchMapping("/update")
    public ResponseEntity<String> updateExoplanet(@RequestBody ExoplanetUpdate updateRequest) {
        try {
            // Validar los datos antes de la actualización
            if (updateRequest.getNewName() == null || updateRequest.getNewName().isEmpty()) {
                throw new IllegalArgumentException("El nuevo nombre no puede estar vacío.");
            }

            if (updateRequest.getNewRadius() == null) {
                throw new IllegalArgumentException("El nuevo radio no puede ser nulo.");
            }

            if (updateRequest.getNewWeight() == null) {
                throw new IllegalArgumentException("El nuevo peso no puede ser nulo.");
            }

            // Obtener el exoplaneta actual
            Exoplanet exoplanet = telescopeHandler.getExoplanet();

            // Actualizar el exoplaneta
            telescopeHandler.updateExoplanet(exoplanet, updateRequest);

            return ResponseEntity.ok("Exoplaneta actualizado correctamente.");
        } catch (IllegalArgumentException e) {
            // Manejamos errores de validación
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Manejamos otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }


    //este metodo no recibe nada
    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getExoplanet() {
        try {
            // obtenemos el exoplaneta
            Exoplanet exoplanet = telescopeHandler.getExoplanet();
            //formulamos la respuesta a brindar, hay que ejecutarla luego del post.
            // si no vamos a tener el exoplaneta por default sin ubicaciones de x o de y
            return ResponseEntity.ok(telescopeHandler.putData(exoplanet));
        } catch (IllegalArgumentException e) {
            // Maneja excepciones si los círculos no se intersectan
            return ResponseEntity.notFound().build();
        }
    }
    //este metodo saca
    @PostMapping("/topsecret")
    public ResponseEntity<Map<String, Object>> topSecret(@RequestBody JsonNode requestData) {
        try {
            JsonNode telescopesNode = requestData.get("telescopes");
            List<Telescope> telescopes = new ArrayList<>();
            logger.info("Solicitud recibida POST: {}", requestData);

            if (telescopesNode != null && telescopesNode.isArray()) {
                for (int i = 0; i < telescopesNode.size(); i++) {
                    JsonNode telescopeNode = telescopesNode.get(i);
                    String name = telescopeNode.get("name").asText();
                    JsonNode locationNode = telescopeNode.get("location");
                    double x = locationNode.get("x").asDouble();
                    double y = locationNode.get("y").asDouble();
                    Point location = new Point(x, y);
                    double distance = telescopeNode.get("distance").asDouble();
                    String[] message = objectMapper.convertValue(telescopeNode.get("message"), String[].class);
                    Telescope telescope = new Telescope(name, location, distance, message);
                    logger.info("Valores - Nombre: {}, Ubicación: ({}, {}), Distancia: {}, Mensaje: {}", name, x, y, distance, Arrays.toString(message));
                    telescopes.add(telescope);
                }
            }

            if (telescopes.size() >= 3) {
                Telescope telescope1 = telescopes.get(0);
                Telescope telescope2 = telescopes.get(1);
                Telescope telescope3 = telescopes.get(2);
                Exoplanet exoplanet = telescopeHandler.trilateration(
                        telescope1.getLocation(), telescope1.getDistance(),
                        telescope2.getLocation(), telescope2.getDistance(),
                        telescope3.getLocation(), telescope3.getDistance());
                String [] messageFinal = telescopeHandler.compareMessage(telescopes);
                exoplanet.setMessage(messageFinal);
                logger.info("Ubicacion del exoplaneta: ({}, {})", exoplanet.getLocation().getX(), exoplanet.getLocation().getY());
                return ResponseEntity.ok(telescopeHandler.putData(exoplanet));
            } else {
                logger.error("Se necesitan al menos 3 telescopios para realizar la trilateración.");
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            logger.error("Error durante el procesamiento de la solicitud: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
