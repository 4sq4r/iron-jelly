alter table if exists users
    add column role varchar(20) not null default 'GUEST';

