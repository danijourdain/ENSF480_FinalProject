DROP DATABASE IF EXISTS `Theater`;
CREATE DATABASE `Theater`;
USE Theater;
CREATE TABLE GenericUser(
    Email   VARCHAR(320) NOT NULL,
    Fname  VARCHAR(30) NOT NULL,
    Lname  VARCHAR(30) NOT NULL,
    `Password_` VARCHAR(32) NOT NULL,
    UserType VARCHAR(10) NOT NULL,
    PRIMARY KEY(Email)
);
CREATE TABLE RegisteredUser(
    Email   VARCHAR(320) NOT NULL,
    StAddress VARCHAR(400) NOT NULL,
    CreditCard CHAR(16) NOT NULL,
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
CREATE TABLE Admin_User(
    Email   VARCHAR(320) NOT NULL,
    PRIMARY KEY(Email),
    FOREIGN KEY(Email) REFERENCES GenericUser(Email)
    ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Credit(
    ID INT AUTO_INCREMENT NOT NULL,
    Email VARCHAR(320) NOT NULL,
    ExpiryDate DATE NOT NULL,
    Amount INT NOT NULL,
    PRIMARY KEY(ID, Email),
    FOREIGN KEY(Email) REFERENCES GenericUser(Email)
    ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABlE MovieTheater(
    LocName   VARCHAR(32) NOT NULL,
    StAddress VARCHAR(400) NOT NULL,
    PRIMARY KEY(LocName)
);
CREATE TABLE TheaterRoom(
    RNumber INT NOT NULL,
    Capacity INT NOT NULL,
    TheaterName VARCHAR(32) NOT NULL,
    PRIMARY KEY(RNumber, TheaterName),
    FOREIGN KEY(TheaterName) REFERENCES MovieTheater(LocName)
    ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABlE Movie(
    Title VARCHAR(32) NOT NULL,
    Duration INT NOT NULL,
    Rating VARCHAR(5) NOT NULL,
    OpeningDate DATE NOT NULL,
    ClosingDate DATE NOT NULL,
    PRIMARY KEY(Title)
);
CREATE TABLE Showtime(
    ShowDateTime DATETIME NOT NULL,
    MTitle VARCHAR(32) NOT NULL,
    RNumber INT NOT NULL,
    PremiumSeats INT NOT NULL,
    Seats INT NOT NULL,
    TName VARCHAR(32) NOT NULL,
    PRIMARY KEY(ShowDateTime, MTitle, RNumber, TName),
    FOREIGN KEY(MTitle) REFERENCES Movie(Title)
    ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY(RNumber, TName) REFERENCES TheaterRoom(RNumber, TheaterName)
    ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Ticket (
    TNo INT NOT NULL,
    MTitle VARCHAR(32) NOT NULL,
    ShowDateTime DATETIME NOT NULL,
    RNumber INT NOT NULL,
    TName VARCHAR(32) NOT NULL,
    Price INT NOT NULL,
    Email VARCHAR(320) NOT NULL,
    PRIMARY KEY(TNo, MTitle, DateTime_, RNumber, TName),
    FOREIGN KEY(MTitle, ShowDateTime, RNumber, TName) REFERENCES Showtime(MTitle, ShowDateTime, RNumber, TName)
    ON UPDATE CASCADE ON DELETE CASCADE
);
