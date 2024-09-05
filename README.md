Noom take home test
Hello, Lukas Lopes here.

On this document, I will put all my thoughts on my decision-making.

The API contains 3 endpoints (included on postman collection): 
POST /v1/sleeplog - create a new register on sleep log
GET /v1/sleeplog - get today's sleep log
GET /v1/sleeplog/month - get last month aggregated sleep log

The total time in bed is calculated from time in bed interval.
If the user don't send sleepDate, it will be today.

I decided to use a straightforward apis, but the logic is easily adaptable if we need to personalize interval data.

***
I not fully implemented a clean architecture, but I like to keep the business logic isolation.
This way, the core business logic (usecases) only know the logic entities (entity package) and dont know database and api entities.
Controllers and database have their own 'entities'. This way, we can make changes on database or api without affecting business logic.
Also, we can manipulate data as needed for databases or apis freely, without affecting business logic.


***
The test is asking for tests just for repository and logic business. For controllers, I did just basic tests to make sure the conversions works.

***
For database tests, i used an h2 in mem test. I created a script to populate the database on test/resources/testdata
I created a profile just for database tests, different from unittests profile. This way we dont need to start h2 for unittests.
***
I dont know if user can have more than 1 sleep per day, so, i dont created a unique index per user/date

***
For this test, I'm ignoring timezone issues

***
For this test, i will use a generic exception handler instead of a specific per error to save time.
I'm 'manually' validating some request fields for same reason