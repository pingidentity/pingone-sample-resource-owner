# PingOne Identity Spring Authorization Client and Resource Owner Example. 
# Authorization Client

This front-end sample application to test the [resource server](../resource-server-sample) sample application provided in this repository.

## Installation Steps
1. Add Ping14C `spring-boot-sdk` artifact to your pom:
```xml
    <dependency>
      <groupId>com.pingidentity.samples</groupId>
      <artifactId>spring-boot-sdk</artifactId>
      <version>${sdk.version}</version>
    </dependency>
```
You may want to add additional dependency to make your application development experience a little more pleasant, like [`<artifactId>spring-boot-devtools</artifactId>`](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html)
Since we are using Thymeleaf template engine, you can benefit from `spring.thymeleaf.cache` that controls compiled templates cache to avoid repeatedly parsing template files.

2. Create a new Ping14C resource with a custom scope called `message` in Ping14C admin console.
  
3. Create Native or Web application with:
 - `authorization_code` grant type
 - `openid` and `message` scopes
 - `Client Secret Post` token authentication method
 
4. Enable both applications in Ping14C admin console.

5. Configure your spring application configuration `application.yml` by replacing all `<...>` placeholders with the following information:
    - `<environment_id>` with your environment ID
    - `<authorization_code_client_id>` with your client id in
    - `<authorization_code_client_client_credentials_client_secret>` with your client secret

6. Start the resource server (which location is defined in `application.yml` `ping.resourceServerUrl` path):
```bash
cd ../resource-server-sample
mvn spring-boot:run
```

> **NOTE:** The above command starts the resource server on port 8000. 

7. Start your client application:
```bash
cd client-sample
mvn spring-boot:run
```
and browse to: `http://localhost:8080/` to login with exited user. After successful login you should be able to get resources of [resource server](../resource-server-sample) 
by clicking on `"Get some messages?"` link 
