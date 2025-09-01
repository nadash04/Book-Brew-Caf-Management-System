/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nada
 */
public class Book extends Product {

    private String author;
    private String genre;
    private boolean updated;

    public Book(int id, String name, String category, double price, int quantity,
                String imagePath, String description, boolean available,
                String author, String genre) {
        super(id, name, category, price, quantity, imagePath, description, available);
        this.author = author;
        this.genre = genre;
        this.updated = false;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        this.updated = true;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
        this.updated = true;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return super.toString() + 
               ", Book{" +
               "author='" + author + '\'' +
               ", genre='" + genre + '\'' +
               ", updated=" + updated +
               '}';
    }
}

