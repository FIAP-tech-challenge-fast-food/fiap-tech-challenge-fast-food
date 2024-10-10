#!/bin/bash

URL=$1

if [ $# -ge 2 ]; then
  NUM_REQUESTS=$2
else
  NUM_REQUESTS=1000
fi

SLEEP_TIME=0.5

i=1

echo "Começando as $NUM_REQUESTS requisições!"

while [ $i -le $NUM_REQUESTS ]
do
  curl -s -o /dev/null "$URL/stress-test"

  if [ $((i % 50)) -eq 0 ]; then
    echo "Progresso: [$i/$NUM_REQUESTS]"
  fi

  sleep $SLEEP_TIME

  i=$((i+1))
done

echo "Todas as $NUM_REQUESTS requisições foram concluídas!"