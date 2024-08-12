package com.example.perpustakaanbersama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class main_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button buttonDonasi = findViewById(R.id.button_donasi);
        buttonDonasi.setOnClickListener( (View view) -> {
            Intent intent = new Intent(main_menu.this, form_donasi.class);
            startActivity(intent);
        });

        Button buttonPeminjaman = findViewById(R.id.button_peminjaman);
        buttonPeminjaman.setOnClickListener( (View view) -> {
            Intent intent = new Intent(main_menu.this, form_peminjaman.class);
            startActivity(intent);
        });

        Button buttonDaftar = findViewById(R.id.button_daftar);
        buttonDaftar.setOnClickListener( (View view) -> {
            Intent intent = new Intent(main_menu.this, form_anggota.class);
            startActivity(intent);
        });

        Button buttonPengembalian = findViewById(R.id.button_pengembalian);
        buttonPengembalian.setOnClickListener( (View view) -> {
            Intent intent = new Intent(main_menu.this, form_pengembalian.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}