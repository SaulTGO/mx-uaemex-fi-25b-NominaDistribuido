FROM azul/zulu-openjdk:17
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
EXPOSE 3001
ENTRYPOINT ["java","-jar","target/frontend-0.0.1-SNAPSHOT.jar"]