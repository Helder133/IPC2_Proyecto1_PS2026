CREATE DATABASE IF NOT EXISTS Proyecto1 DEFAULT CHARACTER SET = 'utf8mb4';

USE Proyecto1;

CREATE TABLE IF NOT EXISTS cliente (
	cliente_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	dpi_o_pasaporte VARCHAR(20) UNIQUE NOT NULL,
	nombre VARCHAR(300) NOT NULL,
	fecha DATE NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	email VARCHAR(100) UNIQUE,
	nacionalidad VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
	usuario_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(250) UNIQUE NOT NULL,
	password VARCHAR(250) NOT NULL,
	rol ENUM('Atencion_al_Cliente', 'Operaciones', 'Administrador') DEFAULT 'Atencion_al_Cliente' NOT NULL
);

-- password = 123
INSERT INTO usuario (nombre, password, rol) VALUES ('admin', 'MTIz','Administrador');

CREATE TABLE IF NOT EXISTS destino (
	destino_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	nombre VARCHAR(250) UNIQUE NOT NULL,
	pais VARCHAR(200) NOT NULL,
	descripcion VARCHAR(300) NOT NULL,
	clima_mejor_epoca VARCHAR(250),
	imagen VARCHAR(300)
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
