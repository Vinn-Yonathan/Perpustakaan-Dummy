package com.example.perpustakaanbersama;

public class BukuModel {
    private int id;
    private String judul;
    private String penerbit;
    private String penulis;
    private String isbn;
    private String genre;
    private boolean dipinjam;

    public BukuModel(int id, String judul, String penerbit, String penulis, String isbn, String genre, boolean dipinjam) {
        this.id = id;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.isbn = isbn;
        this.genre = genre;
        this.dipinjam = dipinjam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isDipinjam() {
        return dipinjam;
    }

    public void setDipinjam(int id, boolean dipinjam) {
        this.dipinjam = dipinjam;
    }

    @Override
    public String toString() {
        String status = dipinjam == true ? "Sedang dipinjam" : "Tersedia";
        return
                "ID: " + id +
                "\nJudul: " + judul +
                "\nPenerbit: " + penerbit +
                "\nPenulis: " + penulis +
                "\nISBN: " + isbn +
                "\nGenre: " + genre +
                "\nStatus: " + status;
    }
}
