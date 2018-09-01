Database Structure
==================

There are tables for the transactions and vendors, along with namings of the vendors for identification.

###Tables

####transactions

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each transaction           |
| timestamp       | string         | NOT NULL                  | Time of the transaction          |
| amount          | int            | NOT NULL                  | Amount for transaction           |
| vendor          | int            | NOT NULL                  | The vendor for the transaction   |

####vendors

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each vendor                |
| name            | string         | NOT NULL                  | name of the vendor               |
| type            | string         | NOT NULL                  | the type of product sold         |

####vendor_namings

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each naming                |
| vendor          | string         | NOT NULL                  | name of the vendor               |
| raw             | string         | NOT NULL                  | raw name of the vendor           |
