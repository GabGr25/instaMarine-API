# Build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy parent pom first
COPY pom.xml .

# Copy module structure and poms
COPY instaMarine-api/pom.xml ./instaMarine-api/
COPY instaMarine-core/pom.xml ./instaMarine-core/
COPY instaMarine-data/pom.xml ./instaMarine-data/

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY . .

# Build
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

RUN addgroup --system spring && adduser --system spring-user --ingroup spring

COPY --from=builder /app/instaMarine-api/target/*.jar app.jar

RUN chown spring-user:spring app.jar
USER spring-user

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-Xmx512m", "-Xms256m", "-server", "-jar", "/app.jar"]