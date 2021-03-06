# Spring-Boot-Api-Rest
Spring Boot project for manage service orders.

## Tecnologies
- Java 11;
- Spring 4;
- Spring Boot;
- Spring Data JPA for data persistence;
- Maven as a project configuration for management of dependencies;
- H2 Database.

## How to run
This application is packaged as a jar which has Tomcat 9.0.36 embedded by Spring MVC of Spring Boot Starter. No Tomcat or any servlet container installation is necessary. You can run it using the <code>java -jar</code> command.
- Clone this ropository;
- Make sure you have installed JDK 12;
- You can build the project by running <code>mvn clean package</code>;
- Once successfully built, you can run by using the following command:
<code>java -jar target/osworks-api-0.0.1-SNAPSHOT.jar</code>

<p>Once the application runs you will see something like this:</p>
<pre>
	<code>
	2020-08-17 21:46:31.157  INFO 824 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
 	2020-08-17 21:46:34.690  INFO 824 --- [           main] c.a.osworks.OsworksApiApplication        : Started OsworksApiApplication in 13.414 seconds (JVM running for 14.816)
 	</code>
</pre>

## DataBase 
It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySql or other of your preference by changing the connection properties on <code>application.properties</code>.

### How to view your H2 in-memory database
<p>
	To view and to query the database you can access the link <a href="http://localhost:8080/h2">http://localhost:8080/h2</a> that will direct you to the h2 console. Default username is 'sa' with a blank password.
</p>
<p>
	The queries to create the tables are at SQL folder. You can use the Flyway (it's dependency is found on pom.xml file), it's a tool for version control to make migration of the tables created, or any change to be made on a relational database, to the MySql or other of your preference. That said, create the following diretory on <code>src/main/resources</code>: <code>db/migration</code><br/>. Then, create each version file, following the default <code>V00n__description</code>, where n is the number of version. <a href="https://flywaydb.org/documentation/migrations">[Details here]</a>.
</p>
<p>
	Other form is the creation of the tables automaticaly that is already configured on <code>application.properties</code> file through the command <code>spring.jpa.hibernate.ddl-auto=create-drop</code>. Don't worry with the other form above, because the Hibernate will create the tables and columns based on the entity classes.
</p>

## Endpoints
- <code>localhost:8080/clientes</code>
- <code>localhost:8080/ordens-servico</code>
- <code>localhost:8080/ordens-servico/{id}/comentarios</code> (comments of a especific service order, passing a id referencing a service order)

## Current features avaliable
- List, search, create, update and delete clients;
- List, search, create and update service orders;
- List, create, update and delete comments.
