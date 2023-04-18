# Kafka-Consumer-Producer services
Very basic architecture to publish and consume messages through Kafka broker from a specific topic.
Producer and Consumer are built in Spring Boot 3.0.5

---
## Start the Services
Requires Apache Kafka broker to be started at first
```bash
$ export KAFKA_BOOTSTRAP_SERVERS=localhost:9092
$ mvn -f consumer/ spring-boot:run 
```
In docker container
```bash
$ export KAFKA_BOOTSTRAP_SERVERS=kafka0:29092
$ docker build . -t consumer
$ docker run --net kafka_net1 --name consumer --env KAFKA_BOOTSTRAP_SERVERS=$KAFKA_BOOTSTRAP_SERVERS -p 9001:9001 consumer
```
