
## Getting up and Running...

````bash
./build.sh
docker-compose up
````

## Apps
* keycloak (port: 8080 | external)
* security-gateway (port: 8081 | external)
* secured-service (port: 9091 | internal) >> gateway path /service1
* mysql (port: 3306  | internal)


## Cleaning Up

When you're done use `Ctrl-C` to stop the containers, `docker-compose rm -f` to remove leftovers.

## Want to go again? 

Probably have to clear your cookies and restart the Docker bits... 
