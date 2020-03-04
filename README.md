# PingOne Identity Spring Authorization Client and Resource Server Example. 

This sample demonstrate how [client](client-sample/README.md#authorization-client) accesses protected resources by presenting the access token to the [resource server (API)](resource-server-sample). 
The resource server must validate the access token and ensure that it has not expired and that its scope covers the requested resource.

In our case [resource server](resource-server-sample/README.md#resource-server) application is a standalone service, so we need to configure it how to decode tokens.
So, to specify which authorization server to use, we simply do:
```yaml
security:
  oauth2:
    resourceserver:
      jwt:
        issuer-uri: https://auth.pingone.com/<environment_id>/as/jwks
```

If resource server must be able to start up independently from the authorization server, then `issuer-uri` can be exchanged for `jwk-set-uri`.
The JWK Set uri is the endpoint returns the JSON Web Key Set [JWK](https://apidocs.pingidentity.com/pingone/customer/v1/api/auth/p1-a_Authorize/) document (i.e `https://auth.pingone.com/c2c2b4f8-c3da-4b23-abef-457ceaf25591/as/jwks`). This document contains the signing keys that can be used to validate JWT signatures.
Consequently, resource server will not ping the authorization server at startup. However, it will also no longer validate the iss claim in the JWT (since resource server no longer knows what the issuer value should be).

A JWT that is issued from an OAuth 2.0 Authorization Server will typically either have a scope or scp attribute, indicating the scopes (or authorities) it’s been granted, for example:

`{ …​, "scope" : "message contact"}`

When this is the case, resource server will attempt to coerce these scopes into a list of granted authorities, prefixing each scope with the string `"SCOPE_"`.
This means that to protect an endpoint or method with a scope derived from a JWT, the corresponding expressions should include this prefix: 
```java
.antMatchers("/api/messages/**").hasAuthority("SCOPE_message")
.antMatchers("/api/contacts/**").hasAuthority("SCOPE_contact")
```

Thereby, client has to have the same scope to get those resources:
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          pingidentity:
            ...
            scope: openid,profile,message,contact
```
