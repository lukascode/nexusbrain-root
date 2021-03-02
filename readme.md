Update project version
```
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT -DgenerateBackupPoms=false
```

Run tests (ommit profile if you want to run only unit tests)
```
mvn clean verify -PwithIT 
```

Liquibase <-> Testcontainers (mysql) problems

https://www.testcontainers.org/modules/databases/jdbc/