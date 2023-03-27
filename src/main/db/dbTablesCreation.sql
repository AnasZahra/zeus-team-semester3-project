create table if not exists
        Booking_trash_collection (Booking_id INT PRIMARY KEY,
        Room_Number INT NOT NULL,
        Floor_Number INT NOT NULL,
        Start_Date DATE NOT NULL,
        End_Date DATE NOT NULL,
        Guest VARCHAR(255) NOT NULL,
        Payment_id INT NOT NULL,
        FOREIGN KEY (Payment_id) REFERENCES Payments(Payment_id) )


create table if not exists
        address_trash_collection (id INT NOT NULL,
        Country VARCHAR(255) NOT NULL,
        City VARCHAR(255) NOT NULL,
        Street VARCHAR(255) NOT NULL,
        PostCode INT NOT NULL,
        House_Number INT NOT NULL,
        PRIMARY KEY (id) )


create table if not exists
        Person_trash_collection (id INT PRIMARY KEY,
        Firstname VARCHAR(255) NOT NULL,
        Lastname VARCHAR(255) NOT NULL,
        Birthday DATE NOT NULL,
        Email VARCHAR(255),
        Phone_Number VARCHAR(12),
        address_id INT NOT NULL,
        FOREIGN KEY (address_id) REFERENCES Address(id))


