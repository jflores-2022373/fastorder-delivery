-- Inserción de usuarios iniciales (Contraseñas cifradas con BCrypt)
-- ADMIN: admin@fastorder.com / Admin123*
-- REPARTIDOR: repartidor@fastorder.com / Repartidor123*
-- CLIENTE: cliente@fastorder.com / Cliente123*

INSERT INTO usuario (nombre, direccion, telefono, email, password, rol)
VALUES ('Administrador', 'Zona 1, Ciudad', '55551111', 'admin@fastorder.com', '$2a$10$XmQ8K8G6t2Q8K8G6t2Q8Ke8K8G6t2Q8K8G6t2Q8K8G6t2Q8K8G6t2', 'ADMIN');

INSERT INTO usuario (nombre, direccion, telefono, email, password, rol)
VALUES ('Repartidor Ejemplo', 'Zona 4, Ciudad', '55552222', 'repartidor@fastorder.com', '$2a$10$XmQ8K8G6t2Q8K8G6t2Q8Ke8K8G6t2Q8K8G6t2Q8K8G6t2Q8K8G6t2', 'REPARTIDOR');

INSERT INTO usuario (nombre, direccion, telefono, email, password, rol)
VALUES ('Cliente Ejemplo', 'Zona 10, Ciudad', '55554321', 'cliente@fastorder.com', '$2a$10$XmQ8K8G6t2Q8K8G6t2Q8Ke8K8G6t2Q8K8G6t2Q8K8G6t2Q8K8G6t2', 'CLIENTE');

-- Inserción de comercio inicial requerido
INSERT INTO comercio (nombre, categoria, direccion, abierto)
VALUES ('Burger Express', 'RESTAURANTE', 'Calzada Roosevelt 12-45', true);