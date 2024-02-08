# Building REST services with Spring

Project based on [Spring Guide](https://spring.io/guides/tutorials/rest/)

## Up & Running

In your terminal run: `docker compose up --build`

## Roadmap

- Refactor (Project Structure)

  - [x] Controller
  - [x] Model
  - [x] Interface
  - [x] Etc...

- [x] [Metrics and Tracing](https://spring.io/guides/gs/tanzu-observability/)
- [x] [Dockerfile](https://spring.io/guides/topicals/spring-boot-docker/)

### Todo

For the sake of simplicity, only the Employee model has tests

- [Tests](https://docs.spring.io/spring-boot/docs/3.2.2/reference/html/features.html#features.testing)

  - [x] [JSON](https://spring.academy/courses/building-a-rest-api-with-spring-boot)
  - [x] Unit
  - [x] Integration
  - [x] [WebMvc](https://spring.io/guides/gs/testing-web/)

- [CI/CD GitHub Actions](https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven)

  - [x] Build
  - [x] Test
  - [ ] Publish API Docs (Bônus)

### Bônus 🎁

- [x] Replace H2 for [Postgres](https://www.docker.com/blog/how-to-use-the-postgres-docker-official-image/)
- [x] [Monitor Postgres with PGAdmin](https://github.com/docker/awesome-compose/tree/master/postgresql-pgadmin)
- [ ] [Caching (Redis)](https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html)
- [x] [Docker Compose](https://github.com/docker/awesome-compose/tree/master/spring-postgres)
- [ ] [Nginx](https://github.com/docker/awesome-compose/tree/master)
- [ ] [Performance Test (Jmeter)](https://jmeter.apache.org/index.html)
- [ ] [API Docs with Restdocs](https://spring.io/guides/gs/testing-restdocs/)
