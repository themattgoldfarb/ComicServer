# --- !Ups

create table role (
  roleName VARCHAR(255) not null,
  CONSTRAINT pk_roleName PRIMARY KEY (roleName)
);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists role;

SET REFERENTIAL_INTEGRITY TRUE;