package model;

import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public static boolean insertBook(Book book) {
        String sql = "INSERT INTO books(book_title, book_author, book_genre, book_imagepath, book_available) VALUES(?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setString(4, book.getImagePath());
            ps.setBoolean(5, book.isAvailable());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting book: " + e.getMessage());
            return false;
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet res = ps.executeQuery()) {

            while (res.next()) {
                Book book = new Book(
                        res.getInt("book_id"),
                        res.getString("book_title"),
                        "Books",
                        0.0,
                        1,
                        res.getString("book_imagepath"),
                        "",
                        res.getBoolean("book_available"),
                        res.getString("book_author"),
                        res.getString("book_genre")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
        return books;
    }

    public static boolean updateBook(Book book) {
        String sql = "UPDATE books SET book_title=?, book_author=?, book_genre=?, book_imagepath=?, book_available=? WHERE book_id=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getGenre());
            ps.setString(4, book.getImagePath());
            ps.setBoolean(5, book.isAvailable());
            ps.setInt(6, book.getId());

            boolean updated = ps.executeUpdate() > 0;
            book.setUpdated(updated);
            return updated;
        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteBook(int bookId) {
        String sql = "DELETE FROM books WHERE book_id=?";
        try (Connection con = DatabaseConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }

    public static void insertDefaultBooks() {
        Book[] defaultBooks = {
            new Book(0, "Al-Hulooli Book", "Books", 3.0, 1,
            "images/book1.jpg", "A great book", true, "Author1", "Fiction"),
            new Book(0, "The Subtle Art of Not Giving a F*ck", "Books", 2.0, 1,
            "images/book2.jpg", "Self-help book", true, "Mark Manson", "Self-Help"),
            new Book(0, "Time Management Book", "Books", 4.0, 1,
            "images/book3.jpg", "Productivity guide", true, "Author3", "Business"),
            new Book(0, "Children's Diary Book", "Books", 3.0, 1,
            "images/book4.jpg", "For kids", true, "Author4", "Children"),
            new Book(0, "Girls' Rules Book", "Books", 5.0, 1,
            "images/book5.jpg", "Empowering book", true, "Author5", "Inspirational"),
            new Book(0, "Zikola Land 2 Book", "Books", 3.0, 1,
            "images/book6.jpg", "Adventure story", true, "Author6", "Adventure"),
            new Book(0, "Productive Thinking Book", "Books", 2.0, 1,
            "images/book7.jpg", "Improve your thinking", true, "Author7", "Self-Help"),
            new Book(0, "Rabri Cookies Book", "Books", 4.0, 1,
            "images/book8.jpg", "Cooking guide", true, "Author8", "Cooking")
        };

        for (Book book : defaultBooks) {
            insertBook(book);
        }
    }
}
