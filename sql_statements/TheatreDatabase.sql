DROP DATABASE IF EXISTS `Theatre`;
CREATE DATABASE `Theatre`;
USE Theatre;
CREATE TABLE GenericUser(
    Email   VARCHAR(320) NOT NULL,
    Password_ VARCHAR(32) NOT NULL,
    UserType VARCHAR(10) NOT NULL,
    PRIMARY KEY(Email)
);

CREATE TABLE RegisteredUser(
    Email   VARCHAR(320) NOT NULL,
    Fname  VARCHAR(30) NOT NULL,
    Lname  VARCHAR(30) NOT NULL,
    StAddress VARCHAR(400) NOT NULL,
    CreditCard CHAR(16) NOT NULL,
    ExpDate DATE NOT NULL,
    PRIMARY KEY(Email),
    FOREIGN KEY(Email) REFERENCES GenericUser(Email)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE GuestUser(
    Email   VARCHAR(320) NOT NULL,
    PRIMARY KEY(Email),
    FOREIGN KEY(Email) REFERENCES GenericUser(Email)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Credit(
    ID INT AUTO_INCREMENT NOT NULL,
    Email VARCHAR(320) NOT NULL,
    IssueDate DATE NOT NULL,
    Amount INT NOT NULL,
    PRIMARY KEY(ID, Email),
    FOREIGN KEY(Email) REFERENCES GenericUser(Email)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABlE MovieTheatre(
    LocName   VARCHAR(32) NOT NULL,
    StAddress VARCHAR(400) NOT NULL,
    PRIMARY KEY(LocName)
);

CREATE TABLE TheatreRoom(
    RNumber INT NOT NULL,
    Capacity INT NOT NULL,
    TheatreName VARCHAR(32) NOT NULL,
    PRIMARY KEY(RNumber, TheatreName),
    FOREIGN KEY(TheatreName) REFERENCES MovieTheatre(LocName)
    ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABlE Movie(
    Title VARCHAR(32) NOT NULL,
    Duration INT NOT NULL,
    OpeningDate DATE NOT NULL,
    PRIMARY KEY(Title)
);

CREATE TABLE Showtime(
    ShowDateTime TIMESTAMP NOT NULL,
    MTitle VARCHAR(32) NOT NULL,
    RNumber INT NOT NULL,
    PremiumSeats INT NOT NULL,
    Seats INT NOT NULL,
    TName VARCHAR(32) NOT NULL,
    PRIMARY KEY(ShowDateTime, MTitle, RNumber, TName),
    FOREIGN KEY(MTitle) REFERENCES Movie(Title)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(RNumber, TName) REFERENCES TheatreRoom(RNumber, TheatreName)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Ticket (
    TNo INT NOT NULL,
    MTitle VARCHAR(32) NOT NULL,
    ShowDateTime TIMESTAMP NOT NULL,
    RNumber INT NOT NULL,
    TName VARCHAR(32) NOT NULL,
    Price INT NOT NULL,
    Email VARCHAR(320),
    PRIMARY KEY(TNo, MTitle, ShowDateTime, RNumber, TName),
    FOREIGN KEY(MTitle, ShowDateTime, RNumber, TName) REFERENCES Showtime(MTitle, ShowDateTime, RNumber, TName)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(Email) references GenericUser(Email) ON DELETE Set NULL ON UPDATE CASCADE
);









INSERT INTO MovieTheatre(LocName, StAddress) VALUES ('UCalgary Theatre', ' 2500 University Dr NW, Calgary, AB');

INSERT INTO TheatreRoom(RNumber, Capacity, TheatreName) VALUES 
(1, 30, 'UCalgary Theatre'),
(2, 30, 'UCalgary Theatre'),
(3, 30, 'UCalgary Theatre'),
(4, 30, 'UCalgary Theatre');

INSERT INTO Movie(Title, Duration, OpeningDate) VALUES
('Movie A', 90, '2022-12-03'),
('Movie B', 100, '2022-12-03'),
('Movie C', 95, '2022-12-03'),
('Movie D', 90, '2022-12-03');

INSERT INTO Showtime(ShowDateTime, MTitle, RNumber, PremiumSeats, Seats, TName) VALUES 
('2022-12-04 9:00:00', 'Movie A', 1, 3, 27, 'UCalgary Theatre'),
('2022-12-04 11:00:00', 'Movie A', 2, 3, 27, 'UCalgary Theatre'),
('2022-12-04 13:00:00', 'Movie A', 3, 3, 27, 'UCalgary Theatre'),
('2022-12-04 15:00:00', 'Movie A', 4, 3, 27, 'UCalgary Theatre'),

('2022-12-04 9:00:00', 'Movie B', 2, 3, 27, 'UCalgary Theatre'),
('2022-12-04 11:00:00', 'Movie B', 3, 3, 27, 'UCalgary Theatre'),
('2022-12-04 13:00:00', 'Movie B', 4, 3, 27, 'UCalgary Theatre'),
('2022-12-04 15:00:00', 'Movie B', 1, 3, 27, 'UCalgary Theatre'),

('2022-12-04 9:00:00', 'Movie C', 3, 3, 27, 'UCalgary Theatre'),
('2022-12-04 11:00:00', 'Movie C', 4, 3, 27, 'UCalgary Theatre'),
('2022-12-04 13:00:00', 'Movie C', 1, 3, 27, 'UCalgary Theatre'),
('2022-12-04 15:00:00', 'Movie C', 2, 3, 27, 'UCalgary Theatre'),

('2022-12-04 9:00:00', 'Movie D', 4, 3, 27, 'UCalgary Theatre'),
('2022-12-04 11:00:00', 'Movie D', 1, 3, 27, 'UCalgary Theatre'),
('2022-12-04 13:00:00', 'Movie D', 2, 3, 27, 'UCalgary Theatre'),
('2022-12-04 15:00:00', 'Movie D', 3, 3, 27, 'UCalgary Theatre');


INSERT INTO Ticket(TNo, MTitle, ShowDateTime, RNumber, TName, Price, Email) VALUES
(1, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(2, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(3, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(4, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(5, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(6, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(7, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(8, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(9, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(10, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(11, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(12, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(13, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(14, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(15, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(16, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(17, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(18, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(19, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(20, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(21, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(22, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(23, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(24, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(25, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(26, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(27, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(28, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(29, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),
(30, 'Movie A', '2022-12-04 9:00:00', 1, 'UCalgary Theatre', 15, null),

(1, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(2, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(3, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(4, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(5, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(6, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(7, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(8, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(9, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(10, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(11, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(12, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(13, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(14, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(15, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(16, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(17, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(18, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(19, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(20, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(21, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(22, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(23, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(24, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(25, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(26, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(27, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(28, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(29, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),
(30, 'Movie A', '2022-12-04 11:00:00', 2, 'UCalgary Theatre', 15, null),

(1, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(2, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(3, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(4, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(5, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(6, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(7, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(8, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(9, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(10, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(11, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(12, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(13, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(14, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(15, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(16, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(17, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(18, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(19, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(20, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(21, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(22, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(23, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(24, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(25, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(26, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(27, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(28, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(29, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),
(30, 'Movie A', '2022-12-04 13:00:00', 3, 'UCalgary Theatre', 15, null),

(1, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(2, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(3, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(4, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(5, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(6, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(7, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(8, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(9, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(10, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(11, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(12, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(13, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(14, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(15, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(16, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(17, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(18, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(19, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(20, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(21, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(22, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(23, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(24, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(25, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(26, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(27, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(28, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(29, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),
(30, 'Movie A', '2022-12-04 15:00:00', 4, 'UCalgary Theatre', 15, null),


(1, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(2, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(3, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(4, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(5, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(6, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(7, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(8, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(9, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(10, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(11, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(12, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(13, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(14, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(15, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(16, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(17, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(18, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(19, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(20, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(21, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(22, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(23, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(24, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(25, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(26, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(27, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(28, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(29, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),
(30, 'Movie B', '2022-12-04 9:00:00', 2, 'UCalgary Theatre', 15, null),

(1, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(2, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(3, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(4, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(5, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(6, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(7, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(8, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(9, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(10, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(11, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(12, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(13, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(14, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(15, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(16, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(17, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(18, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(19, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(20, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(21, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(22, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(23, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(24, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(25, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(26, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(27, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(28, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(29, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),
(30, 'Movie B', '2022-12-04 11:00:00', 3, 'UCalgary Theatre', 15, null),

(1, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(2, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(3, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(4, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(5, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(6, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(7, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(8, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(9, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(10, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(11, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(12, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(13, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(14, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(15, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(16, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(17, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(18, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(19, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(20, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(21, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(22, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(23, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(24, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(25, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(26, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(27, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(28, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(29, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),
(30, 'Movie B', '2022-12-04 13:00:00', 4, 'UCalgary Theatre', 15, null),

(1, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(2, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(3, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(4, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(5, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(6, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(7, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(8, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(9, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(10, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(11, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(12, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(13, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(14, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(15, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(16, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(17, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(18, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(19, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(20, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(21, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(22, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(23, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(24, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(25, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(26, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(27, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(28, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(29, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),
(30, 'Movie B', '2022-12-04 15:00:00', 1, 'UCalgary Theatre', 15, null),


(1, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(2, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(3, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(4, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(5, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(6, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(7, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(8, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(9, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(10, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(11, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(12, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(13, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(14, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(15, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(16, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(17, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(18, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(19, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(20, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(21, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(22, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(23, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(24, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(25, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(26, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(27, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(28, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(29, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),
(30, 'Movie C', '2022-12-04 9:00:00', 3, 'UCalgary Theatre', 15, null),

(1, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(2, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(3, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(4, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(5, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(6, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(7, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(8, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(9, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(10, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(11, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(12, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(13, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(14, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(15, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(16, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(17, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(18, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(19, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(20, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(21, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(22, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(23, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(24, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(25, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(26, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(27, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(28, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(29, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),
(30, 'Movie C', '2022-12-04 11:00:00', 4, 'UCalgary Theatre', 15, null),

(1, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(2, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(3, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(4, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(5, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(6, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(7, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(8, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(9, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(10, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(11, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(12, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(13, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(14, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(15, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(16, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(17, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(18, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(19, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(20, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(21, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(22, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(23, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(24, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(25, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(26, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(27, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(28, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(29, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),
(30, 'Movie C', '2022-12-04 13:00:00', 1, 'UCalgary Theatre', 15, null),

(1, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(2, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(3, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(4, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(5, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(6, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(7, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(8, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(9, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(10, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(11, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(12, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(13, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(14, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(15, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(16, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(17, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(18, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(19, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(20, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(21, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(22, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(23, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(24, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(25, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(26, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(27, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(28, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(29, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),
(30, 'Movie C', '2022-12-04 15:00:00', 2, 'UCalgary Theatre', 15, null),


(1, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(2, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(3, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(4, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(5, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(6, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(7, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(8, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(9, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(10, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(11, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(12, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(13, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(14, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(15, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(16, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(17, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(18, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(19, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(20, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(21, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(22, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(23, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(24, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(25, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(26, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(27, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(28, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(29, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),
(30, 'Movie D', '2022-12-04 9:00:00', 4, 'UCalgary Theatre', 15, null),

(1, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(2, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(3, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(4, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(5, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(6, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(7, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(8, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(9, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(10, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(11, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(12, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(13, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(14, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(15, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(16, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(17, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(18, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(19, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(20, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(21, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(22, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(23, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(24, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(25, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(26, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(27, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(28, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(29, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),
(30, 'Movie D', '2022-12-04 11:00:00', 1, 'UCalgary Theatre', 15, null),

(1, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(2, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(3, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(4, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(5, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(6, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(7, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(8, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(9, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(10, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(11, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(12, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(13, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(14, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(15, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(16, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(17, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(18, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(19, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(20, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(21, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(22, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(23, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(24, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(25, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(26, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(27, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(28, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(29, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),
(30, 'Movie D', '2022-12-04 13:00:00', 2, 'UCalgary Theatre', 15, null),

(1, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(2, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(3, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(4, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(5, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(6, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(7, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(8, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(9, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(10, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(11, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(12, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(13, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(14, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(15, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(16, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(17, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(18, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(19, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(20, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(21, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(22, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(23, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(24, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(25, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(26, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(27, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(28, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(29, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null),
(30, 'Movie D', '2022-12-04 15:00:00', 3, 'UCalgary Theatre', 15, null);