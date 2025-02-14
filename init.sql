-- Создание базы данных для Keycloak
CREATE DATABASE keycloak_db;

-- Создание пользователя для Keycloak
CREATE USER keycloak WITH PASSWORD 'password';

-- Предоставление прав пользователю keycloak на базу данных keycloak_db
GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO keycloak;

-- Создание базы данных для приложения
CREATE DATABASE jwt_auth_db;

-- Создание пользователя для приложения
CREATE USER jwt_auth_app WITH PASSWORD 'password';

-- Предоставление прав пользователю jwt_auth_app на базу данных jwt_auth_db
GRANT ALL PRIVILEGES ON DATABASE jwt_auth_db TO jwt_auth_app;
