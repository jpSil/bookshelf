package org.jpsil;

import java.sql.*;
import java.util.ArrayList;

// Class for connecting to database and making queries
public class DatabaseConnection {

    private String url;

    public DatabaseConnection(String url) {
        this.url = url;
    }

    // Returns connections for database at URL
    private Connection connection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(this.url);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    // Inserts books in to database
    public void insertBook(Book book) {
        String name = book.getName();
        String author = book.getAuthor();
        String publishYear = book.getPublishYear();
        String category = book.getCategory().toLowerCase();
        int hasBeenRead = book.getHasBeenRead();

        String insertStatement = "INSERT INTO books(name,author,publish_year,category,has_been_read) VALUES(?,?,?,?,?)";

        try(Connection connection = connection()) {
            PreparedStatement statement = connection.prepareStatement(insertStatement);
            statement.setString(1, name);
            statement.setString(2, author);
            statement.setString(3, publishYear);
            statement.setString(4, category);
            statement.setInt(5, hasBeenRead);
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Removes a book from database
    public void removeBook(int rowid) {
        String query = "DELETE FROM books WHERE rowid = ?";
        String reOrderRows = "VACUUM";

        try(Connection connection = this.connection()) {
            PreparedStatement delete = connection.prepareStatement(query);
            delete.setInt(1, rowid);
            delete.executeUpdate();

            PreparedStatement reOrder = connection.prepareStatement(reOrderRows);
            reOrder.executeUpdate();

        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    // Finds book that corresponds with rowid
    public Book findBook(int rowid) {
        Book book = new Book();
        String query = "SELECT rowid, name, author FROM books WHERE rowid = " + rowid;

        try(Connection connection = this.connection()) {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);

            int id = results.getInt("rowid");
            String name = results.getString("name");
            String author = results.getString("author");
            book.setRowID(id);
            book.setName(name);
            book.setAuthor(author);

        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return book;

    }

    // Lists all books in database
    public ArrayList<Book> listBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        String query = "SELECT  rowid, name, author, publish_year, category, has_been_read FROM books";

        try(Connection conn = this.connection()) {
            Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(query);

            while(results.next()) {
                Book book = new Book(results.getString("name"), results.getString("author"),
                        results.getString("publish_year"), results.getString("category"), results.getInt("has_been_read"));
                book.setRowID(results.getInt("rowid"));
                bookList.add(book);
            }

        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return bookList;

    }

    // Update book information
    public void updateBook(int rowid, String field, String info) {
        String update = "UPDATE books SET " + field + " = ? WHERE rowid = ?";

        try(Connection connection = this.connection()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, info);
            statement.setInt(2, rowid);
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Updates the has_been_read field of a book entry
    public void updateHasBeenRead(int rowid) {
        String update = "UPDATE books SET has_been_read = 1 WHERE rowid = ?";

        try(Connection connection = this.connection()) {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, rowid);
            statement.executeUpdate();
        }
        catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
