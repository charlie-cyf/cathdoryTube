insert into Branch (branchNumber, location) values (1, '8777 Thackeray Drive');
insert into Branch (branchNumber, location) values (2, '36564 Mcguire Trail');
insert into Branch (branchNumber, location) values (3, '466 Wayridge Terrace');
insert into Branch (branchNumber, location) values (4, '45 Knutson Pass');
insert into Branch (branchNumber, location) values (5, '13658 Kipling Street');

insert into Clerk (clerkID, name, wage, branchNumber, type) values (1252, 'Osbourn Borsay', 21, 5, 'Manager');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1045, 'Sheryl Luce', 26, 2, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1144, 'Willa Maciejak', 15, 4, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1040, 'Helen Tulip', 27, 1, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1093, 'Petronia Stretton', 28, 2, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1141, 'Ora McCarthy', 30, 3, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1338, 'Araldo Banaszewski', 28, 2, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1429, 'Peter Creaney', 27, 1, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1441, 'Venus Bodesson', 28, 5, 'Employee');
insert into Clerk (clerkID, name, wage, branchNumber, type) values (1456, 'Augy De Lorenzo', 29, 3, 'Employee');

insert into Item (itemId, name, price, type) values (0744, 'Happy Nerd Water', 1.65, 'Drink');
insert into Item (itemId, name, price, type) values (0745, 'Humor Frozen Pizza', 9.99, 'Food');
insert into Item (itemId, name, price, type) values (0746, 'Durex Conodom', 15.99, 'Commodity');
insert into Item (itemId, name, price, type) values (0747, 'Fuel', 1.65,  'Service');
insert into Item (itemId, name, price, type) values (0748, 'LaoGanMa Sauce', 7, 'Food');

insert into Deal (dealName, duration) values ('Spring Sale', '02-01 - 02-05');
insert into Deal (dealName, duration) values ('Summer Sale', '07-01 - 07-03');
insert into Deal (dealName, duration) values ('Christmas Sale', '12-25 - 12-31');
insert into Deal (dealName, duration) values ('Canada Day Deal', '07-01 - 07-01');
insert into Deal (dealName, duration) values ('Winter Sale', '01-01 - 01-05');

insert into Offer (dealName, branchNumber) values ('Summer Sale', 1);
insert into Offer (dealName, branchNumber) values ('Summer Sale', 2);
insert into Offer (dealName, branchNumber) values ('Summer Sale', 3);
insert into Offer (dealName, branchNumber) values ('Spring Sale', 4);
insert into Offer (dealName, branchNumber) values ('Spring Sale', 3);
insert into Offer (dealName, branchNumber) values ('Spring Sale', 2);
insert into Offer (dealName, branchNumber) values ('Christmas Sale', 1);
insert into Offer (dealName, branchNumber) values ('Christmas Sale', 2);
insert into Offer (dealName, branchNumber) values ('Christmas Sale', 3);
insert into Offer (dealName, branchNumber) values ('Christmas Sale', 5);

insert into ItemsInDeal (itemId, dealName, percentage) values (0745, 'Christmas Sale', 0.5);
insert into ItemsInDeal (itemId, dealName, percentage) values (0747, 'Spring Sale', 0.2);
insert into ItemsInDeal (itemId, dealName, percentage) values (0744, 'Spring Sale', 0.25);
insert into ItemsInDeal (itemId, dealName, percentage) values (0745, 'Summer Sale', 0.10);
insert into ItemsInDeal (itemId, dealName, percentage) values (0748, 'Summer Sale', 0.30);

insert into MemberShip (memberID, name, phone, points) values (1270, 'Aurelie Dibb', '266-901-5451', 28);
insert into MemberShip (memberID, name, phone, points) values (1349, 'Ingunna Tytherton', '563-282-7211', 68);
insert into MemberShip (memberID, name, phone, points) values (1088, 'Kenny Burkill', '632-473-2922', 96);
insert into MemberShip (memberID, name, phone, points) values (1023, 'Malina Bone', '725-347-0973', 218);
insert into MemberShip (memberID, name, phone, points) values (1055, 'Craggy Trott', '402-733-1676', 154);

insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3165, '20-02', '22:15', 43.77, 1252, 1);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3304, '30-01', '02:30', 95.43, 1252, 1);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3051, '15-02', '9:15', 99.48, 1040, 2);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3120, '17-04', '10:00', 60.7, 1429, 2);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3155, '12-01', '11:14', 28.25, 1093, 3);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3083, '14-11', '19:45', 78.97, 1429, 4);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3428, '23-12', '06:01', 65.73, 1441, 5);
insert into Purchase (receiptNumber, purchaseDate, purchaseTime, TotalPrice, clerkID, BranchNumber) values (3209, '11-42', '14:20', 27.54, 1429, 4);

insert into MemberPurchase (receiptNumber, memberID) values (3209, 1349);
insert into MemberPurchase (receiptNumber, memberID) values (3120, 1088);
insert into MemberPurchase (receiptNumber, memberID) values (3051, 1023);
insert into MemberPurchase (receiptNumber, memberID) values (3304, 1023);
insert into MemberPurchase (receiptNumber, memberID) values (3165, 1055);

commit;
