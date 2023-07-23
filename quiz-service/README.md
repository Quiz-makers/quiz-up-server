## quiz-service

quiz-service is an application responsible for managing and conducting the main game in the 'quizUp' mobile application for Android.
It provides the main endpoints used by the mobile application.

The application consists of the following layers:

- config - contains application configuration classes, such as Spring configuration files, database configuration, security settings, etc.
- controller - contains application controller classes that handle HTTP requests, process data from forms, and call the appropriate services.
- converter - contains converter classes that transform DTO objects into entities and vice versa.
- dto - contains data transfer object classes that are used to transfer data between the application layers or between the application and the client.
- entity - contains entity classes that represent data structures in the database.
- exception - contains exception classes that are thrown in case of application errors.
- mapper - contains mapper classes that are responsible for mapping DTO objects to entities and vice versa.
- repository - contains repository classes that are responsible for communicating with the database and performing CRUD operations on entities.
- service - contains service classes that are responsible for the business logic of the application and use repositories to communicate with the database.
- utils - contains utility classes such as the class responsible for generating tokens or the class with methods for handling dates, etc.
