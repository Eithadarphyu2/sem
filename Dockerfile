FROM eclipse-temurin:26-jre

COPY ./target/seMethods-v0.1.0.4-jar-with-dependencies.jar /tmp

WORKDIR /tmp

ENTRYPOINT ["java", "-jar", "seMethods-v0.1.0.4-jar-with-dependencies.jar"]