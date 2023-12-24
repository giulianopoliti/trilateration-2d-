# trilateration-2d-
Java trilateration project using satellites made with spring boot

Este proyecto, desarrollado en Java con Spring Boot, proporciona un sistema de seguimiento de un exoplaneta mediante la recepción de señales de tres satélites. La información de las señales se envía al método topSecret a través del endpoint /topsecret en formato JSON. A continuación, se presenta un ejemplo de la solicitud: POST /topsecret { "telescopes": [ { "name": "Mandalorian", "location": {"x": 0, "y": 0}, "distance": 5, "message": ["Hola", "como", ""] }, { "name": "Obi Wan Kenobi", "location": {"x": 0, "y": 6}, "distance": 5, "message": ["", "", "como", "estas"] }, { "name": "Chewbacca", "location": {"x": 8, "y": 0}, "distance": 8, "message": ["", "", "estas"] } ] } Este método utiliza el algoritmo de trilateración junto con las ubicaciones y distancias proporcionadas por cada satélite para determinar la ubicación del exoplaneta. El exoplaneta encontrado tiene un nombre por defecto, "Pluton", así como un radio, un peso y un conjunto de mensajes. La respuesta tiene el siguiente formato: { "message": [ "Hola", "como", "estas" ], "name": "Pluton", "x": 1.5625, "y": 3.0, "weight": 100.0, "radius": 10.0 } La información no esencial, como el nombre, peso y radio, puede actualizarse mediante el método updateExoplanet y el endpoint /update.

Para visualizar la información actualizada localmente, puede llamarse al método con el endpoint /get. Si topSecret no se ha ejecutado previamente, los valores predeterminados se devolverán: { "message": "", "name": "Pluton", "x": 0.0, "y": 0.0, "weight": 100.0, "radius": 10.0 }

This project, developed in Java with Spring Boot, provides a system for tracking an exoplanet by receiving signals from three satellites. Token information is sent to the topSecret method via the /topsecret endpoint in JSON format. Here is an example of the request: POST /topsecret { "telescopes": [ { "name": "Mandalorian", "location": {"x": 0, "y": 0}, "distance" :5, "message": ["Hello", "like", ""] }, { "name": "Obi Wan Kenobi", "location": {"x": 0, "y": 6}, "distance": 5, "message": ["", "", "how", "are you"] }, { "name": "Chewbacca", "location": {"x": 8, "y" : 0}, "distance": 8, "message": ["", "", "these"] } ] } This method uses the trilateration algorithm along with the locations and distances provided by each satellite to determine the location of the exoplanet. The exoplanet found has a default name, "Pluto", as well as a radio, a weight and a set of messages. The response has the following format: { "message": [ "Hello", "how", "are you" ], "name": "Pluton", "x": 1.5625, "y": 3.0, "weight": 100.0, "radius": 10.0 } Non-essential information, such as name, weight, and radius, can be updated using the updateExoplanet method and the /update endpoint.

To display the updated information locally, the method can be called with the /get endpoint. If topSecret has not been previously run, the default values will be returned: { "message": "", "name": "Pluton", "x": 0.0, "y": 0.0, "weight": 100.0, "radius ": 10.0 }
