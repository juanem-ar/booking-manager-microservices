FROM openjdk:21
LABEL authors="Juanem"
VOLUME /tmp
ADD ./target/business-unit-0.0.1-SNAPSHOT.jar business-unit.jar
ENTRYPOINT ["java", "-jar", "/business-unit.jar"]