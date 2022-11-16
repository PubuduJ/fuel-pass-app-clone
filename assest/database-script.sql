CREATE TABLE User (
    nic VARCHAR(10) PRIMARY KEY ,
    first_name VARCHAR(50) NOT NULL ,
    last_name VARCHAR(50) NOT NULL ,
    address VARCHAR(200) NOT NULL ,
    quota INT NOT NULL
);