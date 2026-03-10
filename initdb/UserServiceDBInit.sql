CREATE DATABASE UserServiceDB;

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    CONSTRAINT chk_role_name CHECK (role_name ~ '^[a-zA-Z].*')
);

CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    CONSTRAINT chk_username CHECK (username ~ '^[a-zA-Z].*'),
    CONSTRAINT chk_password_length CHECK (LENGTH(password) >= 4)
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);