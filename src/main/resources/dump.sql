create table if not exists users
(
    id serial
    constraint users_pkey
    primary key,
    first_name varchar(50),
    last_name varchar(50),
    login varchar(50),
    password_hash varchar(255)
    );

alter table users owner to postgres;

create table if not exists auth
(
    id serial
    constraint auth_pkey
    primary key,
    user_id bigint
    constraint auth_user_id_fkey
    references users,
    cookie_value varchar(255)
    );

alter table auth owner to postgres;

create table if not exists products
(
    id serial
    constraint products_pk
    primary key,
    title varchar(50),
    cost double precision,
    description text
    );

alter table products owner to postgres;


create table if not exists favourite_products
(
    user_id integer not null
    constraint favourite_products_users_id_fk
    references users,
    product_id integer not null
    constraint favourite_products_products_id_fk
    references products
);

alter table favourite_products owner to postgres;

create table if not exists bucket
(
    user_id integer not null
    constraint bucket_users_id_fk
    references users,
    product_id integer not null
    constraint bucket_id_fk
    references products
);

alter table bucket owner to postgres;
