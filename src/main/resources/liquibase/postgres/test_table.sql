--liquibase formatted sql
--changeset jaguar1337:VNDMARKET-7931-create-vendors-file-meta-table
create table hello_world
(
    id           uuid                     not null,
    name         varchar(255)             not null,
    constraint pk_filmet_id primary key (id)
);