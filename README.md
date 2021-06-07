# Codecool shop

## Introduction

The Codecool shop was two sprints teamwork project in the java-based OOP module of our full-stack programmer course.
The main aim was to learn and experience that how can different technologies cooperate and fit together.
The project includes some basic unit tests linked to the cart activities of the user. 

## Functional information for users

The starting page of the shop contains selection fields by product categories and providers and the image of the product
from one same product category. The user can browse within the product database and can place the product into the cart. 
When the user finishes the product selections, can move forward to check-in and payment pages or can go back and modify 
the order.
Users can register to the shop when they receive a confirmation mail into their mailbox.

The store has an admin interface where the mailbox of the shop can be defined by the proper person who has admin 
permission for it. The link to the admin sheet is only displayed when one of the admin persons is logged in.


## Technologies

![](tech_logos.png)

The project use java servlets and PostgreSQL for implementing back-end tasks, and thymeleaf, HTML, CSS for front-end.
Log4j technology - with property file - apply to log out the main user activities and their consequences.
Mockito applied for unit testing of the cart operating.


## Project status

Even though, due to the short time, not all features were 100% targeted, our team tried to implement as many tasks as possible.
The targeted memory-based functionalities are completed as it can be reached in the "development" branch.
The implementation of further tasks is in progress on the unmerged branches.

## Setting up database

The runnable version can be found on the development branch of the repository.

1. After cloning the project to the local machine, the next step is the creation of an SQL database within the 
   local repository:
   sudo -u <user_name> createdb <name_of_database><br>

2. Initiating connection to the created database: <br>
   \connect <name_of_database>
   
3. Move to "sample_data/" folder and enter command into the terminal:<br>
   psql -U <username> <name_of_database> < init_db.sql

4. Configure your created database within your IDE

## How to run

You need a running web server for the operation of the webshop.
We applied Jetty server for this aim, with the further command: 
jetty:run -Djetty.port=8888