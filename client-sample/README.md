# PingOne Identity Spring Authorization Client and Resource Owner Example. 
## Authorization Client

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

2. Create a new Ping14C resource with a custom scope called `message` in Ping14C admin console (click: CONNECTIONS -> Resources).
  
3. Create Native or Web application with:
    - `Code` response type
    - `Authorization Code` grant type
    - `openid` and `message` scopes
    - `Client Secret Post` token authentication method
    - `http://localhost:8080/login/oauth2/code/pingidentity` redirect URI
 
4. Enable both applications in Ping14C admin console.

5. Configure your spring application configuration `application.yml` by replacing all `<...>` placeholders with the following information:
    - `<environment_id>` with your environment ID
    - `<authorization_code_client_id>` with your client id in
    - `<authorization_code_client_client_credentials_client_secret>` with your client secret
    - (optional) to enable __SSL__ change following properties `server.ssl.enabled: true`, `server.port: 8433` and fill certificate related params:
      - `<key store type>` the format used for the keystore (i.e PKCS12, JKS file)
      - `<path to key>` the path to the keystore containing the certificate
      - `<key store password>` the password used to generate the certificate
      - `<key alias>` the alias mapped to self-signed certificate
<br>_`TIP:`_ ["how to create self-signed SSL certificate"](https://oracle-base.com/articles/linux/create-self-signed-ssl-certificates)

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
and browse to: `http://localhost:8080/` to login with existing user. After successful login you should be able to get resources of [resource server](../resource-server-sample) 
by clicking on `"Get some messages?"` link.
