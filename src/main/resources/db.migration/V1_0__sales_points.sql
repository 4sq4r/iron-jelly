create table if not exists sales_points
(
    id          bigint primary key,
    company_id  bigint  not null references companies (id)
    name   varchar(20)  not null,
    external_id uuid         not null,
    created_at  timestamp    not null,
    updated_at  timestamp    not null,
    created_by  varchar(50)  not null default 'SYSTEM',
    updated_by  varchar(50)  not null default 'SYSTEM'
)