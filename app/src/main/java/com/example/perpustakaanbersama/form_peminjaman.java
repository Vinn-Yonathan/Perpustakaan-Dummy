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
import java.util.Scanner;

public class form_peminjaman extends AppCompatActivity {
    ListView lv_anggota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_peminjaman);

        EditText et_search = findViewById(R.id.et_cariNama);
        Button btn_search = findViewById(R.id.button_cariNama);

        btn_search.setOnClickListener((View view)->{
            searchAnggota(et_search.getText().toString());
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void searchAnggota(String namaBelakang){
        lv_anggota = findViewById(R.id.lv_nama);
        DatabaseHelper database = new DatabaseHelper(form_peminjaman.this);
        List<AnggotaModel> anggotaList = database.getAnggota(namaBelakang);
//        System.out.println(anggotaList);
        ArrayAdapter anggotaAdapter = new ArrayAdapter<AnggotaModel>(
                form_peminjaman.this, android.R.layout.simple_list_item_1, anggotaList
        );
        lv_anggota.setAdapter(anggotaAdapter);

        lv_anggota.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id ) -> {
            Intent intent = new Intent(form_peminjaman.this, form_peminjaman2.class);
            String[] anggotaDataSplit = parent.getItemAtPosition(position).toString().split(System.lineSeparator());
            String clickedNamaBelakangId = anggotaDataSplit[0].substring(4);
//            Toast.makeText(form_peminjaman.this, clickedNamaBelakangId, Toast.LENGTH_SHORT).show();
            intent.putExtra("clickedNamaBelakangId", clickedNamaBelakangId);
            startActivity(intent);
        });
    }
}