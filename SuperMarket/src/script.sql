DROP
    DATABASE IF EXISTS SuperMarket;
CREATE
    DATABASE IF NOT EXISTS SuperMarket;
SHOW
    DATABASES;
USE
    SuperMarket;
#-------------------
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer
(
    custId     VARCHAR(15),
    custTitle  VARCHAR(5),
    name       VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    address    TEXT,
    city       VARCHAR(20),
    province   VARCHAR(20),
    postalCode VARCHAR(9),
    CONSTRAINT PRIMARY KEY
        (
         custId
            )
);
SHOW
    TABLES;
DESCRIBE Customer;
#---------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`
(
    orderId   VARCHAR(15),
    orderDate date,
    cId       VARCHAR(15),
    CONSTRAINT PRIMARY KEY
        (
         orderId
            ),
    CONSTRAINT FOREIGN KEY
        (
         cId
            ) REFERENCES Customer
            (
             custId
                ) ON DELETE CASCADE
        ON UPDATE CASCADE
);
SHOW
    TABLES;
DESCRIBE `Order`;
#-----------------------
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item
(
    code        VARCHAR(15),
    description TEXT,
    packSize    VARCHAR(20),
    unitPrice   DOUBLE DEFAULT 0.00,
    qtyOnHand   INT    DEFAULT 0,
    CONSTRAINT PRIMARY KEY
        (
         code
            )
);
SHOW
    TABLES;
DESCRIBE Item;
#------------------------
DROP TABLE IF EXISTS OrderDetails;
CREATE TABLE IF NOT EXISTS OrderDetails
(
    itemCode VARCHAR(15),
    orderId  VARCHAR(15),
    qty      INT,
    discount DOUBLE,
    CONSTRAINT PRIMARY KEY
        (
         itemCode,
         orderId
            ),
    CONSTRAINT FOREIGN KEY
        (
         itemCode
            ) REFERENCES Item
            (
             code
                ) ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY
        (
         orderId
            ) REFERENCES `Order`
            (
             orderId
                )
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
SHOW
    TABLES;
DESCRIBE `Order Detail`;

CREATE TABLE CashierLogin
(
    cashierUsrName  VARCHAR(20),
    cashierPassword VARCHAR(15),
    CONSTRAINT PRIMARY KEY (cashierPassword)
);

INSERT INTO CashierLogin
VALUES ('Cashier', 'cashier123');

CREATE TABLE DiscountCodes
(
    dis_Code  VARCHAR(20),
    dis_Price DOUBLE,
    CONSTRAINT PRIMARY KEY (dis_Code)
);

INSERT INTO DiscountCodes
VALUES ('MBONANZA', 20);
INSERT INTO DiscountCodes
VALUES ('FLASHSALE', 20);
INSERT INTO DiscountCodes
VALUES ('FORYOU', 20);
INSERT INTO DiscountCodes
VALUES ('OFFF', 20);
INSERT INTO DiscountCodes
VALUES ('HUGETOP', 20);

CREATE TABLE AdminLogin
(
    adminUsrName  VARCHAR(20),
    adminPassword VARCHAR(15),
    CONSTRAINT PRIMARY KEY (adminPassword)
);

INSERT INTO AdminLogin
VALUES ('Admin', 'admin123');