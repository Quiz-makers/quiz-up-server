## HOW TO START LOCAL ENV

To start quiz-app backend, follow these steps: <br />

1. Launch docker. </br > </br >

2. From the root folder (quiz-up-server) type in the terminal:

```
$ mvn clean install -DskipTests
```

3. Go to directory 'infrastructure/local'
```
$ cd ./infrastructure/local
```

4. Run everything by typing:
```
$ docker-compose up
```
Background option:
```
$ docker-compose up -d
```

All containers local metrics/data e.t.c will be written under 'infrastructure/local/containers-data' folder.

### How to stop local env

5. To stop everything, type:
```
$ docker-compose down
```

6. [OPTIONAL] Remove 'containers-data' folder from 'infrastructure/local' directory.