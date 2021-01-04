package org.jpsil;

import java.util.ArrayList;
import java.util.Scanner;

// Holds everything needed for running application
public class AppLogic {

    private final Scanner input;
    private String URL;
    private final DatabaseConnection connection;

    public AppLogic(String URL) {
        input = new Scanner(System.in);
        this.URL = URL;
        connection = new DatabaseConnection(URL);
    }

    // Runs application
    public void runApp() {
        System.out.println("WELCOME TO YOUR BOOKSHELF");
        System.out.println("------------------------------------------");

        input.useDelimiter("\\n");

        boolean running = true;
        int choice;
        while(running) {
            System.out.println("What would you like to do?");
            System.out.println("1: Add a new book to your shelf");
            System.out.println("2: Modify book information");
            System.out.println("3. Mark a book as read");
            System.out.println("4. List all the books on your shelf");
            System.out.println("5. Exit your bookshelf");
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
                    listBooks();
                    break;
                case 5:
                    System.out.println("Closing bookshelf, goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("No valid choice made");
                    printLines();

            }
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

        System.out.print("Have you read the book? Y/N ");
        int hasBeenRead;
        if(input.next().toLowerCase().equals("y")) {
            hasBeenRead = 1;
        }
        else {
            hasBeenRead = 0;
        }
        System.out.println();

        System.out.println("Placing " + name + " in to bookshelf");
        Book book = new Book(name, author, publishYear, category, hasBeenRead);
        connection.insertBook(book);

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

            System.out.println("ID: " + rowID + " | " + "Name: " + name + " | Author: " + author + " | Category: " + category + " | Read it: " + readIt);
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