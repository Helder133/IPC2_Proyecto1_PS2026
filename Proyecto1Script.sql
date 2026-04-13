CREATE DATABASE IF NOT EXISTS Proyecto1 DEFAULT CHARACTER SET = 'utf8mb4';

USE Proyecto1;

CREATE TABLE IF NOT EXISTS cliente (
	cliente_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	dpi_o_pasaporte VARCHAR(20) UNIQUE NOT NULL,
	nombre VARCHAR(300) NOT NULL,
	fecha DATE NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	email VARCHAR(100),
	nacionalidad VARCHAR(100) NOT NULL
);

desc cliente

ALTER TABLE cliente MODIFY COLUMN 'email'
ALTER TABLE cliente DROP INDEX email;

CREATE TABLE IF NOT EXISTS usuario (
	usuario_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(250) UNIQUE NOT NULL,
	password VARCHAR(250) NOT NULL,
	rol ENUM('Atencion_al_Cliente', 'Operaciones', 'Administrador') DEFAULT 'Atencion_al_Cliente' NOT NULL,
	estado bool DEFAULT 1
); 

-- password = 123
INSERT INTO usuario (nombre, password, rol) VALUES ('admin', 'MTIz','Administrador');

CREATE TABLE IF NOT EXISTS destino (
	destino_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(250) UNIQUE NOT NULL,
	pais VARCHAR(200) NOT NULL,
	descripcion VARCHAR(300) NOT NULL,
	clima_mejor_epoca VARCHAR(250),
	imagen VARCHAR(300)Gerente
);

CREATE TABLE IF NOT EXISTS proveedor (
	proveedor_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(250) UNIQUE NOT NULL,
	pais VARCHAR(250) NOT NULL,
	tipo ENUM('Aerolinea','Hotel','Tour','Traslado','Otro') NOT NULL DEFAULT 'Otro',
	contacto VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS paquete_turistico (
	paquete_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	destino_id INT NOT NULL,
	nombre VARCHAR(250) NOT NULL UNIQUE,
	duracion INT NOT NULL,
	precio_publico DECIMAL(10,2) NOT NULL,
	capacidad_maxima INT NOT NULL,
	descripcion VARCHAR(300),
	estado BOOL DEFAULT 1,
	CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES destino (destino_id)
);

SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre AS nombre_destino, d.pais, d.descripcion, d.clima_mejor_epoca, d.imagen FROM paquete_turistico pt JOIN destino d ON pt.destino_id = d.destino_id
SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre AS nombre_destino, d.pais, d.descripcion, d.clima_mejor_epoca, d.imagen FROM paquete_turistico pt JOIN destino d ON pt.destino_id = d.destino_id WHERE pt.nombre LIKE "%Buceo%" 

CREATE TABLE IF NOT EXISTS servicio_paquete (
	proveedor_id INT NOT NULL,
	paquete_id INT NOT NULL,
	descripcion VARCHAR(250) NOT NULL, 
	costo DECIMAL(10,2) NOT NULL, 
	CONSTRAINT pk_service PRIMARY KEY (proveedor_id, paquete_id),
	CONSTRAINT fk_proveedor FOREIGN KEY (proveedor_id) REFERENCES proveedor (proveedor_id),
	CONSTRAINT fk_paquete FOREIGN KEY (paquete_id) REFERENCES paquete_turistico (paquete_id)
);

SELECT pt.precio_publico, COALESCE(SUM(sp.costo), 0) AS precio_agencia FROM paquete_turistico pt LEFT JOIN servicio_paquete sp ON pt.paquete_id = sp.paquete_id WHERE pt.paquete_id = 1 GROUP BY pt.paquete_id, pt.precio_publico;
SELECT sp.proveedor_id, sp.paquete_id, sp.descripcion, sp.costo, p.nombre AS nombre_proveedor, p.pais, p.tipo, p.contacto FROM servicio_paquete sp JOIN proveedor p  ON sp.proveedor_id = p.proveedor_id  WHERE sp.paquete_id = ?

CREATE TABLE IF NOT EXISTS reservacion (
	reservacion_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	paquete_id INT NOT NULL,
	usuario_id INT NOT NULL,
	fecha_viaje DATE NOT NULL,
	fecha_creacion DATE NOT NULL DEFAULT (CURRENT_DATE),
	cantidad_persona INT,
	costo_total DECIMAL(10,2),
	costo_agencia DECIMAL(10,2),
	estado ENUM('Pendiente','Confirmada','Cancelada','Completada') DEFAULT 'Pendiente',
	reembolso DECIMAL(10,2),
	fecha_cancelacion DATE,
	codigo_archivo CHAR(9),
	CONSTRAINT fk_paquete2 FOREIGN KEY (paquete_id) REFERENCES paquete_turistico (paquete_id),
	CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (usuario_id)
);

SELECT r.reservacion_id, r.paquete_id, r.usuario_id, r.fecha_viaje, r.fecha_creacion, r.cantidad_persona, r.costo_total, r.costo_agencia, r.estado AS estado_reservacion, r.reembolso, r.fecha_cancelacion, r.codigo_archivo, p.destino_id, p.nombre AS nombre_destino, p.duracion, p.precio_publico, p.capacidad_maxima, p.descripcion AS descripcion_paquete, p.estado AS estado_paquete, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen  FROM reservacion r JOIN paquete_turistico p ON r.paquete_id = p.paquete_id JOIN destino d ON p.destino_id = d.destino_id;

SELECT r.reservacion_id, r.paquete_id, r.usuario_id, r.fecha_viaje, r.fecha_creacion, r.cantidad_persona, r.costo_total, r.costo_agencia, r.estado, r.reembolso, r.fecha_cancelacion, r.codigo_archivo, p.destino_id, p.nombre AS nombre_destino, p.duracion, p.precio_publico, p.capacidad_maxima, p.descripcion AS descripcion_paquete, p.estado AS estado_paquete, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen FROM reservacion r JOIN paquete_turistico p ON r.paquete_id = p.paquete_id JOIN reservacion_cliente rc ON r.reservacion_id = rc.reservacion_id JOIN destino d ON p.destino_id = d.destino_id  WHERE rc.cliente_id = 2

CREATE TABLE IF NOT EXISTS reservacion_cliente (
	reservacion_id INT NOT NULL,
	cliente_id INT NOT NULL,
	CONSTRAINT pk_reservacion_cliente PRIMARY KEY (reservacion_id, cliente_id),
	CONSTRAINT fk_reservacion FOREIGN KEY (reservacion_id) REFERENCES reservacion (reservacion_id),
	CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES cliente (cliente_id)
);

SELECT COUNT(*) AS total_clientes FROM reservacion_cliente WHERE reservacion_id = ?;

CREATE TABLE IF NOT EXISTS historial_pago (
	historial_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	reservacion_id INT NOT NULL,
	monto DECIMAL(10,2) NOT NULL,
	metodo ENUM ('Efectivo','Tarjeta','Transferencia') DEFAULT 'Transferencia',
	fecha DATE DEFAULT (CURRENT_DATE),
	CONSTRAINT fk_reservacion2 FOREIGN KEY (reservacion_id) REFERENCES reservacion (reservacion_id)
);

SELECT * FROM paquete_turistico; 
SELECT * FROM proveedor;
SELECT * FROM servicio_paquete;
SELECT * FROM servicio_paquete WHERE paquete_id = 5 AND proveedor_id = 1
SELECT * FROM paquete_turistico

SELECT * FROM usuario
SELECT * FROM reservacion

SELECT SUM(monto) AS anticipo FROM historial_pago WHERE reservacion_id = 1

-- Para eliminar los datos basuras
SET FOREIGN_KEY_CHECKS = 0; 

TRUNCATE TABLE cliente;
TRUNCATE TABLE usuario;
TRUNCATE TABLE destino;
TRUNCATE TABLE proveedor;
TRUNCATE TABLE paquete_turistico;
TRUNCATE TABLE servicio_paquete;
TRUNCATE TABLE reservacion;
TRUNCATE TABLE reservacion_cliente;
TRUNCATE TABLE historial_pago;

SET FOREIGN_KEY_CHECKS = 1;

SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion,
    pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado,
    d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino,
    d.clima_mejor_epoca, d.imagen,
    -- Sumamos la cantidad_persona de tu tabla reservacion
    COALESCE(SUM(r.cantidad_persona), 0) AS total_ocupado 
    FROM paquete_turistico pt 
    JOIN destino d ON pt.destino_id = d.destino_id 
    -- Hacemos el JOIN con las reservaciones futuras y que NO estén canceladas
    LEFT JOIN reservacion r ON pt.paquete_id = r.paquete_id
        AND r.fecha_viaje >= CURRENT_DATE 
        AND r.estado IN ('Pendiente', 'Confirmada')
    WHERE pt.paquete_id = 1
    GROUP BY pt.paquete_id, pt.destino_id, pt.nombre, pt.duracion, pt.precio_publico,
    pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre, d.pais, d.descripcion,
    d.clima_mejor_epoca, d.imagen 
    
;    
SELECT pt.paquete_id, pt.destino_id, pt.nombre AS nombre_paquete, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre AS nombre_destino, d.pais, d.descripcion AS descripcion_destino, d.clima_mejor_epoca, d.imagen, COALESCE(SUM(r.cantidad_persona), 0) AS total_ocupado FROM paquete_turistico pt JOIN destino d ON pt.destino_id = d.destino_id LEFT JOIN reservacion r ON pt.paquete_id = r.paquete_id AND r.fecha_viaje >= CURRENT_DATE AND r.estado IN ('Pendiente', 'Confirmada') GROUP BY pt.paquete_id, pt.destino_id, pt.nombre, pt.duracion, pt.precio_publico, pt.capacidad_maxima, pt.descripcion, pt.estado, d.nombre, d.pais, d.descripcion, d.clima_mejor_epoca, d.imagen 
-- 1
SELECT r.reservacion_id, pt.nombre AS paquete, r.cantidad_persona AS pasajeros, u.nombre AS agente, r.costo_total FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.estado = 'Confirmada' AND r.fecha_creacion BETWEEN '2025-12-01' AND '2026-04-14';
BETWEEN '2025-12-01' AND '2026-04-14';
-- 2
SELECT r.reservacion_id, pt.nombre AS paquete, r.fecha_cancelacion, COALESCE(r.reembolso, 0) AS monto_reembolsado, (r.costo_agencia - (COALESCE(SUM(hp.monto), 0) - COALESCE(r.reembolso, 0))) AS perdida_agencia FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id LEFT JOIN historial_pago hp ON r.reservacion_id = hp.reservacion_id WHERE r.estado = 'Cancelada' AND r.fecha_cancelacion BETWEEN ? AND ? GROUP BY r.reservacion_id, pt.nombre, r.fecha_cancelacion, r.reembolso, r.costo_agencia;
-- 3
SELECT SUM(total_pagado - costo_agencia) AS ganancia_bruta, SUM(reembolso_aplicado) AS total_reembolsos, SUM((total_pagado - costo_agencia) - reembolso_aplicado) AS ganancia_neta FROM ( SELECT  r.reservacion_id, COALESCE(r.costo_agencia, 0) AS costo_agencia, COALESCE(r.reembolso, 0) AS reembolso_aplicado, (SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id) AS total_pagado FROM reservacion r WHERE r.fecha_creacion BETWEEN ? AND ?) AS resumen_financiero;
-- 4.1
SELECT u.usuario_id, u.nombre, SUM(r.costo_total) AS total_vendido FROM reservacion r JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? GROUP BY u.usuario_id, u.nombre ORDER BY total_vendido DESC LIMIT 1;
-- 4.2
SELECT r.reservacion_id, pt.nombre AS paquete_nombre, r.fecha_creacion, r.fecha_viaje, r.cantidad_persona, r.costo_total FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id WHERE r.usuario_id = ? AND r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ?;
-- 5
SELECT u.usuario_id, u.nombre AS agente, SUM((resumen.total_pagado - resumen.costo_agencia) - resumen.reembolso_aplicado) AS ganancia_neta FROM ( SELECT r.reservacion_id, r.usuario_id, COALESCE(r.costo_agencia, 0) AS costo_agencia, COALESCE(r.reembolso, 0) AS reembolso_aplicado, (SELECT COALESCE(SUM(hp.monto), 0) FROM historial_pago hp WHERE hp.reservacion_id = r.reservacion_id) AS total_pagado FROM reservacion r WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? ) AS resumen JOIN usuario u ON resumen.usuario_id = u.usuario_id GROUP BY u.usuario_id, u.nombre ORDER BY ganancia_neta DESC LIMIT 1;
-- 6.1
SELECT pt.paquete_id, pt.nombre, COUNT(r.reservacion_id) AS total_reservaciones FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? GROUP BY pt.paquete_id, pt.nombre ORDER BY total_reservaciones DESC LIMIT 1;
-- 6.2
SELECT r.reservacion_id, u.nombre AS agente, r.fecha_creacion, r.fecha_viaje, r.cantidad_persona, r.costo_total FROM reservacion r JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.paquete_id = ? AND r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ?;
-- 7.1
SELECT pt.paquete_id, pt.nombre, COUNT(r.reservacion_id) AS total_reservaciones FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ? GROUP BY pt.paquete_id, pt.nombre ORDER BY total_reservaciones ASC LIMIT 1;
-- 7.2
SELECT r.reservacion_id, u.nombre AS agente, r.fecha_creacion, r.fecha_viaje, r.cantidad_persona, r.costo_total FROM reservacion r JOIN usuario u ON r.usuario_id = u.usuario_id WHERE r.paquete_id = ? AND r.estado IN ('Confirmada', 'Completada') AND r.fecha_creacion BETWEEN ? AND ?;
-- 8 
SELECT d.nombre AS destino, COUNT(r.reservacion_id) AS cantidad_viajes, SUM(r.cantidad_persona) AS total_turistas FROM reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id JOIN destino d ON pt.destino_id = d.destino_id WHERE r.estado IN ('Confirmada', 'Completada') AND r.fecha_viaje BETWEEN '2025-12-01' AND '2026-04-14' GROUP BY d.destino_id, d.nombre ORDER BY cantidad_viajes DESC;




select r.*, d.nombre from reservacion r JOIN paquete_turistico pt ON r.paquete_id = pt.paquete_id join destino d ON pt.destino_id = d.destino_id where r.estado IN ('Confirmada', 'Completada') AND r.fecha_viaje BETWEEN '2025-12-01' AND '2026-04-14'




