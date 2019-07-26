
-- Create Tables OLD TABLES NOT USED
CREATE TABLE employees (
    employee_id INT,
    first_name VARCHAR2(30) NOT NULL,
    last_name VARCHAR2(30) NOT NULL,
    address VARCHAR2(255) NOT NULL,
    phone VARCHAR2(30),
    email VARCHAR2(30) NOT NULL,
    password VARCHAR2(30) NOT NULL,
    PRIMARY KEY(employee_id)
);

CREATE TABLE managers (
    manager_id INT,
    first_name VARCHAR2(30) NOT NULL,
    last_name VARCHAR2(30) NOT NULL,
    address VARCHAR2(255) NOT NULL,
    phone VARCHAR2(30),
    email VARCHAR2(30) NOT NULL,
    password VARCHAR2(30) NOT NULL,
    PRIMARY KEY (manager_id)
);

CREATE TABLE reimburstment_request (
    reimburstment_id INT,
    amount INT NOT NULL,
    dates VARCHAR2(15) NOT NULL,
    status VARCHAR2(15) NOT NULL,
    employee_id INT,
    manager_id INT,
    PRIMARY KEY (reimburstment_id),
    FOREIGN KEY (employee_id) REFERENCES employees (employee_id),
    FOREIGN KEY (manager_id) REFERENCES managers (manager_id)
);

-- ===== Drop  tables =====
DROP TABLE reimburstment_request;
DROP TABLE sys_users;
DROP TABLE request_status;

-- ==== Drop Triggers & Sequence
DROP SEQUENCE user_id_seq;
DROP TRIGGER sys_users_id_trigger;

-- ======= New Tables =======
CREATE TABLE reimburstment_request (
    reimburstment_id NUMBER,
    amount INT NOT NULL,
    dates VARCHAR2(15) NOT NULL,
    status NUMBER NOT NULL,
    user_id NUMBER,
    request_description VARCHAR2(256),
    CONSTRAiNT pk_reimburstment PRIMARY KEY (reimburstment_id),
    CONSTRAINT fk_reimburstment_status FOREIGN KEY (status) REFERENCES request_status (status_id),
    CONSTRAINT fk_reimburstment_user FOREIGN KEY (user_id) REFERENCES sys_users (user_id)
);

CREATE TABLE request_status (
        status_id NUMBER,
        status VARCHAR2(30),
        CONSTRAINT pk_status PRIMARY KEY (status_id)
);

CREATE TABLE sys_users (
    user_id NUMBER,
    is_manager NUMBER NOT NULL,
    first_name VARCHAR2(30) NOT NULL,
    last_name VARCHAR2(30) NOT NULL,
    address VARCHAR2(255) NOT NULL,
    phone VARCHAR2(30),
    email VARCHAR2(30) NOT NULL UNIQUE,
    user_password VARCHAR2(30) NOT NULL,
   CONSTRAINT pk_user PRIMARY KEY(user_id)
);

-- ===== Populate request_status table =====
INSERT INTO request_status(status_id, status)
VALUES (1, 'Pending');

INSERT INTO request_status(status_id, status)
VALUES (2, 'Approved');

INSERT INTO request_status(status_id, status)
VALUES (3, 'Rejected');

-- ===== Create trigger for primary key incrementer =====
CREATE OR REPLACE TRIGGER sys_users_id_trigger
BEFORE INSERT 
ON sys_users
FOR EACH ROW
BEGIN
    SELECT user_id_seq.nextval INTO :new.user_id
FROM dual;
END;

CREATE OR REPLACE TRIGGER reimburstment_id_trigger
BEFORE INSERT 
ON reimburstment_request
FOR EACH ROW
BEGIN
    SELECT reimburstment_id_seq.nextval INTO :new.reimburstment_id
FROM dual;
END;

-- ===== Create sequence =====
CREATE SEQUENCE user_id_seq
START WITH 1
INCREMENT BY 1 
MINVALUE 1
MAXVALUE 999999999999;
COMMIT;

CREATE SEQUENCE reimburstment_id_seq
START WITH 1
INCREMENT BY 1 
MINVALUE 1
MAXVALUE 999999999999;
COMMIT;

