# DogSpring
Dog raising simulator using SpringBoot to create RESTful APIs. Inspired by online anmal raising games like [Neopets](https://www.neopets.com/) and [Webkinz](https://www.webkinz.com/).

## Build & Deploy Instructions
This section explains how to run DogSpring using Gradle.

1. Clone this repository
2. Navigate to this project's root directory and run `./gradlew bootrun`
3. DogSpring should be running on [http://127.0.0.1:8080/](http://127.0.0.1:8080/)

### Connecting to a PostgreSQL database
Follow these steps if you want to connect this program to a PostgreSQL database instead of the default list implementation.

1. Create the "**dog-spring**" database using pgAdmin.
2. Create the **dogs** and **users** tables within the **dog-spring** database <br>
The two following commands should be run:

    - For dog table: <br>
`CREATE TABLE dogs (
	id UUID PRIMARY KEY NOT NULL,
	name VARCHAR(50) NOT NULL,
	happiness INTEGER DEFAULT 100,
	energy INTEGER DEFAULT 100,
	hunger INTEGER DEFAULT 0,
	imgLink VARCHAR(200) NOT NULL,
	birthdate TIMESTAMP,
	ownerUsername VARCHAR(50) 
)`

    - For user table: <br>
`CREATE TABLE users (
	username VARCHAR(50) PRIMARY KEY NOT NULL UNIQUE,
	displayName VARCHAR(50) NOT NULL,
	ownedDogIDs UUID[],
	gold INTEGER DEFAULT 0,
	lastDogRequest timestamp with time zone 
)`

3. Navigate to src/main/resources/application.properties and input the appropriate datasource url, username and password for your installation
4. Change the source code datasources by going to src/main/java/io/github/jdrolet/dogspring/service and changing the appropriate qualifiers <br>
    - Change "**fakeDogDao**" to "**postgresDogDao**" in DogService.java and change "**fakeUserDao**" to "**postgresUserDao**" in UserService.java
5. Run the program as normal using `./gradlew bootrun`

## Acknowledgments
Dog images are provided courtesy of the [Dog CEO Dog API](https://dog.ceo/dog-api/).

## About
This project was created by me  to furthur my understanding of SpringBoot, RESTful APIs, Databases and software architecture. DogSpring was motivated as being a jumping off point for furthur development and technologies. Possible expansions include 
- Implementing authentication for users
- Adding more complicated game mechanics
- Trading with other users

