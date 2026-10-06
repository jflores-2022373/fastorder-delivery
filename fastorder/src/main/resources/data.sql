-- Inserción de usuarios iniciales (Contraseñas cifradas con BCrypt)
-- ADMIN: admin@fastorder.com / Admin123*
-- REPARTIDOR: repartidor@fastorder.com / Repartidor123*
-- CLIENTE: cliente@fastorder.com / Cliente123*

INSERT INTO usuarios (nombre, apellido, correo, password, telefono, rol)
VALUES ('Administrador', 'Sistema', 'admin@fastorder.com', '$2a$10$XmQ8K8G6t2Q8K8G6t2Q8Ke8K8G6t2Q8K8G6t2Q8K8G6t2Q8K8G6t2', '55551111', 'ADMIN');

INSERT INTO usuarios (nombre, apellido, correo, password, telefono, rol)
VALUES ('Repartidor', 'Ejemplo', 'repartidor@fastorder.com', '$2a$10$XmQ8K8G6t2Q8K8G6t2Q8Ke8K8G6t2Q8K8G6t2Q8K8G6t2Q8K8G6t2', '55552222', 'REPARTIDOR');

INSERT INTO usuarios (nombre, apellido, correo, password, telefono, rol)
VALUES ('Cliente', 'Ejemplo', 'cliente@fastorder.com', '$2a$10$XmQ8K8G6t2Q8K8G6t2Q8Ke8K8G6t2Q8K8G6t2Q8K8G6t2Q8K8G6t2', '55554321', 'CLIENTE');

-- Inserción de categorías iniciales compatibles con tu proyecto
INSERT INTO categorias (nombre, descripcion)
VALUES ('Comida Rápida', 'Hamburguesas, papas fritas y más');