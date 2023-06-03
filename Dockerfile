FROM amazoncorretto:17 as builder

RUN yum install -y wget tar gzip \
    && wget https://archive.apache.org/dist/maven/maven-3/3.2.5/binaries/apache-maven-3.2.5-bin.tar.gz \
    && tar xzf apache-maven-3.2.5-bin.tar.gz \
    && mv apache-maven-3.2.5 /opt/maven \
    && rm apache-maven-3.2.5-bin.tar.gz

ENV MAVEN_HOME=/opt/maven
ENV PATH=${MAVEN_HOME}/bin:${PATH}

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests

FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/target/DummyCarrierService-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "DummyCarrierService-0.0.1-SNAPSHOT.jar"]
