Noom Take Home Test
Hello, Lukas Lopes here.

***
## Summary
This project implements a sleep log for Noom.

The API contains 3 endpoints (included on postman collection):
POST /v1/sleeplog - create a new register on sleep log
GET /v1/sleeplog - get today's sleep log
GET /v1/sleeplog/month - get last month aggregated sleep log
*** 

## Running the project
On project root, run `docker compose up` to start the database and project.
The application runs on port 8080.
***

## Testing
On the root folder there is a Postman collection. Sleep Log - Local.postman_collection.json 
That collection contains all service APIs.

***
## Developer thoughts

Here I will discuss all my thoughts and my decision-making about the project.


For this project, I considered that a user can sleep more than once a day. That's why I didn't create a unique key for user/date.
I created verification to make sure that sleep doesn't overlap another sleep.
The total time in bed is calculated from time in bed interval.
If the user doesn't send sleepDate, it will be today.

I decided to use a straightforward apis, but the logic is easily adaptable if we need to personalize interval data.

***
I didn't fully implement a clean architecture, but I like to keep the business logic isolation.
This way, the core business logic (usecases) only knows the logic domain (domain package) and don't know database and api entities.
Controllers and databases have their own 'entities'. This way, we can make changes on databases or api without affecting business logic.
Also, we can manipulate data as needed for databases or apis freely, without affecting business logic.


***
The test is asking for tests just for repository and logic business.
For controllers, I did just basic tests to make sure the conversions work.

***
For database tests, I used a h2 in mem test. I created a script to populate the database on test/resources/testdata
I created a profile just for database tests, different from the unit tests profile. This way we don't need to start h2 for unittests.
***
For this test, I'm ignoring timezone issues
