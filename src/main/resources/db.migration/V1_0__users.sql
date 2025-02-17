create table if not exists users
(
    id           bigint primary key,
    first_name   varchar(20)  not null,
    last_name    varchar(20)  not null,
    phone_number varchar(12)  not null,
    email        varchar(30)  not null,
    password     varchar(255) not null,
    role         varchar(20)  not null default 'GUEST',
    external_id uuid         not null,
    created_at  timestamp    not null,
    updated_at  timestamp    not null,
    created_by  varchar(50)  not null default 'SYSTEM',
    updated_by  varchar(50)  not null default 'SYSTEM'
)