FROM openjdk:latest
MAINTAINER com.example
COPY build/libs/RecipesApi-0.0.2-SNAPSHOT.jar RecipesApi-0.0.2-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/RecipesApi-0.0.2-SNAPSHOT.jar"]