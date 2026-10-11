# Usa la imagen oficial de Tomcat 11 con JDK 17
FROM tomcat:11.0-jdk17-temurin

# Eliminar las aplicaciones por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar el archivo WAR compilado como ROOT.war
COPY target/javaWeb-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Exponer el puerto predeterminado de Tomcat
EXPOSE 8080

CMD ["catalina.sh", "run"]
