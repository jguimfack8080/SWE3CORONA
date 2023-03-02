#!/bin/bash
endpoint="$1"
port=$((($(id -u)-10000)*10+10008))
[ "$1" = "" ] && { echo "call $0 http://turing:$port/docker-$USER-java/hello"; exit 1; }
for i in {1..10}; do
	curl $endpoint
	wrk -d10 -c20 -t10 $endpoint
	sleep 0.1
done
