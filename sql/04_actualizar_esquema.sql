

USE la_cantera;


SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'clientes' AND COLUMN_NAME = 'nombre') = 0,
    'ALTER TABLE clientes ADD COLUMN nombre VARCHAR(50) NOT NULL DEFAULT ''''', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'clientes' AND COLUMN_NAME = 'correo') = 0,
    'ALTER TABLE clientes ADD COLUMN correo VARCHAR(100) NULL AFTER nombre', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'clientes' AND COLUMN_NAME = 'telefono') = 0,
    'ALTER TABLE clientes ADD COLUMN telefono VARCHAR(15) NOT NULL DEFAULT ''0000000''', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;


SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'clientes' AND COLUMN_NAME = 'contrasena') = 0,
    'ALTER TABLE clientes ADD COLUMN contrasena VARCHAR(100) NOT NULL DEFAULT ''''', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
ALTER TABLE clientes ALTER COLUMN contrasena DROP DEFAULT;

ALTER TABLE clientes ALTER COLUMN nombre DROP DEFAULT;
ALTER TABLE clientes ALTER COLUMN telefono DROP DEFAULT;


UPDATE clientes SET correo = CONCAT('cliente', id, '@correo.com') WHERE correo IS NULL OR correo = '';
ALTER TABLE clientes MODIFY correo VARCHAR(100) NOT NULL;


SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'clientes'
      AND COLUMN_NAME = 'correo' AND NON_UNIQUE = 0) = 0,
    'ALTER TABLE clientes ADD UNIQUE (correo)', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;


SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'canchas' AND COLUMN_NAME = 'tipo') = 0,
    'ALTER TABLE canchas ADD COLUMN tipo VARCHAR(20) NOT NULL DEFAULT ''Sin tipo''', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'canchas' AND COLUMN_NAME = 'capacidad') = 0,
    'ALTER TABLE canchas ADD COLUMN capacidad INT NOT NULL DEFAULT 0', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;


SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'reservas' AND COLUMN_NAME = 'capacidad') = 0,
    'ALTER TABLE reservas ADD COLUMN capacidad INT NOT NULL DEFAULT 0', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'reservas' AND COLUMN_NAME = 'estado') = 0,
    'ALTER TABLE reservas ADD COLUMN estado VARCHAR(30) NOT NULL DEFAULT ''Disponible''', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'reservas' AND COLUMN_NAME = 'cliente_id') = 0,
    'ALTER TABLE reservas ADD COLUMN cliente_id INT NULL', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;


SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'reservas'
      AND COLUMN_NAME = 'fecha' AND SEQ_IN_INDEX = 1) = 0,
    'ALTER TABLE reservas ADD INDEX idx_reservas_fecha (fecha)', 'DO 0');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;


SELECT 'Esquema actualizado. Estructura actual:' AS aviso;
DESCRIBE clientes;
DESCRIBE canchas;
DESCRIBE reservas;
select * from reservas;
