package com.example.perpustakaanbersama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Array;
import java.util.Date;
import java.util.List;


public class form_peminjaman2 extends AppCompatActivity {

    ListView lv_buku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_peminjaman2);
        showAllBuku();

        EditText et_search = findViewById(R.id.et_cariBuku);
        Button btn_search = findViewById(R.id.button_cariBuku);

        btn_search.setOnClickListener((View view) -> {
            if(et_search.getText().toString().equals("")){
                showAllBuku();
            }else{
                searchBuku(et_search.getText().toString());
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showAllBuku(){
        lv_buku = findViewById(R.id.lv_buku);
        DatabaseHelper database = new DatabaseHelper(form_peminjaman2.this);
        List<BukuModel> bukuList = database.getAllBuku();
        ArrayAdapter allBukuAdapter = new ArrayAdapter<BukuModel>(
                form_peminjaman2.this, android.R.layout.simple_list_item_1, bukuList);
        lv_buku.setAdapter(allBukuAdapter);

        lv_buku.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intentFromPrev = getIntent();
            int clickedNamaBelakangId = Integer.parseInt(intentFromPrev.getStringExtra("clickedNamaBelakangId"));
            System.out.println(clickedNamaBelakangId);

            Intent intent = new Intent(form_peminjaman2.this, main_menu.class);
            String[] bukuDataSplit = parent.getItemAtPosition(position).toString().split(System.lineSeparator());
            int clickedJudulBukuId = Integer.parseInt(bukuDataSplit[0].substring(4));

            try{
                PeminjamanModel peminjaman = new PeminjamanModel(-1,new String(), false,
                        clickedJudulBukuId, clickedNamaBelakangId);
                if(database.addPeminjaman(peminjaman)){
                    database.setStatusBuku(clickedJudulBukuId, true);
                    Toast.makeText(form_peminjaman2.this, "Buku berhasil dipinjam", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(form_peminjaman2.this, "Maaf, buku masih belum berhasil dipinjam", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                System.out.println(e);
            }
        });
    }
    public void searchBuku(String judulBuku){
        lv_buku = findViewById(R.id.lv_buku);
        DatabaseHelper database = new DatabaseHelper(form_peminjaman2.this);
        List<BukuModel> bukuList = database.getBuku(judulBuku);

        ArrayAdapter bukuAdapter = new ArrayAdapter<BukuModel>(
                form_peminjaman2.this, android.R.layout.simple_list_item_1, bukuList);
        lv_buku.setAdapter(bukuAdapter);

        lv_buku.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intentFromPrev = getIntent();
            int clickedNamaBelakangId = Integer.parseInt(intentFromPrev.getStringExtra("clickedNamaBelakangId"));

            Intent intent = new Intent(form_peminjaman2.this, main_menu.class);
            String[] bukuDataSplit = parent.getItemAtPosition(position).toString().split(System.lineSeparator());
            int clickedJudulBukuId = Integer.parseInt(bukuDataSplit[0].substring(4));

            try {
                PeminjamanModel peminjaman = new PeminjamanModel(-1,new String(),false,
                        clickedJudulBukuId, clickedNamaBelakangId);
                if (database.addPeminjaman(peminjaman)) {
                    database.setStatusBuku(clickedJudulBukuId, true);
                    Toast.makeText(form_peminjaman2.this, "Buku berhasil dipinjam", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(form_peminjaman2.this, "Maaf, buku masih belum berhasil dipinjam", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        });

    }
}