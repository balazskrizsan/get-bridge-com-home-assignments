# One on one application

### Todo:

- [x] SSL
- [x] project setup
- [x] e2e tests - TDD
- [x] integration tests - TDD
- [x] k8s yamls
- [x] OpenApi
- [x] detailed readme.md

# Setup

### host file

```
localhost.balazskrizsan.com should go to 127.0.0.1
```

### Port mapping

| Service                | Port |
|------------------------|------|
| Application            | 8280 |
| Application in test    | 8281 |
| Application db         | 9090 |
| Application db in test | 9091 |

#### OpenApi endpoint

```
:{port}/api-docs
```

#### Postman collection

[GB_homework.postman_collection.json](GB_homework.postman_collection.json)

### Kubernetes db setup

#### Create namespace

[setup-namespaces.yaml](cicd%2Fk8s%2Fsetup-namespaces.yaml)

#### Setup app and test db

[dev-db.yaml](cicd%2Fk8s%2Fdev-db.yaml)

[dev-test-db.yaml](cicd%2Fk8s%2Fdev-test-db.yaml)

#### JOOQ code generation (win)

[gen.bat](jooq%2Fgen.bat)

```windows powershell
./jooq/gen.bat
```

### IDEA setup

#### Env vars as string:

```
SERVER_ENV=TEST;SERVER_PORT=8280;SERVER_SSL_ENABLED=true;SERVER_SSL_KEY_STORE=classpath:keystore/dev.p12;SERVER_SSL_KEY_STORE_PASSWORD=password;SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=10;SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=10;SPRING_DATASOURCE_PASSWORD=password;SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:9090/oneonone;SPRING_DATASOURCE_USERNAME=admin
```

#### IDEA test env variables as string

```
SERVER_ENV=TEST;SERVER_PORT=8281;SERVER_SSL_ENABLED=true;SERVER_SSL_KEY_STORE=classpath:keystore/dev.p12;SERVER_SSL_KEY_STORE_PASSWORD=password;SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=10;SPRING_DATASOURCE_HIKARI_MINIMUM_IDLE=10;SPRING_DATASOURCE_PASSWORD=password;SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:9091/oneonone;SPRING_DATASOURCE_USERNAME=admin
```

 