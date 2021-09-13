create database hotel;

create table booking (
    booking_id integer not null,
    aadhar_number varchar(255),
    booked_on datetime,
    from_date datetime,
    num_of_rooms integer not null,
    room_numbers varchar(255),
    room_price integer not null,
    to_date datetime,
    transaction_id integer default 0,
    primary key (booking_id)
);

create table hibernate_sequence (
    next_val bigint
);
