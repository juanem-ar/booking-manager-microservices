# booking-manager-microservices

TRUST YOURSELF 🚀✨

# Docker 
## run
- docker compose up -p
## stop
- docker stop $(docker ps -q)
- docker rm $(docker ps -a -q)

# Check Microservices Health
```
    http://localhost:8080/actuator/microservice_name/health
```

# Technologies
- Java 17
- Spring Boot 3.2.2
- Spring Cloud 2023.0.0
- Spring Cloud Gateway
- Spring Data JPA
- MySQL
- PostgreSQL
- Docker
- Actuator
- Kafka
- Zookeeper
- Resilience4j
- Circuitbreaker
- WebFlux
- Lombok