# GIẢI THÍCH: Multi-stage build giúp giảm kích thước image

# Stage 1: BUILD - Dùng Maven để build ứng dụng
FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /app

# Copy file pom.xml trước để tận dụng Docker cache layer
COPY pom.xml .
# Download dependencies trước (layer này sẽ được cache nếu pom.xml không đổi)
RUN mvn dependency:go-offline -B

# Copy toàn bộ source code
COPY src ./src

# Build application (skip tests để build nhanh hơn)
RUN mvn clean package -DskipTests

# Stage 2: RUNTIME - Image nhẹ chỉ chứa JRE để chạy ứng dụng
FROM amazoncorretto:21-alpine
WORKDIR /app

# Copy file JAR từ stage build
COPY --from=build /app/target/*.jar app.jar

# Expose port 8085 (port mặc định của ứng dụng)
EXPOSE 8085

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
