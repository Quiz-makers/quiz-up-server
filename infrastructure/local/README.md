## HOW TO START LOCAL ENV

1. Launch docker. </br >

2. From the root folder (quiz-up-server) type in the terminal:

```
$ mvn clean install -DskipTests
```

3. Go to 'infrastructure/local' (this directory) in terminal and type
```
$ docker-compose up
```
Docker containers in background option:
```
$ docker-compose up -d
```

All containers local metrics/data e.t.c will be written under 'infrastructure/local/containers-data' folder.

### How to stop local env

1. To stop everything, type:
```
$ docker-compose down
```

2. [OPTIONAL] Remove 'containers-data' folder from 'infrastructure/local' directory.

If step 2 proceeded, after new start docker will create new resources (database e.t.c), if not, will use existing ones.

## HOW TO START ONLY PostgresDB (without microservices)
```
$ docker-compose -f common.yml -f postgres_database.yml up
```

## HOW TO RUN ANY LIQUIBASE ACTION MANUALLY

Use already set up Maven liquibase plugin (preferred way: IntelliJ IDE) to perform any action on Liquibase.
