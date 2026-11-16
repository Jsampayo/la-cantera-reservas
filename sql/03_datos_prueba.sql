USE la_cantera;

INSERT IGNORE INTO clientes (nombre, correo, telefono, contrasena) VALUES
    ('pacho', 'pacho@correo.com', '3001234567', 'pancho123')
    ON DUPLICATE KEY UPDATE contrasena = 'pacho123';
