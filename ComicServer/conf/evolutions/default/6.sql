# --- !Ups

insert into role(roleName) values
  ('userAdministrator'),
  ('fileAdministrator'),
  ('reader')


# --- !Downs

delete from role where roleName in ('userAdministrator', 'fileAdministrator', 'reader');

