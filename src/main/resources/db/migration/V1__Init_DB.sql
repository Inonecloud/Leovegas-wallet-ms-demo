drop table if exists leovegas_transaction CASCADE;
drop table if exists wallet CASCADE;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table leovegas_transaction
(
    id               varchar2 not null unique ,
    amount           decimal(19, 2),
    transaction_type integer,
    transaction_time timestamp,
    wallet_id        bigint not null,
    primary key (id)
);

create table wallet
(
    id      bigint not null auto_increment,
    balance decimal(19, 2),
    player_id bigint,
    primary key (id)
);

alter table leovegas_transaction
    add constraint leovegas_transaction_wallet_id_fk foreign key (wallet_id) references wallet;