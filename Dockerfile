FROM openjdk:17

WORKDIR /app

COPY target/distribution-sales-techfira-0.0.1-SNAPSHOT.jar /app/distribution-sales-techfira-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "distribution-sales-techfira-0.0.1-SNAPSHOT.jar"]
