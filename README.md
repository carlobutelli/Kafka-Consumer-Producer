# Kafka-Consumer-Producer services
Very basic architecture to publish and consume messages through Kafka broker from a specific topic.
Producer and Consumer are built in Spring Boot 3.0.5

---
## Start the Services
### Apache kafka broker
```bash
$ docker-compose -f kafka-zookeper_compose.yml up -d
```

### Producer and consumer services
```bash
$ mvn -f consumer/ spring-boot:run 
$ mvn -f producer/ spring-boot:run 
```
---

## Test the APIs
### Send a String object
```bash
curl --location 'localhost:9000/api/kafka/send-string' \
--header 'Content-Type: application/json' \
--data '{
    "message": "Oggetto stringa",
    "owner": "Carlo"
}'
```
### Send an object
```bash
curl --location 'localhost:9000/api/kafka/send-json' \
--header 'Content-Type: application/json' \
--data '{
    "message": "Oggetto stringa",
    "owner": "Carlo"
}'
```
