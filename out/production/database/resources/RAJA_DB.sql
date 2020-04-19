-- POSTGRESQL VERSION
-- PostgreSQL 12.2 - 64-bits
-- ***********************************************************************************
-- ******************************BASE DE DATOS V6*************************************

DROP TABLE IF EXISTS Bill;
DROP TABLE IF EXISTS Lines;
DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Type_IDCustomer;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS DB_version;

DROP SEQUENCE IF EXISTS secuencia1;
DROP SEQUENCE IF EXISTS seq_id_bill;
DROP SEQUENCE IF EXISTS seq_id_role;
DROP SEQUENCE IF EXISTS seq_id_typeid;
DROP SEQUENCE IF EXISTS seq_id_plan;
CREATE SEQUENCE seq_id_bill;
CREATE SEQUENCE seq_id_role;
CREATE SEQUENCE seq_id_typeid;
CREATE SEQUENCE seq_id_plan;

-- ESTA TABLA ES PARA LLEVAR EL CONTROL DE QUE SE ESTÁ UTILIZANDO LA VERSIÓN
-- ADECUADA CON LA APLICACIÓN Y EVITAR CONFLICTOS ENTRE ELLAS.
CREATE TABLE DB_version (
	version INT,
	CONSTRAINT pk_db_version PRIMARY KEY (version)
);
INSERT INTO DB_version VALUES(6);

CREATE TABLE Roles (
	id INT,
	name VARCHAR(25) NOT NULL,
	description VARCHAR(200),
	CONSTRAINT pk_role PRIMARY KEY (id)
);
CREATE TABLE Users (
	id VARCHAR (20) NOT NULL,
	name VARCHAR(50) NOT NULL,
	roleID INT NOT NULL,
	status BOOLEAN NOT NULL DEFAULT TRUE,
	password VARCHAR(20) NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY (id),
	CONSTRAINT fk_userrole FOREIGN KEY (roleID) 
		REFERENCES Roles(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE Type_IDCustomer(
	id INT DEFAULT NEXTVAL('seq_id_typeid'),
	name VARCHAR(25) NOT NULL,
	CONSTRAINT pk_type_user PRIMARY KEY (id)
);
CREATE TABLE Customer (
	id VARCHAR(20),
	typeID INT DEFAULT 1,
	name VARCHAR(50) NOT NULL,
	type VARCHAR(25) NOT NULL,
	address VARCHAR(30),
	city VARCHAR(40),
	email VARCHAR(40),
	CONSTRAINT pk_customer PRIMARY KEY (id,typeID),
	CONSTRAINT fk_customertypeid FOREIGN KEY (typeID) 
		REFERENCES Type_IDCustomer(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE Plan (
	id INT DEFAULT NEXTVAL('seq_id_plan'),
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
	customerID VARCHAR(20),
	customerIDtype INT DEFAULT 1,
	planID INT,
	userID VARCHAR(20) NOT NULL,
	status BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT pk_line PRIMARY KEY (number),
	CONSTRAINT fk_linecustomer FOREIGN KEY (customerID,customerIDtype) 
		REFERENCES Customer(id,typeID) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_lineplan FOREIGN KEY (planID) 
		REFERENCES Plan(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_lineuser FOREIGN KEY (userID) 
		REFERENCES Users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE Bill (
	id INT DEFAULT NEXTVAL('seq_id_bill'),
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
	userID VARCHAR(20) NOT NULL,
	numberForPay VARCHAR(28) NOT NULL,
	status BOOLEAN NOT NULL DEFAULT FALSE,
	CONSTRAINT pk_comsumption PRIMARY KEY (id),
	CONSTRAINT fk_consumptionline FOREIGN KEY (lineNumber) 
		REFERENCES Lines(number) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_billuser FOREIGN KEY (userID) 
		REFERENCES Users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
-- *******************************************************************
-- **************************VISTAS**********************************
-- ******************************************************************
-- ******************PROCEDIMIENTOS ALMACENADOS**********************
CREATE OR REPLACE FUNCTION codificate_role() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_role');
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tr_codificate_role ON Roles;
CREATE TRIGGER tr_codificate_role BEFORE INSERT 
ON Roles FOR EACH ROW 
EXECUTE PROCEDURE codificate_role();

CREATE OR REPLACE FUNCTION codificate_plan() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_plan');
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tr_codificate_plan ON Plan;
CREATE TRIGGER tr_codificate_plan BEFORE INSERT 
ON Plan FOR EACH ROW 
EXECUTE PROCEDURE codificate_plan();

CREATE OR REPLACE FUNCTION codificate_bill() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_bill');
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tr_codificate_bill ON Bill;
CREATE TRIGGER tr_codificate_bill BEFORE INSERT 
ON Bill FOR EACH ROW 
EXECUTE PROCEDURE codificate_bill();

CREATE OR REPLACE FUNCTION codificate_typeid() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_typeid');
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS tr_codificate_typeid ON Type_IDCustomer;
CREATE TRIGGER tr_codificate_typeid BEFORE INSERT 
ON Type_IDCustomer FOR EACH ROW 
EXECUTE PROCEDURE codificate_typeid();
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

INSERT INTO Type_IDCustomer VALUES
(1,'Cedula de Ciudadanía'),
(2,'NIT'),
(3,'Cedula de Extrangería');
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

INSERT INTO Customer(id, name, type, address, city) 
VALUES(1007151952, 'Diego Bonilla', 'Natural','Cra 15 # 1 sur 16','Santander de Quilichao');
INSERT INTO Lines 
VALUES('3219234114', 1007151952, 1 ,1, 456, true);
INSERT INTO Bill 
VALUES(1, 100, 200, 50, 150, 400, 160, 0, 0, 0, 'Bancolombia', 30900, '3219234114', '2020/03/30',123,'1007151952321923411420200330', false);

INSERT INTO Customer(id, name, type, email)  
VALUES(1007151295, 'Andrés Viáfara', 'Natural','dianbovi@hotmail.com');
INSERT INTO Lines 
VALUES('3107356146', 1007151295, 1,4, 456, true);
INSERT INTO Bill 
VALUES(2, 10000, 800, 1050, 5000, 200, 8060, 1024, 0, 0, 'Davivienda', 65000, '3107356146', '2020/03/30', 123,'1007151295310735614620200330', false);

INSERT INTO Customer(id, name, type, address, city)  
VALUES(1006106575, 'Pablo Esteban', 'Natural','Noc','Piendamó');
INSERT INTO Lines 
VALUES('3217219953', 1006106575, 1,5, 456, true);
INSERT INTO Bill 
VALUES(3, 25000,8000, 4000,7000,400,10060,2048, 60, 500, 'Davivienda', 100000, '3217219953', '2020/03/30',123,'1006106575321721995320200330', false);

INSERT INTO Bill 
VALUES(4, 110, 210, 60, 160, 410, 170, 0, 0, 0, 'Bancolombia', 30900, '3219234114', '2020/02/28',123,'1007151952321923411420200228', false);
INSERT INTO Bill
VALUES(5, 120, 230, 150, 280, 490, 190, 0, 0, 0, 'Bancolombia', 30900, '3219234114', '2020/04/30',123,'1007151952321923411420200430', false);