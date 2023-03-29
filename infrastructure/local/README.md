## How to START local env

1. Launch docker. </br >

2. Build project

#### OPTION 1 (using IntelliJ IDE)

https://www.jetbrains.com/help/idea/work-with-maven-goals.html#run_goal

Open Maven 'Run Anything window' (Execute Maven goal) and type:

```
$ mvn clean install -DskipTests
```

#### OPTION 2 (REQUIRES JDK 17 installed on local machine)

From the root folder (quiz-up-server) type in the terminal:

Windows
```
$ .\mvnw clean install -DskipTests
```
Linux and other
```
$ ./mvnw clean install -DskipTests
```

3. Go to 'infrastructure/local' (the same directory where this README.md file is placed) in terminal and type
```
$ docker-compose up
```
Docker containers in terminal BACKGROUND option:
```
$ docker-compose up -d
```

All containers local metrics/data e.t.c will be written under 'infrastructure/local/containers-data' folder.

## How to STOP local env

1. To stop everything, type:
```
$ docker-compose down
```

2. [OPTIONAL] Remove 'containers-data' folder from 'infrastructure/local' directory.

If step 2 proceeded, after new start docker will create new resources (database e.t.c), if not, will use existing ones.

## How to START ONLY PostgresDB (without microservices)

```
$ docker-compose -f common.yml -f postgres_database.yml up
```

## How to RUN ANY LIQUIBASE action MANUALLY

#### OPTION 1

From the root folder (quiz-up-server) type in the terminal:

Windows
```
$ .\mvnw liquibase:update -pl db
```
Linux and other
```
$ ./mvnw liquibase:update -pl db
```

Replace 'update' command with any other action if required.

#### OPTION 2

Use already set up Maven liquibase plugin (preferred way: IntelliJ IDE) to perform any action on Liquibase.