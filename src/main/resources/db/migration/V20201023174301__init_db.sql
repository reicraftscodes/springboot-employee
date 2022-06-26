CREATE TABLE COMPANY (
    id INT NOT NULL PRIMARY KEY auto_increment,
    company_name VARCHAR(255)
);

CREATE TABLE EMPLOYEE (
    id INT NOT NULL PRIMARY KEY auto_increment,
    name VARCHAR(30) NOT NULL,
    age INT,
    gender VARCHAR(10),
    salary INT,
    company_id INT,
    FOREIGN KEY (company_id) REFERENCES company (id)
);