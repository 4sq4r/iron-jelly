create table if not exists companies_2_users
(
    id         bigint primary key,
    company_id bigint not null references companies (id),
    user_id    bigint not null references users (id)
)