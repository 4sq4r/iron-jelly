create table if not exists companies
(
    id          bigint primary key,
    name        varchar(30) not null,
    external_id uuid        not null,
    created_at  timestamp   not null,
    updated_at  timestamp   not null
)