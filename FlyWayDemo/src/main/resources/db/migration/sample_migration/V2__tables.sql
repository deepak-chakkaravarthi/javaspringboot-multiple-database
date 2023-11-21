drop table if exists tablea;
create table tablea (
  id bigint not null,
  itemcode varchar(28) not null,
  itemname varchar(28),
  description varchar(55),
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tableb;
create table tableb (
  id bigint not null,
  customercode varchar(28) not null,
  customername varchar(28),
  customerage int,
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
