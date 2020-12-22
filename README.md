# code-test
banking test application

Create Account Holder
---------------------
POST Request : 
http://localhost:9090/accountHolder

{
    "accountName": "Ram Achanta"
}

Response

{
    "id": 1,
    "accountName": "Ram Achanta",
    "accounts": null
}

Get all accountHolders
http://localhost:9090/accountHolders

Get accountHolder and accounts by id
http://localhost:9090/accountHolder/1

Create Accounts for a accountHolder
-----------------------------------
POST Request : 
http://localhost:9090/account
 {
   "accountHolder": {"id": 1 },
    "balance": 4000,
    "currency": "AUD",
    "accountType" : "SAVINGS"
 }
 
 Get all accounts
 http://localhost:9090/accounts
 
 Get an account and Transactions by id 
 http://localhost:9090/account/3
 
Create a Transaction
-----------------------------------
POST Request : 
 http://localhost:9090/transaction
 
  {
   "account": {"accountNumber":4 },
    "transactionType": "CREDIT",
    "currency": "SGD",
    "creditAmount": 100,
    "transactionNarrative": "Test"
 }
  
  Get all transactions
 http://localhost:9090/transactions
 
 Get an transaction by id
 http://localhost:9090/transaction/3
