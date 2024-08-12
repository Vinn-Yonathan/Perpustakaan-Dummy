package com.example.perpustakaanbersama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAMA = "PerpustakaanBersama";

//  Tabel
    private static final String TABEL_BUKU = "tabel_buku";
    private static final String TABEL_ANGGOTA = "tabel_anggota";
    private static final String TABEL_PEMINJAMAN = "tabel_peminjaman";

    public DatabaseHelper(Context context){
        super(context, DB_NAMA, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABEL_ANGGOTA = "CREATE TABLE " + TABEL_ANGGOTA +
                "(" +
                    "id_anggota INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nama_depan TEXT," +
                    "nama_belakang TEXT," +
                    "kota TEXT," +
                    "jenis_kelamin TEXT," +
                    "no_telepon TEXT" +
                ")";

        String CREATE_TABEL_BUKU = "CREATE TABLE " + TABEL_BUKU +
                "(" +
                    "id_buku INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "judul TEXT," +
                    "penerbit TEXT," +
                    "penulis TEXT," +
                    "isbn TEXT," +
                    "genre TEXT," +
                    "dipinjam BOOLEAN" +
                ")";

        String CREATE_TABEL_PEMINJAMAN = "CREATE TABLE " + TABEL_PEMINJAMAN +
                "(" +
                    "id_peminjaman INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tanggal_pinjam TEXT DEFAULT current_timestamp," +
                    "dikembalikan BOOLEAN," +
                    "id_anggota INTEGER," +
                    "id_buku INTEGER," +
                    "FOREIGN KEY (id_anggota) REFERENCES "+ TABEL_ANGGOTA + " (id_anggota)," +
                    "FOREIGN KEY (id_buku) REFERENCES "+ TABEL_BUKU + " (id_buku)" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TABEL_ANGGOTA);
        sqLiteDatabase.execSQL(CREATE_TABEL_BUKU);
        sqLiteDatabase.execSQL(CREATE_TABEL_PEMINJAMAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABEL_ANGGOTA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABEL_BUKU);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABEL_PEMINJAMAN);
        onCreate(sqLiteDatabase);
    }

    public boolean addBuku(BukuModel buku){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("judul", buku.getJudul());
        cv.put("penerbit", buku.getPenerbit());
        cv.put("penulis", buku.getPenulis());
        cv.put("isbn", buku.getIsbn());
        cv.put("genre", buku.getGenre());
        cv.put("dipinjam", buku.isDipinjam());

        long i = database.insert(TABEL_BUKU, null, cv);
        return i != -1;
    }

    public boolean addPeminjaman(PeminjamanModel peminjaman){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dikembalikan", peminjaman.isDikembalikan());
        cv.put("id_anggota", peminjaman.getIdPeminjam());
        cv.put("id_buku", peminjaman.getIdBuku());

        long i = database.insert(TABEL_PEMINJAMAN, null, cv);
        return i != -1;
    }

    public boolean addAnggota(AnggotaModel anggota){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama_depan", anggota.getNamaDepan());
        cv.put("nama_belakang", anggota.getNamaBelakang());
        cv.put("kota", anggota.getKota());
        cv.put("jenis_kelamin", anggota.getJenisKelamin());
        cv.put("no_telepon", anggota.getNoTelepon());

        long i = database.insert(TABEL_ANGGOTA, null, cv);
        return i != -1;
    }

    public List<PeminjamanModel> getAllPeminjaman(){
        List<PeminjamanModel> peminjamanList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABEL_PEMINJAMAN + " WHERE dikembalikan = 0";
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String tanggalPinjam =  cursor.getString(1);
                boolean dikembalikan = cursor.getInt(2) == 1 ? true : false;
                int idAnggota =  cursor.getInt(3);
                int idBuku =  cursor.getInt(4);
                String namaPeminjam = getNamaBelakangPeminjam(idAnggota);
                String judulBuku = getJudulBukuDipinjam(idBuku);
                PeminjamanModel peminjaman = new PeminjamanModel(id, tanggalPinjam, dikembalikan, judulBuku, namaPeminjam, idBuku, idAnggota);
                peminjamanList.add(peminjaman);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return peminjamanList;
    }

    public List<BukuModel> getAllBuku(){
        List<BukuModel> bukuList = new ArrayList<>();
        String query = "SELECT * FROM tabel_buku WHERE dipinjam = 0";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String judul = cursor.getString(1);
                String penerbit = cursor.getString(2);
                String penulis = cursor.getString(3);
                String isbn = cursor.getString(4);
                String genre = cursor.getString(5);
                boolean dipinjam = cursor.getInt(6) == 1 ? true : false;
                BukuModel buku = new BukuModel(id, judul, penerbit, penulis, isbn, genre, dipinjam);
                bukuList.add(buku);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return bukuList;
    }

    public List<PeminjamanModel> getPeminjaman(String namaBelakang){
        List<PeminjamanModel> peminjamanList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT id_anggota FROM " + TABEL_ANGGOTA + " WHERE nama_belakang = '" + namaBelakang + "'", null);
        int searchedIdAnggota = cursor.moveToFirst() ? cursor.getInt(0) : -1;
        cursor.close();
        String query = "SELECT * FROM " + TABEL_PEMINJAMAN + " WHERE id_anggota = " + searchedIdAnggota + " AND dikembalikan = 0";
        cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String tanggalPinjam =  cursor.getString(1);
                boolean dikembalikan = cursor.getInt(2) == 1 ? true : false;
                int idAnggota =  cursor.getInt(3);
                int idBuku =  cursor.getInt(4);
                String namaPeminjam = getNamaBelakangPeminjam(idAnggota);
                String judulBuku = getJudulBukuDipinjam(idBuku);
                PeminjamanModel peminjaman = new PeminjamanModel(id, tanggalPinjam, dikembalikan, judulBuku, namaPeminjam, idBuku, idAnggota);
                peminjamanList.add(peminjaman);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return peminjamanList;
    }

    public List<BukuModel> getBuku(String searchJudulBuku){
        List<BukuModel> bukuList = new ArrayList<>();
        String query = "SELECT * FROM tabel_buku WHERE judul = '" + searchJudulBuku + "' AND dipinjam = 0";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String judul = cursor.getString(1);
                String penerbit = cursor.getString(2);
                String penulis = cursor.getString(3);
                String isbn = cursor.getString(4);
                String genre = cursor.getString(5);
                boolean dipinjam = cursor.getInt(6) == 1 ? true : false;
                BukuModel buku = new BukuModel(id, judul, penerbit, penulis, isbn, genre, dipinjam);
                bukuList.add(buku);
            }while(cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return bukuList;
    }

    public List<AnggotaModel> getAnggota(String searchNamaBelakang){
        List<AnggotaModel> anggotaList = new ArrayList<>();
        String query = "SELECT * FROM " + TABEL_ANGGOTA + " WHERE nama_belakang = '" + searchNamaBelakang + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor= database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String namaDepan = cursor.getString(1);
                String namaBelakang = cursor.getString(2);
                String kota = cursor.getString(3);
                String gender = cursor.getString(4);
                String no_telepon = cursor.getString(5);
                AnggotaModel anggota = new AnggotaModel(id, namaDepan, namaBelakang, kota, gender, no_telepon);
                anggotaList.add(anggota);
            }while(cursor.moveToNext());
        }
        return anggotaList;
    }

    public void setStatusBuku(int idBuku, boolean status){
        SQLiteDatabase database = this.getWritableDatabase();
        int statusInt = status == true ? 1 : 0;
        String query = "UPDATE " + TABEL_BUKU + " SET dipinjam = "+ statusInt +" WHERE id_buku = " + idBuku;
        database.execSQL(query);
    }

    public void setStatusPinjam(int idPeminjaman, boolean status){
        SQLiteDatabase database = this.getWritableDatabase();
        int statusInt = status == true ? 1 : 0;
        String query = "UPDATE " + TABEL_PEMINJAMAN + " SET dikembalikan = "+ statusInt +" WHERE id_peminjaman = " + idPeminjaman;
        database.execSQL(query);
    }

    public String getNamaBelakangPeminjam(int idPeminjam){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT nama_belakang FROM " + TABEL_ANGGOTA + " WHERE id_anggota = " + idPeminjam;
        Cursor cursor = database.rawQuery(query, null);
        return cursor.moveToFirst() ? cursor.getString(0) : "Unknown";
    }

    public String getJudulBukuDipinjam(int idBuku){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT judul FROM " + TABEL_BUKU + " WHERE id_buku = " + idBuku;
        Cursor cursor = database.rawQuery(query, null);
        return cursor.moveToFirst() ? cursor.getString(0) : "Unknown";
    }
}
