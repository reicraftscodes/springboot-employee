DELETE FROM EMPLOYEE;
DELETE FROM COMPANY;

INSERT INTO COMPANY (id, company_name) VALUES (1, 'OOCL');

INSERT INTO EMPLOYEE (id, name, age, gender, salary, company_id) VALUES (1, 'Cedric', 19, 'male', 1000000, 1);
INSERT INTO EMPLOYEE (id, name, age, gender, salary, company_id) VALUES (2, 'Jc', 21, 'male', 1000000, 1);
INSERT INTO EMPLOYEE (id, name, age, gender, salary, company_id) VALUES (3, 'Janelle', 20, 'female', 1000000, 1);
INSERT INTO EMPLOYEE (id, name, age, gender, salary, company_id) VALUES (4, 'Charlie', 21, 'male', 1000000, 1);
INSERT INTO EMPLOYEE (id, name, age, gender, salary, company_id) VALUES (5, 'Joseph', 18, 'male', 1000000, 1);