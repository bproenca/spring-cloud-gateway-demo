#!/bin/bash

echo "Begin ..."
TOKEN_JSON_RESPONSE=$(curl --location --request POST 'http://sre-keycloak.insidepublicprd.gru.oraclevcn.com:8080/auth/realms/scs/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'client_id=client-tenant-aaa' \
--data-urlencode 'client_secret=63494476-d818-4d1d-bab9-8da6e0d6d572')
echo ''; echo 'The token response is ' $TOKEN_JSON_RESPONSE

ACCESS_TOKEN=$(echo $TOKEN_JSON_RESPONSE | jq -j '.access_token')
echo ''; echo 'The access token is '$ACCESS_TOKEN; echo '';

for i in `seq 1 400`
do 
    curl -H 'Accept: application/json' -H "Authorization: Bearer ${ACCESS_TOKEN}"  http://localhost:8081/service1/api/private
    #curl http://localhost:8081/service1/api/public
    echo ''
done