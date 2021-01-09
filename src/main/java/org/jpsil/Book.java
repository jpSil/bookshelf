package org.jpsil;

// Represents a book in database
public class Book {

    private int rowID;
    private String name;
    private String author;
    private String publishYear;
    private String category;
    private int hasBeenRead;
    private int owned;

    public Book(String name, String author, String publishYear, String category, int hasBeenRead, int owned) {
        this.name = name;
        this.author = author;
        this.publishYear = publishYear;
        this.category = category;
        this.hasBeenRead = hasBeenRead;
        this.owned = owned;
    }

    public Book() {}

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public int getRowID() {
        return rowID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public int getHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(int hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public int getOwned() {
        return owned;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }
}
