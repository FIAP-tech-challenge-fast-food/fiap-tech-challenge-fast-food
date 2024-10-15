# Estágio de Build
FROM maven:3.8.4-openjdk-17 AS build

# Copia os arquivos do projeto para o contêiner
COPY src /app/src
COPY pom.xml /app

# Define o diretório de trabalho
WORKDIR /app

# Executa a compilação do Maven
RUN mvn clean install

# Estágio Final
FROM openjdk:17-jdk-slim

# Copia o arquivo JAR gerado no estágio de build para o estágio final
COPY --from=build /app/target/fastfood-1.1.0.jar /app/app.jar

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta 8080 para o contêiner
EXPOSE 8080

# Comando para executar a aplicação com o perfil docker
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
