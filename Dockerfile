# ETAPA 1: Compilacion con Maven y JDK 17 
FROM maven:3.9-eclipse-temurin-17 AS build 
WORKDIR /app 
COPY pom.xml . 
COPY src ./src 
RUN mvn clean package -DskipTests 
# ETAPA 2: Despliegue en Tomcat 11 
FROM tomcat:11.0-jdk17-temurin 
WORKDIR /usr/local/tomcat 
RUN rm -rf webapps/* 
COPY --from=build /app/target/*.war webapps/ROOT.war 
EXPOSE 8080 
CMD ["catalina.sh", "run"]
