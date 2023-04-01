# Start of Backend Dockerfile
FROM mvn:latest
MAINTAINER Scholarizer Team

EXPOSE 8080

COPY pom.xml ./
COPY . .

RUN mvn clean package
ENTRYPOINT ["java", "-jar", "target/edu.kit.scholarizer.jar"]

# Start of Frontend Dockerfile
FROM node:latest

COPY package*.json ./
RUN pnpm install

RUN pnpm build
RUN pnpm start

ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "target/edu.kit.scholarizer.jar"]

# Start of Database Dockerfile
FROM postgres:latest

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=scholarizer

COPY init.sql /docker-entrypoint-initdb.d/

# Start of Docker Compose File
version: '1.0'
services:
    frontend:
        container_name: frontend
        build:
            context: docker-frontend
            dockerfile: Dockerfile
        image: frontend:latest
        ports:
            - "8080:8080"
        networks:
            - scholarizer

    backend:
        container_name: backend
        build:
            context: docker-backend
            dockerfile: Dockerfile
        image: backend:latest
        ports:
            - "8081:8081"
        networks:
            - scholarizer

networks:
    scholarizer:
        driver: bridge
