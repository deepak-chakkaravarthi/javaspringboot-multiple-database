drop table if exists things;
create table things (
  id bigint not null,
  itemcode varchar(28) not null,
  itemname varchar(28),
  description varchar(55),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists clients;
create table clients (
  id bigint not null,
  customercode varchar(28) not null,
  customername varchar(28),
  customerage int,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
