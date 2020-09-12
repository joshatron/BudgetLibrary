Data Model
==========

There is a lot of data that can be added to a transaction.
Below, I will try and explain the model that this application uses.

Transaction
-----------

This is the base and main unit.
Most analysis will be done on these transactions.

 * ID (UUID)
 * description
 * Amount (in cents)
 * Transaction partner (ID)
 * tags
 
Transaction Partner
-------------------
 
These are who transactions are done with.
These can be stores, people, or something else.
This is separated from transactions to save memory and make edits easier.

 * ID (UUID)
 * Name
 * Description
