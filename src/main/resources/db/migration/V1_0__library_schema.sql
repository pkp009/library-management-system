CREATE TABLE LEDGER (
   userId LONG NOT NULL,
   bookId LONG NOT NULL,
   checkOutDate DATE NOT NULL,
   expectedReturnDate DATE NOT NULL,
   returnDate DATE,
   dues INT
);


CREATE TABLE IF NOT EXISTS BOOK (
   bookId LONG NOT NULL PRIMARY KEY,
   title VARCHAR(50) NOT NULL,
   author VARCHAR(50) NOT NULL,
   noOfCopies INT);

INSERT INTO BOOK (bookId, title, author, noOfCopies)
VALUES (12345,'The India Story', 'Bimal Jalal', 5);
INSERT INTO BOOK (bookId, title, author, noOfCopies)
VALUES (54345,'Listen to Your Heart: The London Adventure', 'Ruskin Bond', 5);
INSERT INTO BOOK (bookId, title, author, noOfCopies)
VALUES (41345,'A Place Called Home','Preeti Shenoy', 5);
INSERT INTO BOOK (bookId, title, author, noOfCopies)
VALUES (16325,'Business of Sports: The Formula for Success', 'Vinit Karnik', 5);
INSERT INTO BOOK (bookId, title, author, noOfCopies)
VALUES (12745,'Modi AT 20: Dreams Meeting Delivery', 'VP Venkaiah Naidu', 5);



CREATE TABLE IF NOT EXISTS MEMBERS (
   userId LONG NOT NULL PRIMARY KEY,
   userName VARCHAR(50) NOT NULL,
   noOfBooksIssued INT,
   dues INT);

INSERT INTO MEMBERS (userId, userName, noOfBooksIssued, dues)
VALUES (000001,'Prateek Pandey', 0, 0);
INSERT INTO MEMBERS (userId, userName, noOfBooksIssued, dues)
VALUES (000002,'Rasheed Kidwai', 0, 0);
INSERT INTO MEMBERS (userId, userName, noOfBooksIssued, dues)
VALUES (000003,'Shashi Tharoor', 0, 0);
