create table if not exists card_templates
(
    id          bigint primary key,
    company_id  bigint      not null references companies (id),
    limit_value integer,
    expire_days integer,
    description varchar(500),
    is_active   boolean     not null default true,
    external_id uuid        not null,
    created_at  timestamp   not null,
    updated_at  timestamp   not null,
    created_by  varchar(50) not null default 'SYSTEM',
    updated_by  varchar(50) not null default 'SYSTEM'
)