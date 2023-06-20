--liquibase formatted sql
--changeset alex_klepov:add_schema

create table free_numbers.free_number
(
    id                  serial              not null,
    number              varchar(255)             not null,
    country_code         integer             not null,
    updated_at           timestamp           not null,
    data_humans          varchar(255)        not null,
    full_number          varchar(255)        not null,
    country_text         varchar(255)        not null,
    max_date             timestamp           not null,
    status              varchar(255)        not null,
    constraint free_number_pk_id primary key (id)
);


--changeset alex_klepov:add_index
create index idx_free_number_full_number
on free_numbers.free_number(full_number)