CREATE DATABASE IF NOT EXISTS Proyecto1 DEFAULT CHARACTER SET = 'utf8mb4';

USE Proyecto1;

CREATE TABLE IF NOT EXISTS cliente (
	dpi_o_pasaporte VARCHAR(20) PRIMARY KEY NOT NULL,
	nombre VARCHAR(300) NOT NULL,
	fecha DATE NOT NULL,
	telefono VARCHAR(20) NOT NULL,
	email VARCHAR(100) UNIQUE,
	nacionalidad VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS usuario (
	nombre VARCHAR(250) PRIMARY KEY NOT NULL,
	password VARCHAR(250) NOT NULL,
	rol ENUM('Atencion_al_Cliente', 'Operaciones', 'Administrador') DEFAULT 'Atencion_al_Cliente' NOT NULL
);

CREATE TABLE IF NOT EXISTS destino (
	nombre VARCHAR(250) PRIMARY KEY NOT NULL,
	pais VARCHAR(200) NOT NULL,
	descripcion VARCHAR(300) NOT NULL,
	clima_mejor_epoca VARCHAR(250),
	imagen VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS proveedor (
	nombre VARCHAR(250) PRIMARY KEY NOT NULL,
	pais VARCHAR(250) NOT NULL,
	tipo ENUM('Aerolinea','Hotel','Tour','Traslado','Otro') NOT NULL DEFAULT 'Otro',
	contacto VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS paquete_turistico (
	nombre VARCHAR(250) PRIMARY KEY NOT NULL,
	nombre_destino VARCHAR(250) NOT NULL,
	duracion INT NOT NULL,
	precio_publico DECIMAL(10,2) NOT NULL,
	capacidad_maxima INT NOT NULL,
	descripcion VARCHAR(300),
	estado BOOL DEFAULT 1,
	CONSTRAINT fk_destino FOREIGN KEY (nombre_destino) REFERENCES destino (nombre)
);


CREATE TABLE IF NOT EXISTS servicio_paquete (
	servicio_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	nombre_proveedor VARCHAR(250) NOT NULL,
	nombre_paquete VARCHAR(250) NOT NULL,
	descripcion VARCHAR(250) NOT NULL, 
	costo DECIMAL(10,2) NOT NULL,
	CONSTRAINT fk_proveedor FOREIGN KEY (nombre_proveedor) REFERENCES proveedor (nombre),
	CONSTRAINT fk_paquete FOREIGN KEY (nombre_paquete) REFERENCES paquete_turistico (nombre)
);

CREATE TABLE IF NOT EXISTS reservacion (
	id_numerico INT AUTO_INCREMENT UNIQUE,
	reservacion_id CHAR(9) PRIMARY KEY GENERATED ALWAYS AS (CONCAT('RES-', LPAD(id_numerico, 5, '0'))) STORED,
	nombre_paquete VARCHAR(250) NOT NULL,
	nombre_usuario VARCHAR(250) NOT NULL,
	fecha_viaje DATE NOT NULL,
	fecha_creacion DATE NOT NULL DEFAULT (CURRENT_DATE),
	cantidad_persona INT,
	costo_total DECIMAL(10,2),
	costo_agencia DECIMAL(10,2),
	estado ENUM('Pendiente','Confirmada','Cancelada','Completada') DEFAULT 'Pendiente',
	CONSTRAINT fk_paquete2 FOREIGN KEY (nombre_paquete) REFERENCES paquete_turistico (nombre),
	CONSTRAINT fk_usuario FOREIGN KEY (nombre_usuario) REFERENCES usuario (nombre)
);

CREATE TABLE IF NOT EXISTS reservacion_cliente (
	reservacion_id CHAR(9) NOT NULL,
	dpi_o_pasaporte VARCHAR(20) NOT NULL,
	CONSTRAINT pk_reservacion_cliente PRIMARY KEY (reservacion_id, dpi_o_pasaporte),
	CONSTRAINT fk_reservacion FOREIGN KEY (reservacion_id) REFERENCES reservacion (reservacion_id),
	CONSTRAINT fk_cliente FOREIGN KEY (dpi_o_pasaporte) REFERENCES cliente (dpi_o_pasaporte)
);

CREATE TABLE IF NOT EXISTS historial_pago (
	historial_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	reservacion_id CHAR(9) NOT NULL,
	monto DECIMAL(10,2) NOT NULL,
	metodo ENUM ('Efectivo','Tarjeta', 'Transferencia') DEFAULT 'Transferencia',
	fecha DATE DEFAULT (CURRENT_DATE),
	CONSTRAINT fk_reservacion2 FOREIGN KEY (reservacion_id) REFERENCES reservacion (reservacion_id)
);

CREATE TABLE IF NOT EXISTS reservacion_cancelada (
	reservacion_id CHAR(9) NOT NULL,
	fecha DATE NOT NULL DEFAULT (CURRENT_DATE),
	reembolso DECIMAL(10,2) NOT NULL,
	CONSTRAINT fk_reservacion3 FOREIGN KEY (reservacion_id) REFERENCES reservacion (reservacion_id)
)






