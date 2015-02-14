# --- !Ups

insert into role(role_name) values
  ('userAdministrator'),
  ('fileAdministrator'),
  ('reader')


# --- !Downs

delete from role where role_name in ('userAdministrator', 'fileAdministrator', 'reader');

