package org.jpsil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Scanner;

// Holds everything needed for running application
public class AppLogic {



    private final Scanner input;
    private String URL;
    private DatabaseConnection connection;

    public AppLogic() {
        input = new Scanner(System.in);
    }

    // Runs application
    public void runApp() {
        input.useDelimiter("\\n");

        int initiate = initiateConnection();
        if (initiate == 0) {

            System.out.println("WELCOME TO YOUR BOOKSHELF");
            printLines();

            boolean running = true;
            int choice;
            while (running) {
                System.out.println("What would you like to do?");
                System.out.println("1: Add a new book to your shelf");
                System.out.println("2: Modify book information");
                System.out.println("3. Mark a book as read");
                System.out.println("4. Mark a book as owned");
                System.out.println("5. List all the books on your shelf");
                System.out.println("6. Remove a book from your shelf");
                System.out.println("7. Exit your bookshelf");
                printLines();

                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        addBookToShelf();
                        break;
                    case 2:
                        modifyBookInfo();
                        break;
                    case 3:
                        markBookAsRead();
                        break;
                    case 4:
                        markBookAsOwned();
                        break;
                    case 5:
                        listBooks();
                        break;
                    case 6:
                        removeBookFromShelf();
                        break;
                    case 7:
                        System.out.println("Closing bookshelf, goodbye!");
                        input.close();
                        running = false;
                        break;
                    default:
                        System.out.println("No valid choice made");
                        printLines();

                }
            }
        }
        else {
            input.close();
            System.out.println("Exiting program... goodbye!");
        }
    }

    public int initiateConnection() {
        int exit;
        System.out.println("Please select a bookshelf: ");

        File[] bookshelves = findDatabases();
        listDatabases(bookshelves);
        System.out.println(bookshelves.length + 1 + ": Exit program");

        int userChoice = input.nextInt();
        if(userChoice == bookshelves.length + 1) {
            return exit = 1;
        } else {
            this.URL = "jdbc:sqlite:" + bookshelves[userChoice - 1].getPath();
            this.connection = new DatabaseConnection(URL);
        }
        return exit = 0;

    }

    // Finds all database files in program directory
    public File[] findDatabases() {
        File dir = new File("./");
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".db");
            }
        });

        return files;
    }

    // lists all database files in program directory
    public void listDatabases(File[] files) {
        for(int index = 0; index < files.length; index++) {
            System.out.println(index + 1 +": " + files[index].getName());
        }
    }

    // Adds books to bookshelf
    public void addBookToShelf() {
        System.out.println("Please enter book details:");

        System.out.print("Name: ");
        String name = input.next();

        System.out.print("Author: ");
        String author = input.next();

        System.out.print("Publish year: ");
        String publishYear = input.next();

        System.out.print("Category: ");
        String category = input.next();

        System.out.print("Have you read the book? Y/N: ");
        int hasBeenRead;
        if(input.next().toLowerCase().equals("y")) {
            hasBeenRead = 1;
        }
        else {
            hasBeenRead = 0;
        }
        System.out.print("Do you own the book? Y/N: ");
        int owned;
        if(input.next().toLowerCase().equals("y")) {
            owned = 1;
        }
        else {
            owned = 0;
        }

        System.out.println();

        System.out.println("Placing " + name + " in to bookshelf");
        Book book = new Book(name, author, publishYear, category, hasBeenRead, owned);
        connection.insertBook(book);

        printLines();
    }

    // Removes a book from the shelf
    public void removeBookFromShelf() {
        System.out.println("Please enter the ID of the book you want to remove");
        int rowid = input.nextInt();

        Book book = connection.findBook(rowid);
        System.out.println("Removing " + book.getName() + " by " + book.getAuthor() + " ID: " + book.getRowID());

        connection.removeBook(rowid);
        System.out.println("Book removed from shelf");

        printLines();

    }

    // Lists all books on bookshelf
    public void listBooks() {
        System.out.println("Listing all books on shelf: ");
        System.out.println();

        ArrayList<Book> books = connection.listBooks();
        for(int i = 0; i < books.size(); i++) {
            int rowID = books.get(i).getRowID();
            String name = books.get(i).getName();
            String author = books.get(i).getAuthor();
            String category = books.get(i).getCategory();

            String readIt;
            if(books.get(i).getHasBeenRead() == 1) {
                readIt = "Yes";
            }
            else {
                readIt = "No";
            }

            String owned;
            if(books.get(i).getOwned() == 1) {
                owned = "Yes";
            }
            else {
                owned = "No";
            }

            System.out.println("ID: " + rowID + " | " + "Name: " + name + " | Author: " + author + " | Category: " + category +
                    " | Read it: " + readIt + " | Owned: " + owned);
        }
        printLines();
    }

    // Marks a book as read
    public void markBookAsRead() {
        System.out.println("Please enter the ID of the book you want to mark as 'read'");
        int rowid = input.nextInt();

        connection.updateHasBeenRead(rowid);
        Book book = connection.findBook(rowid);

        System.out.println();
        System.out.println("Marking as read: " + book.getRowID() + " | " + book.getName() + " | " + book.getAuthor());

        printLines();
    }

    // Marks a book as read
    public void markBookAsOwned() {
        System.out.println("Please enter the ID of the book you want to mark as 'owned'");
        int rowid = input.nextInt();

        connection.updateOwned(rowid);
        Book book = connection.findBook(rowid);

        System.out.println();
        System.out.println("Marking as read: " + book.getRowID() + " | " + book.getName() + " | " + book.getAuthor());

        printLines();
    }

    // Updates information of a database field
    public void modifyBookInfo() {
        System.out.print("Please enter book ID: ");
        int rowid = input.nextInt();

        System.out.print("Please enter field you want to modify: ");
        String field = input.next();

        System.out.print("Please enter new information: ");
        String info = input.next();

        System.out.println("Updating book information...");
        connection.updateBook(rowid, field, info);

        System.out.println("Information updated:");
        Book book = connection.findBook(rowid);
        System.out.println("ID: " + book.getRowID() + " | " + "Name: " + book.getName() + " | Author: " + book.getAuthor() + " | Category: " + book.getCategory());

        printLines();
    }


    // Prints dashed line
    public void printLines() {
        System.out.println("-----------------------------------------------------");
        System.out.println();
    }

}
