# Mutant detector

El proyecto consiste en recibir un Json con una secuencia de caracteres emulando un ADN.
Si se determina que el ADN es mutante, se devuelve un Status OK, caso contrario, Forbidden.
En caso de contener caracteres no válidos, se devuelve un Status BAD_REQUEST.


### Requisitos

- Motor de base de datos MySQL versión 8 o posterior.
- Maven versión 3 o posterior.
- JDK versión 1.8.
- Git.


### Configuración

- Clonar el proyecto. 
```
git clone https://github.com/javimayer14/mutantDetector.git
```
- El IDE que se utilice para visualizar el codigo debe tener instalado la herramienta lombok
```
https://projectlombok.org/download
```
- Debe crearse una base de datos en MySQL con nombre "mutant_db".
- Para facilitar la creación de la DB se recomienda crear un contenedor de la siguiente manera
```
sudo docker run --name mutant_db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=root -e MYSQL_PASSWORD=root -p 3306:3306  -d mysql:8
```
- Luego acceder al contenedor de la siguiente manera:
```
sudo docker exec -it mutant_db bash
```
- Mas tarde:
```
mysql -uroot -proot
```
- Por ultimo creamos la base de datos:
```
CREATE DATABASE mutant_db;
```

- Posicionarse en el directorio del proyecto y correr ```mvn clean install``` desde una terminal para compilar el proyecto.


### Ejecución
- Posicionarse en el directorio del proyecto y ejecutar ```mvn spring-boot:run``` desde una terminal.
- También puede correr la aplicación desde su ide de preferencia.
