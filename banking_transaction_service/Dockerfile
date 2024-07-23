FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update --fix-missing && apt-get install -y maven

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . . 

RUN mvn clean install -DskipTests

EXPOSE 9003

CMD ["java", "-jar", "target/banking_transaction_service-0.0.1-SNAPSHOT.jar"]