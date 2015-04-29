
# Java Web Starter &nbsp; [![Build Status](https://travis-ci.org/delkopiso/java-web-starter.svg?branch=master)](https://travis-ci.org/delkopiso/java-web-starter)

A starter Java web application based on Spring. It leverages Spring Web MVC, Spring Data JPA with a Hibernate implementation, and Spring Security. 
:octocat: &nbsp;**Live Site**: <http://java-web-starter.herokuapp.com> 

## Scope
This is meant to be a starter. It is configured with User and Role data models which are used to implement best-practice authentication schemes.
The application comes set up with two users pre-configured: 

1.  Admin (username: _admin_, password: _admin_) with two roles ('ROLE\_ADMIN' and 'ROLE\_USER')
2.  Test (username: _test_, password: _test_) with one role ('ROLE\_USER')

## Features
*	Login and Register users
*	Verification of user emails after registration to control spam accounts
*	Forgotten password logic
*	User roles are implemented to allow for administration by a central entity
*	Clean. Simple. Minimal.

## Dependencies

*   [Spring Framework](http://projects.spring.io/spring-framework/) provides the core functionality in this application such as Web MVC.
*   [Spring Security](http://projects.spring.io/spring-security/) provides authentication and other related security features.
*	[Spring Data JPA](http://projects.spring.io/spring-data-jpa/) uses a convention over configuration specification for abstracting away the Data Access Layer.
*	[Apache Tiles](https://tiles.apache.org/) is used for the layout templating allowing a very dynamic approach to setting out the views.
*	[Hibernate](http://hibernate.org/) is an ORM (Object-Relational Model) that implements the JPA specification.
*	[Hypersonic Database](http://hsqldb.org/) is an in-memory Java database that allows for faster development when a relational database is required.
*	[PostgreSQL](http://www.postgresql.org/) is a robust, reliable and powerful relational database that uses SQL.
*	[DBCP](http://commons.apache.org/proper/commons-dbcp/) lets you create a pool of open database connections to be shared by clients.
*	[BSON Types](http://docs.mongodb.org/manual/reference/bson-types/#objectid) provides the ObjectId type which is used as a primary key in the database.
*	[SendGrid](https://sendgrid.com/) is a transactional and marketing email service.
*	[Twitter Bootstrap](http://getbootstrap.com/) is a frontend framework that simplifies the creation of decent user interfaces for web applications.

## Running the application

### In Development (your local environment)

To run this application in development using an embedded Tomcat 7 server: 
`mvn tomcat7:run -Dspring.profiles.active=dev` 

This will start the embedded Tomcat server on port 8080 and you can access the application here: <http://localhost:8080/java-web-starter> 

The `dev` profile uses an embedded HSQL database which is persistent only when the application is running.

You will also need to set up the your own copy of the SendGrid credentials system environment variables. 

### In Production 

Run the following commands in the application root directory:

*   `mvn clean package`
*   `java -jar target/dependency/webapp-runner.jar target/*.war`

**_WARNING_**: Running production-style will require you have PostgreSQL installed and the database credentials stored in the `DATABASE_URL` environment variable.

## Deployment

The application is currently deployed on Heroku. The following steps will deploy the WAR file produced in the previous section by running this heroku plugin command:
`heroku deploy:war --war target/*.war --app <appname>`

## TODOs
*	Implement _Rememeber Me_ feature to persist user sessions beyond each browser session.