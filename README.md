# booking-manager-microservices

TRUST YOURSELF 🚀✨

# Docker 
## run servers
```
docker-compose up eureka-server config-server
```
## run databases
```
docker-compose up db-availabilities db-bookings db-bu db-payments db-rates
```
## run services
```
docker-compose up booking-service business-service availability-service rate-service payment-service
```
```
docker-compose up api-gateway
```
## stop
```
docker-compose stop
```

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

# Postman Collection
[Link de descarga](https://drive.google.com/file/d/1t0sQ6xGKyK2lfS6ykPVeu45q2gMbskKZ/view?usp=sharing)


# Arquitectura Actual

<a href="https://ibb.co/8sz2vkr"><img src="https://i.ibb.co/Jtx26Zv/arquitectura.jpg" alt="arquitectura" border="0"></a>
