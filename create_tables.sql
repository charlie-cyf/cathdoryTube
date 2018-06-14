drop table Item CASCADE constraints;
drop table Branch CASCADE constraints;
drop table Clerk CASCADE constraints;
drop table Storage;
drop table Deal CASCADE constraints;
drop table ItemsInPurchase;
drop table Offer;
drop table Purchase CASCADE constraints;
drop table ItemsInDeal;
drop table Membership CASCADE constraints;
drop table MemberPurchase;

CREATE TABLE Item
(
	itemID INTEGER PRIMARY KEY,
	name CHAR(20),
	price NUMBER(6, 2),
	type CHAR(20)
);

grant select on Item to public;

CREATE TABLE Branch (
	branchNumber INTEGER PRIMARY KEY,
	location CHAR(100)
);

grant select on Branch to public;

CREATE TABLE Clerk (
	clerkID INTEGER PRIMARY KEY,
	name CHAR(20),
	wage INTEGER,
	branchNumber INTEGER NOT NULL,
	type CHAR(20),
	FOREIGN KEY (branchNumber) REFERENCES Branch
		ON DELETE CASCADE
);

grant select on Clerk to public;

CREATE TABLE Storage (
	itemID INTEGER,
	branchNumber INTEGER NOT NULL,
	amount INTEGER,
	primary key (itemID, branchNumber),
	FOREIGN KEY (itemID) REFERENCES Item
		ON DELETE CASCADE	,
	FOREIGN KEY (branchNumber) REFERENCES Branch
		ON DELETE CASCADE
);

grant select on Storage to public;

CREATE TABLE Deal (
	dealName CHAR(20) PRIMARY KEY,
	duration CHAR(20)
);

grant select on Deal to public;

CREATE TABLE ItemsInDeal (
	itemID INTEGER NOT NULL,
	dealName CHAR(20),
	percentage NUMBER(1, 2),
	primary key (itemID, dealName),
	FOREIGN KEY (itemID) REFERENCES Item
		ON DELETE CASCADE,
	FOREIGN KEY (dealName) REFERENCES Deal
		ON DELETE CASCADE
);

grant select on ItemsInDeal to public;

CREATE TABLE Offer (
	dealName CHAR(20),
	branchNumber INTEGER NOT NULL,
	primary key (dealName, branchNumber),
	FOREIGN KEY (dealName) REFERENCES Deal
		ON DELETE CASCADE,
	FOREIGN KEY (branchNumber) REFERENCES Branch
		ON DELETE CASCADE
);

grant select on Offer to public;

CREATE TABLE Purchase (
	receiptNumber INTEGER PRIMARY KEY,
	purchaseTime CHAR(20),
	purchaseDate CHAR(20),
	totalPrice NUMBER(5, 2) NOT NULL,
	clerkID INTEGER NOT NULL,
	branchNumber INTEGER NOT NULL,
	FOREIGN KEY (clerkID) REFERENCES Clerk,
	FOREIGN KEY (branchNumber) REFERENCES Branch
);

grant select on Purchase to public;

CREATE TABLE ItemsInPurchase (
	receiptNumber INTEGER,
	itemID INTEGER NOT NULL,
	amount INTEGER NOT NULL,
	primary key(receiptNumber, itemID),
	FOREIGN KEY (receiptNumber) REFERENCES Purchase,
	FOREIGN KEY (itemID) REFERENCES Item
);

grant select on ItemsInPurchase to public;

CREATE TABLE Membership (
	memberID INTEGER PRIMARY KEY,
	name CHAR(20),
	phone CHAR(20),
	points INTEGER,
	UNIQUE (name, phone)
);

grant select on Membership to public;

CREATE TABLE MemberPurchase (
	receiptNumber INTEGER PRIMARY KEY,
	memberID INTEGER NOT NULL,
	FOREIGN KEY (receiptNumber) REFERENCES Purchase
		ON DELETE CASCADE,
	FOREIGN KEY (memberID) REFERENCES Membership
		ON DELETE CASCADE
);

grant select on MemberPurchase to public;

commit;
