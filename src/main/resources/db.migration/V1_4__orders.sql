create table if not exists orders
(
    id          bigint primary key,
    card_id     bigint      not null references cards (id),
    is_free     boolean     not null default false,
    external_id uuid        not null,
    created_at  timestamp   not null,
    updated_at  timestamp   not null,
    created_by  varchar(50) not null default 'SYSTEM',
    updated_by  varchar(50) not null default 'SYSTEM'
)