# PingOne Identity Spring Authorization Client and Resource Owner Example. 
# Authorization Client

This front-end sample application to test the [resource server](../resource-server-sample) sample application provided in this repository.

## Prerequisites

Before running this sample, you should start the resource server.

```bash
cd ../resource-server-sample
mvn
```

> **NOTE:** The above command starts the resource server on port 8000. 

Then you can start a client application
```bash
cd client-sample
mvn
```
and browse to: `http://localhost:8080/` to login with exited user
