# --- First database schema

# --- !Ups

create table comic_book (
    id          integer primary key AUTOINCREMENT,
    file_name    varchar(255),
    path        varchar(1000),
    cover_index     int default 1,
    num_pages   int null,
    series_name  varchar(255),
    issue_number int null
);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comic_Book;

SET REFERENTIAL_INTEGRITY TRUE;


