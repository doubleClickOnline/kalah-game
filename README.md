### Startup service
`docker-compose up`

### Build service
`docker build -f ./docker/Dockerfile --tag kalah-game-service .`

### Development prerequisites
- Java 15
- Gradle 6.x

### Documentation
- API documentation hosted in service(requires service started) [here](http://localhost:9000/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)

### BDD Tests

#### Development prerequisites
 - Ruby 2.4.x
 - Bundle 1.x

#### Environment variables
 - HOST=localhost (game service host)
 - PORT=9000 (game service port)

#### Start BDD tests
    - bundle install (install dependencies)
    - source .env (default env variables values)
    - cucumber --format pretty (run bdd tests)
