package com.example.perpustakaanbersama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class form_donasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_donasi);

        Button btn_donasi = findViewById(R.id.button_submitDonasi);
        EditText et_judul =  findViewById(R.id.input_judulBuku);
        EditText et_penerbit = findViewById(R.id.input_penerbit);
        EditText et_penulis = findViewById(R.id.input_penulis);
        EditText et_isbn = findViewById(R.id.input_isbn);
        EditText et_genre = findViewById(R.id.input_genre);

        btn_donasi.setOnClickListener((View view)->{
            try{
                BukuModel buku = new BukuModel(-1, et_judul.getText().toString(),
                        et_penerbit.getText().toString(), et_penulis.getText().toString(),
                        et_isbn.getText().toString(), et_genre.getText().toString(),
                        false);
                DatabaseHelper database = new DatabaseHelper(form_donasi.this);
                if(database.addBuku(buku)){
                    Toast.makeText(form_donasi.this, "Terima kasih, buku berhasil didonasikan.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(form_donasi.this, main_menu.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(form_donasi.this, "Maaf, buku belum berhasil didonasikan.", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(form_donasi.this, "Data tidak lengkap !", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}