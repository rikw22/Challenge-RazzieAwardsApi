# Code Challenge - Golden Raspberry Awards

### Statement
Develop a RESTful API that returns the list of nominees and winners of the Worst Film category of the Golden Raspberry Awards.

### Requirements
- JDK 21+

## How to run this project
```bash
./mvnw spring-boot:run
```

### How to run the tests of this project
```bash
./mvnw test
```

### Http Request
```bash
curl http://localhost:8080/producer/stats
```
### API Documentation (Swagger UI)
http://localhost:8080/swagger-ui/index.html

### Sample Response - Award intervals Response
```json
{
  "min": [
    {
      "producer": "Producer 1",
      "interval": 1,
      "previousWin": 2008,
      "followingWin": 2009
    },
    {
      "producer": "Producer 2",
      "interval": 1,
      "previousWin": 2018,
      "followingWin": 2019
    }
  ],
  "max": [
    {
      "producer": "Producer 1",
      "interval": 99,
      "previousWin": 1900,
      "followingWin": 1999
    },
    {
      "producer": "Producer 2",
      "interval": 99,
      "previousWin": 2000,
      "followingWin": 2099
    }
  ]
}
```

### TODO
- [X] Add integration tests (only integration tests are needed for this project according to the requirement)
- [X] Add swagger documentation
- [ ] Finish README documentation
- [ ] Add Global Exception Handler
