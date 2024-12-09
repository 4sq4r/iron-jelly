create table if not exists users
(
    id          bigint primary key,
    username    varchar(50)  not null unique,
    password    varchar(255) not null,
    first_name  varchar(20)  not null,
    last_name   varchar(20)  not null,
    external_id uuid         not null,
    created_at  timestamp    not null,
    updated_at  timestamp    not null
)