# ClientServerApplication
![](https://img.shields.io/badge/Code-Java-informational?style=flat&logo=Java&logoColor=white&color=4AB197)
![](https://img.shields.io/badge/Code-PostgreSQL-informational?style=flat&logo=PostgreSQL&logoColor=white&color=4AB197)
![](https://img.shields.io/badge/Code-JDBC-informational?style=flat&logo=JDBC&logoColor=white&color=4AB197)
![](https://img.shields.io/badge/Log-Log4j2-informational?style=flat&logo=Log4j2&logoColor=white&color=4AB197)
<br>
A simple multithreading application with a client-server architecture 
##Technologies
- Java
- PostgreSQL
- JDBC
- Log4j2
## How to run
```sh
Firstly launch classes Server.java in Server, then Client.java in Client
```
## Server
Contains the general logic of working and validating person.

| HTTP METHOD | PATH | USAGE |
| -----------| ------ | ------ |
| GET | /api/v1/people | get all people |
| GET | /api/v1/people/{id} | get person by id |
| POST | /api/v1/people| create new person |
| PUT | /api/v1/people/{id}| update person info |
| DELETE | /api/v1/people/{id} | delete person by id | 

