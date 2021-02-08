# Bookshelf

Bookshelf is a CLI application for storing information about books you have read and want to read.

## Description

Bookshelf is a simple way to store information about any books you have read, <br/>
or would like to read. You can add and remove books, or modify existing entries, <br/>
and list all the books you have in your bookshelf.

## Getting Started

Bookshelf uses a sqlite database file for storing information.
You can create a new file on start up, or select an existing one. <br/>
Please do not use spaces or other special characters for database names. <br/>
Navigating the application is easy, as you will be prompted to select the next action.

### Dependencies

* Sqlite JDBC Driver

### Installing

* Clone the repo from github:
```
git clone https://github.com/jpSil/bookshelf.git
```
* Navigate to project folder and run:
```
mvn package
```
This will create a Jar with the Sqlite driver dependency inside.
### Executing program

* To execute the program run the command:
```
java -jar bookshelf-1.0.jar
```