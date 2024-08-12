package com.example.perpustakaanbersama;

public class AnggotaModel {
    private int id;
    private String namaDepan;
    private String namaBelakang;
    private String kota;
    private String jenisKelamin;
    private String noTelepon;

    public AnggotaModel(int id, String namaDepan, String namaBelakang, String kota, String jenisKelamin, String noTelepon) {
        this.id = id;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
        this.kota = kota;
        this.jenisKelamin = jenisKelamin;
        this.noTelepon = noTelepon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaDepan() {
        return namaDepan;
    }

    public void setNamaDepan(String namaDepan) {
        this.namaDepan = namaDepan;
    }

    public String getNamaBelakang() {
        return namaBelakang;
    }

    public void setNamaBelakang(String namaBelakang) {
        this.namaBelakang = namaBelakang;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    @Override
    public String toString() {
        return
                "ID: " + id +
                "\nNama Depan: " + namaDepan  +
                "\nNama Belakang: " + namaBelakang  +
                "\nKota: " + kota  +
                "\nJenis Kelamin: " + jenisKelamin  +
                "\nNomor Telepon: " + noTelepon;
    }
}
