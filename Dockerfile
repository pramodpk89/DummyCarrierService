FROM adoptopenjdk:11-jdk-hotspot as builder

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

COPY --from=builder /app/target/DummyCarrierService-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "DummyCarrierService-0.0.1-SNAPSHOT.jar"]
