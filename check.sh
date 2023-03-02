#!/bin/bash
webapp="$(cat webapp.txt)"
manager="$(cat manager.txt)"
baseurl=$(cat baseurl.txt)

curl -s $baseurl/$webapp/hello
curl -s $baseurl/$webapp/forwarder
curl -s $baseurl/$webapp/sql
curl -s $baseurl/$webapp/redis
curl -s $baseurl/$webapp/image | identify -format "%m %B %z %r %A %wx%h\n" -
curl -s -F name=filename -F thefile=@misc/small.png $baseurl/$webapp/upload
curl -s -F name=filename -F thefile=@misc/docker.jpg $baseurl/$webapp/upload
curl -s $baseurl/$webapp/all
curl -s $baseurl/$webapp/sqlpool
curl -s $baseurl/$webapp/redispool
