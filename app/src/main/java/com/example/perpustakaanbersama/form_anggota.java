package com.example.perpustakaanbersama;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class form_anggota extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_anggota);

        Button btn_daftar = findViewById(R.id.button_submitDaftar);
        EditText et_namaDepan = findViewById(R.id.input_namaDepan);
        EditText et_namaBelakang = findViewById(R.id.input_namaBelakang);
        EditText et_kota = findViewById(R.id.input_kota);
        RadioButton radio_jenisKelaminPria = findViewById(R.id.radio_pria);
        EditText et_noTelepon = findViewById(R.id.input_telp);

        btn_daftar.setOnClickListener((View view) -> {
            try {
                String gender = radio_jenisKelaminPria.isChecked() == true ? "pria" : "wanita";
                AnggotaModel anggota = new AnggotaModel(-1, et_namaDepan.getText().toString(),
                        et_namaBelakang.getText().toString(), et_kota.getText().toString(),
                        gender, et_noTelepon.getText().toString());
                DatabaseHelper database = new DatabaseHelper(form_anggota.this);
                if(database.addAnggota(anggota)){
                    Toast.makeText(form_anggota.this, "Anda sudah terdaftar !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(form_anggota.this, main_menu.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(form_anggota.this, "Maaf, anda gagal terdaftar !", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e ){
                Toast.makeText(form_anggota.this, "Data tidak lengkap !", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}