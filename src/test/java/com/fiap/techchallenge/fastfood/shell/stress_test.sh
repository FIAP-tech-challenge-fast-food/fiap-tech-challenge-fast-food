#!/bin/bash

if [ $# -eq 0 ]; then
  NUM_REQUESTS=1000
else
  NUM_REQUESTS=$1
fi

URL="ad3e57928f6144983bda9ce23ea1a490-210924668.us-east-1.elb.amazonaws.com:8080/swagger-ui/index.html#/User%20Management/findAll"
SLEEP_TIME=1                    

i=1
while [ $i -le $NUM_REQUESTS ]
do
  echo "Requisição $i:"
  curl -s -o /dev/null -w "%{http_code}\n" "$URL"
  sleep $SLEEP_TIME
  i=$((i+1))
done
