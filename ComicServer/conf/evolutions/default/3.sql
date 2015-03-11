# --- !Ups

create table role (
  role_name VARCHAR(255) not null,
  CONSTRAINT pk_roleName PRIMARY KEY (role_name)
);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists role;

SET REFERENTIAL_INTEGRITY TRUE;