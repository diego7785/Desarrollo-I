-- POSTGRESQL VERSION
-- PostgreSQL 12.2 - 64-bits
-- ***********************************************************************************
-- ******************************BASE DE DATOS V7*************************************
DROP TRIGGER IF EXISTS tr_codificate_role ON Roles;
DROP TRIGGER IF EXISTS tr_codificate_plan ON Plan;
DROP TRIGGER IF EXISTS tr_codificate_typeid ON Type_IDCustomer;
DROP TRIGGER IF EXISTS tr_generate_bill ON Bill;
DROP TRIGGER IF EXISTS tr_extend_plan ON Bill;


DROP FUNCTION IF EXISTS codificate_role;
DROP FUNCTION IF EXISTS codificate_plan;
DROP FUNCTION IF EXISTS codificate_typeid;
DROP FUNCTION IF EXISTS generate_bill;
DROP FUNCTION IF EXISTS generate_all_bills;
DROP FUNCTION IF EXISTS extend_plan;

DROP TABLE IF EXISTS Bill;
DROP TABLE IF EXISTS Lines;
DROP TABLE IF EXISTS Plan;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Type_IDCustomer;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Roles;
DROP TABLE IF EXISTS DB_version;

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
INSERT INTO DB_version VALUES(7);

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
	id INT,
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
	additional_minute INT,
	additional_minutewp INT,
	CONSTRAINT pk_plan PRIMARY KEY (id)
);
CREATE TABLE Lines (
	number VARCHAR(10),
	customerID VARCHAR(20),
	customerIDtype INT DEFAULT 1,
	planID INT,
	userID VARCHAR(20) NOT NULL,
	status BOOLEAN NOT NULL DEFAULT TRUE,
	physicalBill BOOLEAN DEFAULT FALSE,
	CONSTRAINT pk_line PRIMARY KEY (number),
	CONSTRAINT fk_linecustomer FOREIGN KEY (customerID,customerIDtype) 
		REFERENCES Customer(id,typeID) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_lineplan FOREIGN KEY (planID) 
		REFERENCES Plan(id) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_lineuser FOREIGN KEY (userID) 
		REFERENCES Users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE Bill (
	id SERIAL,
	data_consuption DECIMAL(7,2),
	minutes_consuption INT,
	sms_consuption INT,
	data_wpp DECIMAL(7,2),
	minutes_wpp INT,
	data_fb DECIMAL(7,2),
	data_waze DECIMAL(7,2),
	minutes_international INT,
	data_shared DECIMAL(7,2),
	place_payment VARCHAR(20),
	price INT,
	plan_ext INT DEFAULT 0,
	lineNumber VARCHAR(10) NOT NULL,
	date_pdf DATE,
	date_pay DATE,
	userID VARCHAR(20),
	numberForPay VARCHAR(28),
	status BOOLEAN DEFAULT FALSE,
	CONSTRAINT pk_comsumption PRIMARY KEY (id),
	CONSTRAINT fk_consumptionline FOREIGN KEY (lineNumber) 
		REFERENCES Lines(number) ON UPDATE CASCADE ON DELETE RESTRICT,
	CONSTRAINT fk_billuser FOREIGN KEY (userID) 
		REFERENCES Users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);
-- date_pdf ES LA FECHA QUE SE GENERARA EL PDF Y date_pay ES LA FECHA DE CORTE

-- *******************************************************************
-- **************************VISTAS**********************************
-- ******************************************************************
-- ******************PROCEDIMIENTOS ALMACENADOS**********************
CREATE FUNCTION codificate_role() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_role');
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_codificate_role BEFORE INSERT 
ON Roles FOR EACH ROW 
EXECUTE PROCEDURE codificate_role();


CREATE FUNCTION codificate_plan() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_plan');
	IF NEW.id = 5 THEN
	    NEW.additional_minute := 0;
	 ELSE
	    NEW.additional_minute := NEW.cost / NEW.minutes;
	 END IF;
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_codificate_plan BEFORE INSERT 
ON Plan FOR EACH ROW 
EXECUTE PROCEDURE codificate_plan();


CREATE FUNCTION codificate_typeid() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.id := NEXTVAL('seq_id_typeid');
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_codificate_typeid BEFORE INSERT 
ON Type_IDCustomer FOR EACH ROW 
EXECUTE PROCEDURE codificate_typeid();

CREATE FUNCTION generate_bill() RETURNS TRIGGER AS $$
DECLARE
	Fecha_C DATE;
	id_C VARCHAR;
BEGIN
	Fecha_C := to_date(to_char(LOCALTIMESTAMP - INTERVAL '5 hours','YYYY-MM-DD'),'YYYY-MM-DD');
	IF NEW.id IS NULL THEN
		NEW.date_pdf := to_date(to_char(Fecha_C,'YYYY-')|| extract(month from Fecha_C)+2 || '-01','YYYY-MM-DD')-1;
		NEW.date_pay := to_date(to_char(Fecha_C,'YYYY-')|| extract(month from Fecha_C)+2 || '-05','YYYY-MM-DD');
	ELSE
		NEW.date_pdf := to_date(to_char(Fecha_C,'YYYY-')|| extract(month from Fecha_C)+1 || '-01','YYYY-MM-DD')-1;
		NEW.date_pay := to_date(to_char(Fecha_C,'YYYY-')|| extract(month from Fecha_C)+1 || '-05','YYYY-MM-DD');
	END IF;
	NEW.minutes_consuption := (SELECT Plan.minutes FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.data_consuption := (SELECT Plan.dataplan FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.sms_consuption := (SELECT Plan.messages FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.data_wpp := (SELECT Plan.data_wpp FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.minutes_wpp := (SELECT Plan.minutes_wpp FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.data_fb := (SELECT Plan.data_fb FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.data_waze := (SELECT Plan.data_waze FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.minutes_international := (SELECT Plan.minutes_international FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.data_shared := (SELECT Plan.data_shared FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	NEW.price := (SELECT Plan.cost FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
	id_C := (SELECT CustomerID FROM Lines WHERE number = NEW.lineNumber);
	NEW.id := NEXTVAL('seq_id_bill');
	NEW.numberForPay := (id_C||NEW.lineNumber||to_char(NEW.date_pdf,'YYYYMMDD'));
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_generate_bill BEFORE INSERT 
ON Bill FOR EACH ROW 
EXECUTE PROCEDURE generate_bill();


CREATE FUNCTION extend_plan() RETURNS TRIGGER AS $$
BEGIN
	IF NEW.plan_ext = 1 THEN
		NEW.plan_ext := -1;
		NEW.price := OLD.price +
			((SELECT Plan.cost FROM Lines INNER JOIN Plan 
				ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber)*0.5);
		IF OLD.minutes_consuption != -1 THEN
			NEW.minutes_consuption := OLD.minutes_consuption + 
				(SELECT Plan.minutes FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.data_consuption != -1 THEN
			NEW.data_consuption := OLD.data_consuption +
				(SELECT Plan.dataplan FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.sms_consuption != -1 THEN
			NEW.sms_consuption := OLD.sms_consuption +
				(SELECT Plan.messages FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.data_wpp != -1 THEN
			NEW.data_wpp := OLD.data_wpp +
				(SELECT Plan.data_wpp FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.minutes_wpp != -1 THEN
			NEW.minutes_wpp := OLD.minutes_wpp + 
				(SELECT Plan.minutes_wpp FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.data_fb != -1 THEN
			NEW.data_fb := OLD.data_fb +
				(SELECT Plan.data_fb FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.data_waze != -1 THEN
			NEW.data_waze := OLD.data_waze +
				(SELECT Plan.data_waze FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.minutes_international != -1 THEN
			NEW.minutes_international := OLD.minutes_international +
				(SELECT Plan.minutes_international FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
		IF OLD.data_shared != -1 THEN
			NEW.data_shared := OLD.data_shared +
				(SELECT Plan.data_shared FROM Lines INNER JOIN Plan 
					ON Lines.planID = Plan.id WHERE Lines.number = NEW.lineNumber);
		END IF;
	END IF;	
	RETURN NEW;
END
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_extend_plan BEFORE UPDATE 
ON Bill FOR EACH ROW 
EXECUTE PROCEDURE extend_plan();


CREATE FUNCTION generate_all_bills () RETURNS BOOLEAN AS $$
DECLARE
	mynumber VARCHAR;
BEGIN
	FOR mynumber IN
		SELECT number FROM Lines
	LOOP
		INSERT INTO Bill (lineNumber) VALUES (mynumber);
	END LOOP;
	RETURN TRUE;
END
$$ LANGUAGE plpgsql;
-- ******************************************************************
-- *******************INSERTS PERMANENTES*******************
INSERT INTO Roles (name, description) VALUES
('Administrador','Descripcion del administrador'),
('Gerente','Descripcion del gerente'),
('Operador','Descripcion del operador');

INSERT INTO Plan VALUES
(1,30900,250,1024,100,250,500,250,0,0,0,'...',0),
(2,39900,150,4608,100,250,300,250,0,0,0,'...',0),
(3,49900,300,8704,100,500,600,500,0,0,0,'...',0),
(4,65000,1000,20480,-1,-1,-1,-1,-1,0,0,'...',0),
(5,100000,-1,30720,-1,0,0,0,0,-1,3072,'...',0);
-- NOTA: EL -1 REPRESENTA ILIMITADO EN EL PLAN

INSERT INTO Type_IDCustomer (name) VALUES
('Cedula de Ciudadanía'),
('NIT'),
('Cedula de Extranjería');
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

INSERT INTO Customer(id, name, type, address, city, email) 
VALUES('1007151952', 'Diego Bonilla', 'Natural','Cra 15 # 1 sur 16','Santander de Quilichao','lolerjp30@gmail.com');
INSERT INTO Lines (number,customerID,planID, userID)
VALUES('3219234114', '1007151952', 1, 456);
INSERT INTO Bill (lineNumber)
VALUES ('3219234114');

INSERT INTO Customer(id, name, type, email)  
VALUES('1007151295', 'Andrés Viáfara', 'Natural','dianbovi@hotmail.com');
INSERT INTO Lines (number,customerID,planID, userID, physicalBill)
VALUES('3107356146', '1007151295',4, 456, true);
INSERT INTO Bill (place_payment,lineNumber)
VALUES('Davivienda','3107356146');

INSERT INTO Customer(id, name, type, address, city,email)  
VALUES('1006106575', 'Pablo Esteban', 'Natural','Noc','Piendamó','angelicamunoz1502@gmail.com');
INSERT INTO Lines (number,customerID,planID, userID)
VALUES('3217219953', '1006106575',5, 456);
INSERT INTO Bill (lineNumber)
VALUES('3217219953');


-- Ejemplo con extensión de plan

UPDATE Bill SET plan_ext=1 WHERE id=1;
UPDATE Bill SET data_consuption=500, minutes_consuption = 150, sms_consuption=50, data_wpp=150, minutes_wpp=300, data_fb=150 WHERE id=1;

