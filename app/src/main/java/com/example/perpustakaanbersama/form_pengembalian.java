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

import java.util.List;

public class form_pengembalian extends AppCompatActivity {
    ListView lv_pengembalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pengembalian);
        showAllPeminjaman();

        EditText et_search = findViewById(R.id.et_cariPeminjaman);
        Button btn_search = findViewById(R.id.button_cariPeminjaman);

        btn_search.setOnClickListener((View view)->{
            if(et_search.getText().toString().equals("")){
                showAllPeminjaman();
            }else{
                searchPeminjaman(et_search.getText().toString());
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void showAllPeminjaman(){
        lv_pengembalian = findViewById(R.id.lv_peminjaman);
        DatabaseHelper database = new DatabaseHelper(form_pengembalian.this);
        List<PeminjamanModel> peminjamanList = database.getAllPeminjaman();

        ArrayAdapter allPeminjamanAdapter = new ArrayAdapter<PeminjamanModel>(
                form_pengembalian.this, android.R.layout.simple_list_item_1, peminjamanList
        );
        lv_pengembalian.setAdapter(allPeminjamanAdapter);

        lv_pengembalian.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(form_pengembalian.this, main_menu.class);
            String[] peminjamanDataSplit = parent.getItemAtPosition(position).toString().split(System.lineSeparator());
            int clickedPeminjamanId = Integer.parseInt(peminjamanDataSplit[0].substring(15));
            int clickedBukuId = Integer.parseInt(peminjamanDataSplit[1].substring(9));
            try{
//                Toast.makeText(form_pengembalian.this, "" + clickedPeminjamanId, Toast.LENGTH_SHORT).show();
                Toast.makeText(form_pengembalian.this, "Buku berhasil dikembalikan", Toast.LENGTH_SHORT).show();
                database.setStatusPinjam(clickedPeminjamanId, true);
                database.setStatusBuku(clickedBukuId, false);
                startActivity(intent);
            }catch (Exception e){
                System.out.println(e);
            }
        });
    }

    public void searchPeminjaman(String namaBelakang){
        lv_pengembalian = findViewById(R.id.lv_peminjaman);
        DatabaseHelper database = new DatabaseHelper(form_pengembalian.this);
        List<PeminjamanModel> peminjamanList = database.getPeminjaman(namaBelakang);

        ArrayAdapter peminjamanAdapter = new ArrayAdapter<PeminjamanModel>(
                form_pengembalian.this, android.R.layout.simple_list_item_1, peminjamanList
        );
        lv_pengembalian.setAdapter(peminjamanAdapter);

        lv_pengembalian.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Intent intent = new Intent(form_pengembalian.this, main_menu.class);
            String[] peminjamanDataSplit = parent.getItemAtPosition(position).toString().split(System.lineSeparator());
            int clickedPeminjamanId = Integer.parseInt(peminjamanDataSplit[0].substring(15));
            int clickedBukuId = Integer.parseInt(peminjamanDataSplit[1].substring(9));
            try{
//                Toast.makeText(form_pengembalian.this, "" + clickedBukuId, Toast.LENGTH_SHORT).show();
                database.setStatusPinjam(clickedPeminjamanId, true);
                database.setStatusBuku(clickedBukuId, false);
                Toast.makeText(form_pengembalian.this, "Buku berhasil dikembalikan", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }catch (Exception e){
                System.out.println(e);
            }
        });
    }
}