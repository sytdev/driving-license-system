# Sistema de Licencias de Manejo
Aplicación construida bajo una arquitectura de microservicios con
el objetivo de emitir, validar, listar, mantener e invalidar licencias de conducir.

Para este sistema se usó Spring Cloud, Docker, Postgres, Mongo DB, Kafka, Prometheus, Grafana, Zipkin y Keycloak.

## Requisitos
- Java 21
- Maven 3.6.3 
- Docker 24.0.6
- Docker Desktop 4.24.2

## Instalación y Creación de imágenes

Para la generación de jars e imagenes ubicarse en la raíz de los siguientes módulos del repositorio clonado:

#### 1.Instalación de jar en el repositorio local de maven
```bash
  cd .\common-service\
  mvn -DskipTests clean install
```
#### 2.Creación de imagen Config Server
```bash
  cd .\config-server\
  mvn -DskipTests clean package dockerfile:build
```
#### 3.Creación de imagen Eureka Server
```bash
  cd .\eureka-server\
  mvn -DskipTests clean package dockerfile:build
```
#### 4.Creación de imagen Event Service
```bash
  cd .\event-service\
  mvn -DskipTests clean package dockerfile:build
```
#### 5.Creación de imagen Gateway Server
```bash
  cd .\gateway-server\
  mvn -DskipTests clean package dockerfile:build
```
#### 6.Creación de imagenes License Command Service
```bash
  cd .\license-command-service\
  mvn -DskipTests clean package dockerfile:build
```
#### 7.Creación de imagenes License Query Service
```bash
  cd .\license-query-service\
  mvn -DskipTests clean package dockerfile:build
```
    
## Despliegue

Para el despliegue de imágenes ubicarse en el carpeta *docker* del repositorio clonado:

```bash
  cd .\docker\
```

Finalmente ejecutar el archivo *docker-compose.yml*

```bash
  docker compose --file docker-compose.yml up -d
```

En caso de eliminar los contenedores creados ejecutar:
```bash
  docker compose down
```

## Información adicional y opcional
#### Consola Grafana
Usuario: user | Password: password
#### Consola Keycloak (Registrar *127.0.0.1 keycloak* en el archivo hosts del sistema operativo)
Usuario: user | Password: password
#### DB Mongo
Usuario: mongo | Password: mongo
#### DB Postgres
Usuario: postgres | Password: postgres


