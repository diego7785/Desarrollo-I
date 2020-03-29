-- POSTGRESQL VERSION
-- PostgreSQL 12.2 - 64-bits
-- ***********************************************************************************
-- ******************************BASE DE DATOS V5*************************************

DROP TABLE IF EXISTS Bill;
DROP TABLE IF EXISTS Lines;
DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS DB_version;

DROP SEQUENCE IF EXISTS secuencia1;
CREATE SEQUENCE secuencia1;

-- ESTA TABLA ES PARA LLEVAR EL CONTROL DE QUE SE ESTÁ UTILIZANDO LA VERSIÓN
-- ADECUADA CON LA APLICACIÓN Y EVITAR CONFLICTOS ENTRE ELLAS.
CREATE TABLE DB_version (
	version INT,
	CONSTRAINT pk_db_version PRIMARY KEY (version)
);
INSERT INTO DB_version VALUES(4);

CREATE TABLE Roles (
	id INT,
	name VARCHAR(25) NOT NULL,
	description VARCHAR(200),
	CONSTRAINT pk_role PRIMARY KEY (id)
);
CREATE TABLE Users (
	id INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	roleID INT NOT NULL,
	status BOOLEAN NOT NULL DEFAULT TRUE,
	password VARCHAR(20) NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY (id),
	CONSTRAINT fk_userrole FOREIGN KEY (roleID) REFERENCES Roles(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE Customer (
	id INT NOT NULL,
	name VARCHAR(50) NOT NULL,
	type VARCHAR(25) NOT NULL,
	address VARCHAR(30),
	city VARCHAR(40),
	email VARCHAR(40),
	CONSTRAINT pk_customer PRIMARY KEY (id)
);
CREATE TABLE Plan (
	id INT,
	cost INT NOT NULL,
	minutes INT NOT NULL,
	dataPlan INT NOT NULL,
	messages INT NOT NULL,
	data_wpp INT NOT NULL,
	minutes_wpp INT NOT NULL,
	data_fb INT NOT NULL,
	data_waze INT NOT NULL,
	minutes_international INT NOT NULL,
	data_shared INT NOT NULL,
	description VARCHAR(200),
	CONSTRAINT pk_plan PRIMARY KEY (id)
);
CREATE TABLE Lines (
	number VARCHAR(10),
	customerID INT,
	planID INT,
	userID INT NOT NULL,
	status BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT pk_line PRIMARY KEY (number),
	CONSTRAINT fk_linecustomer FOREIGN KEY (customerID) REFERENCES Customer(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_lineplan FOREIGN KEY (planID) REFERENCES Plan(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_lineuser FOREIGN KEY (userID) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE Bill (
	id INT DEFAULT NEXTVAL('secuencia1'),
	data_consuption DECIMAL(7,2) NOT NULL,
	minutes_consuption INT NOT NULL,
	sms_consuption INT NOT NULL,
	data_wpp DECIMAL(7,2) NOT NULL,
	minutes_wpp INT NOT NULL,
	data_fb DECIMAL(7,2) NOT NULL,
	data_waze DECIMAL(7,2) NOT NULL,
	minutes_international INT NOT NULL,
	data_shared DECIMAL(7,2) NOT NULL,
	place_payment VARCHAR(20),
	price INT NOT NULL,
	lineNumber VARCHAR(10) NOT NULL,
	date date NOT NULL,
	userID INT NOT NULL,
	numberForPay VARCHAR(28) NOT NULL,
	CONSTRAINT pk_comsumption PRIMARY KEY (id),
	CONSTRAINT fk_consumptionline FOREIGN KEY (lineNumber) REFERENCES Lines(number) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_billuser FOREIGN KEY (userID) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- **************************VISTAS**********************************
-- ******************************************************************
-- ******************PROCEDIMIENTOS ALMACENADOS**********************
-- ******************************************************************
-- *******************INSERTS PERMANENTES*******************
INSERT INTO Roles VALUES
(1, 'Administrador','Descripcion del administrador'),
(2, 'Gerente','Descripcion del gerente'),
(3, 'Operador','Descripcion del operador');

INSERT INTO Plan VALUES
(1,30900,250,1024,100,250,500,250,0,0,0,'...'),
(2,39900,150,4608,100,250,300,250,0,0,0,'...'),
(3,49900,300,8704,100,500,600,500,0,0,0,'...'),
(4,65000,1000,20480,-1,-1,-1,-1,-1,0,0,'...'),
(5,100000,-1,30720,-1,0,0,0,0,-1,-1,'...');
-- NOTA: EL -1 REPRESENTA ILIMITADO EN EL PLAN
-- ******************************************************************
-- ******************INSERTS DE PRUEBAS**********************
INSERT INTO Users (id,name,roleID,password) VALUES
(123,'Luis',3,'candamil'),
(234,'Juliana',3,'verapamil'),
(345,'Kevin',3,'furosemida'),
(456,'Andres',2,'acetaminofen'),
(567,'Pablo',3,'ibuprofeno'),
(789,'Karen',2,'losartan'),
(890,'Francisco',3,'hidroclorotiazida'),
(987,'Diana',3,'moringa'),
(876,'Paola',3,'omeprazol'),
(765,'Ricardo',1,'noloserick');

INSERT INTO Customer(id, name, type, address, city) VALUES(1007151952, 'Diego Bonilla', 'Natural','Cra 15 # 1 sur 16','Santander de Quilichao');
INSERT INTO Lines VALUES('3219234114', 1007151952, 1, 456, true);
INSERT INTO Bill VALUES(1, 100, 200, 50, 150, 400, 160, 0, 0, 0, 'Bancolombia', 30900, '3219234114', '2020/03/30',123,'1007151952321923411420200330');

INSERT INTO Customer(id, name, type, email)  VALUES(1007151295, 'Andrés Viáfara', 'Natural','dianbovi@hotmail.com');
INSERT INTO Lines VALUES('3107356146', 1007151295, 4, 456, true);
INSERT INTO Bill VALUES(2, 10000, 800, 1050, 5000, 200, 8060, 1024, 0, 0, 'Davivienda', 65000, '3107356146', '2020/03/30', 123,'1007151295310735614620200330');

INSERT INTO Customer(id, name, type, address, city)  VALUES(1006106575, 'Pablo Esteban', 'Natural','Noc','Piendamó');
INSERT INTO Lines VALUES('3217219953', 1006106575, 5, 456, true);
INSERT INTO Bill VALUES(3, 25000,8000, 4000,7000,400,10060,2048, 60, 500, 'Davivienda', 100000, '3217219953', '2020/03/30',123,'1006106575321721995320200330');

INSERT INTO Bill VALUES(4, 110, 210, 60, 160, 410, 170, 0, 0, 0, 'Bancolombia', 30900, '3219234114', '2020/02/28',123,'1007151952321923411420200228');