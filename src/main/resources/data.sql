-- Insertar usuarios
INSERT INTO users (id, name, email, password, created, modified, last_login, token, is_active)
VALUES (1, 'John Doe', 'john.doe@example.com', 'password', '2022-01-01', '2022-01-01', '2022-01-01', 'token123', true);

-- Insertar teléfonos asociados al usuario
INSERT INTO phones (id, number, city_code, country_code, user_id)
VALUES (1, '1234567', '1', '57', 1);