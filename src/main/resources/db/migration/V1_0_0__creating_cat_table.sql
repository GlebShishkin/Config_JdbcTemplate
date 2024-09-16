CREATE TABLE account
(
    id SERIAL PRIMARY KEY,
    acc_num VARCHAR(25) NOT NULL,
    name VARCHAR(100) NOT NULL
);

insert into account(acc_num, name) values('444555', 'name1');