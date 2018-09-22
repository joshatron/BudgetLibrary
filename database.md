Database Structure
==================

There are tables for the transactions and vendors, along with namings of the vendors for identification.

### Tables

#### Transactions

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each transaction           |
| timestamp       | long           | NOT NULL                  | Time of the transaction          |
| amount          | int            | NOT NULL                  | Amount for transaction           |
| account         | string         | NOT NULL                  | Account transaction was made on  |
| vendor          | int            | NOT NULL                  | The vendor for the transaction   |

#### Vendors

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each vendor                |
| name            | string         | NOT NULL                  | name of the vendor               |
| type            | string         | NOT NULL                  | the type of product sold         |

#### Vendor_namings

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each naming                |
| vendor          | string         | NOT NULL                  | name of the vendor               |
| raw             | string         | NOT NULL                  | raw name of the vendor           |
