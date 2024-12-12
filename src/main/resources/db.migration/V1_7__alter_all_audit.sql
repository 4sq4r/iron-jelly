alter table if exists users
    add column created_by varchar(50) default 'SYSTEM',
    add column updated_by varchar(50) default 'SYSTEM';

alter table if exists companies
    add column created_by varchar(50) default 'SYSTEM',
    add column updated_by varchar(50) default 'SYSTEM';

alter table if exists card_templates
    add column created_by varchar(50) default 'SYSTEM',
    add column updated_by varchar(50) default 'SYSTEM';

alter table if exists cards
    add column created_by varchar(50) default 'SYSTEM',
    add column updated_by varchar(50) default 'SYSTEM';

alter table if exists orders
    add column created_by varchar(50) default 'SYSTEM',
    add column updated_by varchar(50) default 'SYSTEM';

