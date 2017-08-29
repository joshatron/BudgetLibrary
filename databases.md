Database Structure
==================

The main tables will be the transactions, vendors, and categories, with some other helper ones

###Tables

####transactions

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each transaction           |
| timestamp       | string         | NOT NULL                  | Time of the transaction          |
| amount          | double         | NOT NULL                  | Amount for transaction           |
| vendor          | int            | NOT NULL                  | The vendor for the transaction   |

####vendors

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each vendor                |
| name            | string         | NOT NULL                  | name of the vendor               |
| category        | int            | NOT NULL                  | The category for the vendor      |

####vendor_tags

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each tag                   |
| name            | string         | NOT NULL                  | name of the tag                  |
| description     | string         |                           | description of the tag           |

####vendor_taggings

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each tagging               |
| vendor_id       | int            | NOT NULL                  | id of the vendor                 |
| tag_id          | int            | NOT NULL                  | id of the tag                    |

####vendor_namings

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each naming                |
| vendor_id       | int            | NOT NULL                  | id of the vendor                 |
| name            | string         | NOT NULL                  | name of the vendor               |

####categories

| Name            | Data Type      | Parameters                | Description                      |
|-----------------|----------------|---------------------------|----------------------------------|
| id              | int            | PRIMARY KEY AUTOINCREMENT | id of each category              |
| name            | string         | NOT NULL                  | name of the category             |
| description     | string         |                           | description of the category      |
| budget          | double         | NOT NULL                  | budget for the category          |
