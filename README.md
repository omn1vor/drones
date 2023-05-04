## Drones

### Overview
**Drones** is a service via REST API that allows clients to communicate with the drones.
It allows:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone;
- checking available drones for loading;
- check drone battery level for a given drone;
- register medication

### Build and run
1. To build and run in dev mode (with battery capacity logging to the in-memory H2 base which handles all the data)
just use gradle (```gradle bootRun``` or ```gradlew bootRun```)
2. To run in Kafka mode (battery capacity logging to a Kafka topic) you need to build the project and run docker
compose to build and start the app container along with kafka ones:
    - ```gradle build``` or ```gradlew build```
    - docker compose up
3. Service uses port 8080.
4. Swagger UI is available at ```http://localhost:8080/swagger-ui/index.html```
5. Kafka AKHQ in Kafka mode is available at ```http://localhost:8000/```

Both H2 database and Kafka topic are not persisted between service runs to not pollute the system.