FROM maven:3.8.3-openjdk-17 as maven
MAINTAINER Carlo Butelli <dev.butelli@mine.com>

COPY ./pom.xml ./pom.xml

COPY ./src ./src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

EXPOSE 9000

ADD . /app
WORKDIR /app

COPY --from=maven target/sb-consumer-service-0.0.1.jar app.jar

RUN chmod 755 /app/docker-entrypoint.sh
ENTRYPOINT ["/app/docker-entrypoint.sh"]
CMD ["run"]

#ENTRYPOINT ["java", "-jar", "app.jar"]