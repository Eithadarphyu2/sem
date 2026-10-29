
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/sem.jar sem.jar

ENTRYPOINT ["java", "-jar", "sem.jar", "db:3306", "30000"]
