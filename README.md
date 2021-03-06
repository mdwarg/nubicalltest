# API de usuarios 

_API REST de usuarios basado en Spring Boot_

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

Para instalar el proyecto se debe clonar o descargar el repositorio.
Para clonar:
```
git clone git@github.com:mdwarg/nubicalltest.git
```
Descargar ZIP:
```
https://github.com/mdwarg/nubicalltest/archive/master.zip
```

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

_Para instalar la aplicación necesitas tener instalado:_

* [Maven](https://maven.apache.org/install.html)
* [Docker](https://docs.docker.com/install/)
* [Docker Compose](https://docs.docker.com/compose/install/)

## Ejecutando las pruebas ⚙️

_Se pueden ejecutar las pruebas usando maven con el siguiente comando_

Dentro del directorio "./users", que es donde se encuentra el código del proyecto ejecutamos:
```
mvn test
```

### Coverage de las pruebas 🔩

_Se puede obtener un reporte del coverage de las prueba con el siguiente comando:_

Dentro del directorio "./users", que es donde se encuentra el código del proyecto ejecutamos:
```
mvn jacoco:report
```
Luego de que finaliza la generación del reporte podemos encontrar una version del mismo en la siguiente ruta:
```
./users/target/site/jacoco/index.html
```

## Deployment 📦

_Para desplegar el proyecto debemos hacer el empaquetado de la apicacion utilizando maven_

Dentro del directorio "./users", que es donde se encuentra el código del proyecto ejecutamos el siguiente comando:
```
mvn clean package docker:build
```
_Luego con docker compose podemos ejecutar los servicios necesarios para iniciar la aplicación_

Una vez generada la imagen del contenedor ejecutamos el siguiente comando:
```
docker-compose up
```
De esta forma deberiamos tener un contenedor con nuestra base de datos en MySQL y otro con la aplicación de Spring Boot en Java 8

Una vez iniciada la aplicacion podemos encontrar documentacion de la API en la siguiente URL:
```
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
```

## Archivos log ⚙️

_Se pueden consultar los archivos log generados por la aplicación_

Los archivos log se generan en la siguiete ruta (configurable desde application.yml):
```
/var/tmp/nubicall.users.log
```
Como la aplicación se ejecuta dentro de un contenedor docker precisamos enviar al mismo los comandos que queremos ejecutar. Lo hacemos de la siguiente forma:
```
docker exec -i {nombre del contenedor} {comando}
```
Para obtener el nombre del contenedor ejecutamos el siguiente comando:
```
docker ps
```

Ejemplo para ver el log con el comando tail:
```
docker exec -i users_users-nubicall_1 tail -f /var/tmp/nubicall.users.log
```

## Archivos de configuración ⚙️

_Se pueden encontrar los archivos de configuración en las siguientes rutas_

Aplicación:
```
./users/src/main/resources/config.properties
```

Spring:
```
./users/src/main/resources/application.yml
```

Docker compose:
```
./users/docker-compose.yml
```

Maven:
```
./users/pom.xml
```

## Pruebas externas con Postman ⚙️

_Para realizar pruebas con [Postman](https://www.getpostman.com/) se incluye una colección de invocaciones a la API_

Este archivo se encuentra en la siguiente ubicación:
```
./postman/Nubicall users.postman_collection.json
```

## Construido con 🛠️

* [Spring Boot](http://spring.io/projects/spring-boot) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [MySQL](https://www.mysql.com/) - Base de datos
* [Docker](https://www.docker.com/) - Contenedor de aplicacion para la instalacion

## Autores ✒️

* **Marcelo Wieja** - [mdwarg](https://github.com/mdwarg)


