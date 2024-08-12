package com.example.perpustakaanbersama;

import java.util.Date;

public class PeminjamanModel {
        private int idPeminjaman;
        private int idBuku;
        private String namaBelakang;
        private String judulBuku;
        private int idPeminjam;
        private String tanggalPinjam;
        private boolean dikembalikan;


    public PeminjamanModel(int idPeminjaman, String tanggalPinjam, boolean dikembalikan, int idBuku, int idPeminjam) {
        this.idPeminjaman = idPeminjaman;
        this.tanggalPinjam = tanggalPinjam;
        this.dikembalikan = dikembalikan;
        this.idBuku = idBuku;
        this.idPeminjam = idPeminjam;
    }
    public PeminjamanModel(int idPeminjaman, String tanggalPinjam, boolean dikembalikan, String judulBuku, String namaBelakang, int idBuku, int idPeminjam) {
        this.idPeminjaman = idPeminjaman;
        this.tanggalPinjam = tanggalPinjam;
        this.dikembalikan = dikembalikan;
        this.judulBuku = judulBuku;
        this.namaBelakang = namaBelakang;
        this.idBuku = idBuku;
        this.idPeminjam = idPeminjam;
    }

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public int getIdPeminjam() {
        return idPeminjam;
    }

    public void setIdPeminjam(int idPeminjam) {
        this.idPeminjam = idPeminjam;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public boolean isDikembalikan() {
        return dikembalikan;
    }

    public void setDikembalikan(boolean dikembalikan) {
        this.dikembalikan = dikembalikan;
    }

    public String getNamaBelakang(String namaBelakang){
        return namaBelakang;
    }

    public String getJudulBuku(String judulBuku){
        return judulBuku;
    }

    @Override
    public String toString() {
        String status = dikembalikan == true ? "Sudah kembali" : "Belum kembali";
        return
                "ID Peminjaman: " + idPeminjaman +
                "\nID Buku: " + idBuku +
                "\nJudul Buku: " + judulBuku +
                "\nID Peminjam: " + idPeminjam +
                "\nNama Belakang Peminjam: " + namaBelakang +
                "\nTanggal Pinjam: " + tanggalPinjam +
                "\nStatus:  " + status;
    }
}
