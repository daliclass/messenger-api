CREATE TABLE Message (
    id int not null PRIMARY KEY,
    discussionId int not null,
    message varchar(300) not null,
    side int not null
);