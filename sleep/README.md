Hello, Lukas Lopes here.

On this document, I will put all my thoughts on my decision making.

***

    For database, I will save both informations, this way its easier to to create filters or searchs on future.


***
For this test, I'm ignoring timezone issues, using ZoneOffset.UTC as default.

***
The test is asking for tests just for database and logic business. I did just basic controllers test to make sure the conversions works.

***
I not fully implemented a clean architecture, but I like to keep the business logic isolation.
This way, the core business logic (usecases) only know the logic entities (entity package) and dont know database and api entities.
Controllers and database have their own 'entities'. This way, we can make changes on database or api without affecting business logic.
Also, we can manipulate data as needed for databases or apis freely, without affecting business logic.


***
For database tests, i used an h2 in mem test. I created a script to populate the database on test/resources/testdata

***
I dont know if user can have more than 1 sleep per day, so, i dont created a unique index per user/date

***
For this test, i will use a generic exception handler instead of a specific per error to save time.