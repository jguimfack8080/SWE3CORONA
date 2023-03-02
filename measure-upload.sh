#!/bin/bash
webapp="$(cat webapp.txt)"
manager="$(cat manager.txt)"
baseurl=$(cat baseurl.txt)
for i in {1..20}; do 
	curl -s -F name=filename -F thefile=@misc/docker.jpg $baseurl/$webapp/upload?t=[1-50] & 
done; 
wait

ssh mydocker "sudo -u tomcat find /data/upload/ -type f|xargs rm"
