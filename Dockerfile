FROM eclipse-temurin:17-alpine
COPY build/libs/diplom1-0.0.29.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]