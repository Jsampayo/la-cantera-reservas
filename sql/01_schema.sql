CREATE DATABASE IF NOT EXISTS la_cantera CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE la_cantera;

CREATE TABLE IF NOT EXISTS clientes (
    id        INT          NOT NULL AUTO_INCREMENT,
    nombre    VARCHAR(50)  NOT NULL,
    correo    VARCHAR(100) NOT NULL,
    telefono  VARCHAR(15)  NOT NULL,
    contrasena VARCHAR(100) NOT NULL,   -- hash BCrypt (60 caracteres), nunca la contraseña real
    PRIMARY KEY (id),
    UNIQUE (correo)
);

CREATE TABLE IF NOT EXISTS canchas (
    id         INT         NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(30) NOT NULL,
    tipo       VARCHAR(20) NOT NULL,
    capacidad  INT         NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (nombre)
);

CREATE TABLE IF NOT EXISTS reservas (
    id          INT         NOT NULL AUTO_INCREMENT,
    cancha_id   INT         NOT NULL,
    fecha       DATE        NOT NULL,
    hora        VARCHAR(20) NOT NULL,
    capacidad   INT         NOT NULL,
    estado      VARCHAR(30) NOT NULL DEFAULT 'Disponible',
    cliente_id  INT         NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cancha_id)  REFERENCES canchas(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE SET NULL,
    UNIQUE (cancha_id, fecha, hora),
    INDEX idx_reservas_fecha (fecha)
);
