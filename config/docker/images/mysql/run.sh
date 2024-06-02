#!/bin/bash
docker rm -f pollster_mysql
docker run -d --rm -p 3306:3306 \
 --platform linux/amd64 \
 -e MYSQL_ROOT_PASSWORD=masterkey123 \
 -e MYSQL_DATABASE=pollster \
 -e MYSQL_USER=pollster \
 -e MYSQL_PASSWORD=pollster123 \
 -v $PWD/db:/var/lib/mysql \
 --name pollster_mysql -d mysql:8.0.33 \
mysqld --event_scheduler=on --max-connections=1024 --transaction-isolation=READ-COMMITTED --sql-mode="" --character-set-server=utf8 --collation-server=utf8_general_ci
set -x
startDate=`date +%s`
echo $startDate
cd ../../../../ && ./gradlew liquibaseUpdate -Pssl_mode=REQUIRED -Pwait_db_connection=true
