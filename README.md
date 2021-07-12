# Pet-database
a database app using SQlite database

# Project Architecture
1. first I have directly used the sqlite database for query operation
2. but Soon I realised that validity of inserted is important so i used content providers as the abstraction layer betwwen the app and the database
3. content providers also helps us to protect the data from sqlite injection

