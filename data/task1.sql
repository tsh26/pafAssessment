-- Write your Task 1 answers in this file
drop database if exists bedandbreakfast;

create database bedandbreakfast;

use bedandbreakfast;

create table users (

	email varchar(128) not null,
    name varchar(128) not null,
    
    primary key(email)
);

create table bookings (

	booking_id char(8),
    listing_id varchar(20),
    duration int,
    email varchar(128),
    
    primary key(booking_id)
);

create table reviews (
	
    id int,
    date timestamp
        default current_timestamp 
        on update current_timestamp,
    listing_id varchar(20),
    reviewer_name varchar(64),
    comments text,
    
    primary key(id)

);

insert into users(email, name)
	values
	('fred@gmail.com', 'Fred Flintstone'),
	('barney@gmail.com', 'Barney Rubble'),
	('fry@planetexpress.com', 'Philip J Fry'),
	('hlmer@gmail.com', 'Homer Simpson')
;