CREATE DATABASE atm_system;

USE atm_system;

CREATE TABLE accounts (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100),
    balance DOUBLE,
    pin_hash VARCHAR(256)
);

SHOW databases;
USE atm_system;
SELECT * FROM accounts;
