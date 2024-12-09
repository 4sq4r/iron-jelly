create table if not exists cards
(
    id               bigint primary key,
    user_id          bigint    not null references users (id),
    isActive         boolean   not null default true,
    card_template_id bigint    not null references card_templates (id),
    usage_limit      integer   not null default 1,
    external_id      uuid      not null,
    created_at       timestamp not null,
    updated_at       timestamp not null
)