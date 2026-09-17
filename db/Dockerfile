# Use the latest MySQL image
FROM mysql/mysql-server:latest

# Set the working directory
WORKDIR /tmp

# Copy SQL files
COPY test_db/*.sql /tmp/

# Copy dump files
COPY test_db/*.dump /tmp/

# Copy the database creation script
COPY test_db/employees.sql /docker-entrypoint-initdb.d

# Set the root password
ENV MYSQL_ROOT_PASSWORD example
ENV MYSQL_ROOT_HOST=%