FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp

EXPOSE 8761

ADD ./target/discovery-server-0.0.1-SNAPSHOT.jar discovery-server.jar

ENTRYPOINT ["java", "-jar", "/discovery-server.jar"]