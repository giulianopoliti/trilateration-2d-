Este proyecto, desarrollado en Java con Spring Boot, proporciona un sistema de seguimiento de un exoplaneta mediante la recepción de señales de tres satélites. La información de las señales se envía al método topSecret a través del endpoint /topsecret en formato JSON. A continuación, se presenta un ejemplo de la solicitud:
POST /topsecret
{
"telescopes": [
{
"name": "Mandalorian",
"location": {"x": 0, "y": 0},
"distance": 5,
"message": ["Hola", "como", ""]
},
{
"name": "Obi Wan Kenobi",
"location": {"x": 0, "y": 6},
"distance": 5,
"message": ["", "", "como", "estas"]
},
{
"name": "Chewbacca",
"location": {"x": 8, "y": 0},
"distance": 8,
"message": ["", "", "estas"]
}
]
}
Este método utiliza el algoritmo de trilateración junto con las ubicaciones y distancias proporcionadas por cada satélite para determinar la ubicación del exoplaneta. El exoplaneta encontrado tiene un nombre por defecto, "Pluton", así como un radio, un peso y un conjunto de mensajes. La respuesta tiene el siguiente formato:
{
"message": [
"Hola",
"como",
"estas"
],
"name": "Pluton",
"x": 1.5625,
"y": 3.0,
"weight": 100.0,
"radius": 10.0
}
La información no esencial, como el nombre, peso y radio, puede actualizarse mediante el método updateExoplanet y el endpoint /update.

Para visualizar la información actualizada localmente, puede llamarse al método con el endpoint /get. Si topSecret no se ha ejecutado previamente, los valores predeterminados se devolverán:
{
"message": "",
"name": "Pluton",
"x": 0.0,
"y": 0.0,
"weight": 100.0,
"radius": 10.0
}

