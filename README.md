# Sistema de Licencias de Manejo
Aplicación demostrativa y moderna para gestionar el proceso de otorgamiento y revocación de licencias de conducir.

Diseñada bajo una arquitectura de microservicios con **Spring Cloud**. Además de experimentar con tecnologías y conceptos claves como:
- Contenerización con **Docker** para un despliegue escalable, y **Docker Compose** para una mejor gestión de servicios.
- Patron **CQRS** y **Apache Kafka** para desacoplar y optimizar las operaciones de escritura y lectura.
- Persistencia de datos híbrida con base datos SQL (**Postgres**) y NoSQL (**MongoDB**)
- Integración con **Keycloak** como solución de seguridad centralizada para la autenticacion y autorización.
- Integración con **Prometheus** y **Grafana** para la recopilación de metricas y visualización.

---

##  Características principales
- Gestión de **Licencias**: Emisión, validación y revocación, y mantenimiento.  
- Gestión de **Propietarios**: creación, actualización, listado, eliminación.  

---

##  Tecnologías utilizadas
###

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo" title="Java" />
  <img width="12" />
  <img src="https://cdn.worldvectorlogo.com/logos/spring-boot-1.svg" height="40" alt="spring boot logo" title="Spring Boot"/>
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg" height="40" alt="mongo logo"  title="Mongo DB"/>
  <img width="12" />
  <img src="https://spring.io/img/projects/spring-cloud.svg" height="40" alt="spring cloud logo"  title="Spring Cloud"/>
  <img width="12" />
  <img src="https://www.svgrepo.com/show/331370/docker.svg" height="40" alt="docker logo"  title="Docker"/>
  <img width="12" />
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/29/Postgresql_elephant.svg/1200px-Postgresql_elephant.svg.png" height="40" alt="docker logo" title="Docker"/>
  <img width="12" />
  <img src="https://e7.pngegg.com/pngimages/630/547/png-clipart-kafka-vertical-logo-tech-companies-thumbnail.png" height="40" alt="Kafka logo"  title="Kafka"/>
  <img width="12" />
  <img src="https://www.svgrepo.com/show/448228/grafana.svg" height="40" alt="grafana logo"  title="Grafana"/>
  <img width="12" />
  <img src="https://icon.icepanel.io/Technology/svg/Prometheus.svg" height="40" alt="prometheus logo"  title="Prometheus"/>
  <img width="12" />
  <img src="https://avatars.githubusercontent.com/u/4921466?s=280&v=4" height="40" alt="Keycloak logo"  title="Keycloak"/>
</div>

###
---

## Requisitos de despliegue
- Java 21
- Maven 3.6.3 
- Docker 24.0.6
- Docker Desktop 4.24.2

## Instalación y Creación de imágenes

Para la generación de jars e imagenes ubicarse en la raíz de los siguientes módulos del repositorio clonado:

```bash
  ## Instalación local de libreria compartida
  cd .\common-service\
  mvn -DskipTests clean install

  ## Creación de imagen Config Server
  cd .\config-server\
  mvn -DskipTests clean package dockerfile:build

  ## Creación de imagen Eureka Server
  cd .\eureka-server\
  mvn -DskipTests clean package dockerfile:build

  ## Creación de imagen Event Service
  cd .\event-service\
  mvn -DskipTests clean package dockerfile:build

  ## Creación de imagen Gateway Server
  cd .\gateway-server\
  mvn -DskipTests clean package dockerfile:build

  ## Creación de imagenes License Command Service
  cd .\license-command-service\
  mvn -DskipTests clean package dockerfile:build

  ## Creación de imagenes License Query Service
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


