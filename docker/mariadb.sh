docker run -d \
--name mariadb \
-e MARIADB_ROOT_PASSWORD="12345" \
-e MARIADB_USER="user" \
-e MARIADB_PASSWORD="12345" \
-e MARIADB_DATABASE="project" \
-p 3306:3306 \
mariadb:latest