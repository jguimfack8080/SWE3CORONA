#!/bin/bash
YOURDB=${USER}_db
YOURUSER=$USER
YOURPWD=$(grep '^password' ~/.my.cnf|cut -d= -f2)
sed -i \
  -e "s/yourdatabase/$YOURDB/g" \
  -e "s/yourusername/$YOURUSER/g" \
  -e "s/yourpassword/$YOURPWD/g" \
  app/META-INF/context.xml
 # src/hbv/RedisServlet.java src/hbv/MyContextListener.java

echo "docker-$USER-java" > webapp.txt
echo "docker-$USER-manager" > manager.txt
echo "https://informatik.hs-bremerhaven.de" > baseurl.txt
echo "Denke daran, dass Deine .netrc auch das Passwort 
aus ~/.my.cnf enthalten muss:
machine informatik.hs-bremerhaven.de login manager password DEINPASSWORD
"
ssh mydocker ' if ! test -e /data/upload; then mkdir -m 770 /data/upload/; chown :tomcat /data/upload; fi'

