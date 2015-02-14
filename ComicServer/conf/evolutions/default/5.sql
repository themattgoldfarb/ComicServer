# --- !Ups

create table user_in_role (
  email varchar(255) not null,
  role_name VARCHAR(255) not null,
  CONSTRAINT pk_emailRoleName PRIMARY KEY (email, role_name),
  foreign key fk_emailToUser (email)
    references user(email),
  foreign key fk_roleNameToRole(role_name)
    references role(role_name)
);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user_in_role;

SET REFERENTIAL_INTEGRITY TRUE;