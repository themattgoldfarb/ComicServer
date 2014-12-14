# --- !Ups

create table user (
  email varchar(255),
  name VARCHAR(255),
  password VARCHAR(255),
  CONSTRAINT pk_user PRIMARY KEY (email)
);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;