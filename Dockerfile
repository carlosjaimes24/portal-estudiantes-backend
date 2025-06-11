# Imagen base con JDK 17
FROM eclipse-temurin:17-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el proyecto completo al contenedor
COPY . .

# Entra a la carpeta del proyecto real
WORKDIR /app/portal-estudiantes

# Da permisos de ejecución a Maven Wrapper
RUN chmod +x ./mvnw

# Construye la aplicación (puedes omitir tests con -DskipTests si lo deseas)
RUN ./mvnw clean install -DskipTests

# Expone el puerto 8080 (por defecto Spring Boot)
EXPOSE 8080

# Comando para arrancar la aplicación
CMD ["./mvnw", "spring-boot:run"]
