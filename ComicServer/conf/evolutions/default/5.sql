# --- !Ups

create table userInRole (
  email varchar(255) not null,
  roleName VARCHAR(255) not null,
  CONSTRAINT pk_emailRoleName PRIMARY KEY (email, roleName),
  foreign key fk_emailToUser (email)
    references user(email),
  foreign key fk_roleNameToRole(roleName)
    references role(roleName)
);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists userInRole;

SET REFERENTIAL_INTEGRITY TRUE;