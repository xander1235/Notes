create table user_credentials
(
    id             int auto_increment
        primary key,
    user_name      varchar(255)                        not null,
    password       varchar(255)                        not null,
    transaction_id varchar(255)                        null,
    created_at     timestamp default CURRENT_TIMESTAMP not null,
    modified_at    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint user_name_unique unique (user_name)
);

create index user_name_index
    on user_credentials (user_name);

create index transaction_id_index
    on user_credentials (transaction_id);

create table notes
(
    id          int auto_increment
        primary key,
    user_id     int                                 not null,
    title       varchar(255)                        not null,
    description text                                null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    modified_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint title_unique unique (title)
);

create index title_index
    on notes (title);

create index user_id_index
    on notes (user_id);