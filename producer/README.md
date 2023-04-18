# Basic Spring Boot (3.0.5) Producer service
Very basic producer service to publish messages through Kafka broker at a defined topic

## Start the Services
Requires Apache Kafka broker to be started at first
```bash
export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
mvn -f producer/ spring-boot:run 
```
In docker container
```bash
export KAFKA_BOOTSTRAP_SERVERS=kafka0:29092
docker build . -t producer
docker run --net kafka_net1 --name producer -p 9000:9000 --env KAFKA_BOOTSTRAP_SERVERS=$KAFKA_BOOTSTRAP_SERVERS producer
```