FROM amazoncorretto:17 as builder

RUN yum install -y maven

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/target/DummyCarrierService-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "DummyCarrierService-0.0.1-SNAPSHOT.jar"]
