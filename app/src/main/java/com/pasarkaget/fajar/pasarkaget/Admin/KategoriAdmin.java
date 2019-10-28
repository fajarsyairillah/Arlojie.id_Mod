package com.pasarkaget.fajar.pasarkaget.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pasarkaget.fajar.pasarkaget.R;

public class KategoriAdmin extends AppCompatActivity {
    private ImageView AnalogWatch, DigitalWatch, ChainWatch;
    private ImageView FemaleWatch, SmartlWatch, DigitalAnalogWatch;
    private ImageView CoupleWatch, AlarmlWatch, WeckerWatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_admin);

        AnalogWatch = (ImageView) findViewById(R.id.jam_analog_admin);
        DigitalWatch = (ImageView) findViewById(R.id.jam_digital_admin);
        ChainWatch = (ImageView) findViewById(R.id.jam_rantai_admin);
        FemaleWatch = (ImageView) findViewById(R.id.jam_wanita_admin);
        SmartlWatch = (ImageView) findViewById(R.id.jam_pintar_admin);
        DigitalAnalogWatch = (ImageView) findViewById(R.id.jam_digital_analog_admin);
        CoupleWatch = (ImageView) findViewById(R.id.jam_couple_admin);
        AlarmlWatch = (ImageView) findViewById(R.id.jam_alarm_admin);
        WeckerWatch = (ImageView) findViewById(R.id.jam_wecker_admin);

        AnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Analog");
                startActivity(intent);
            }
        });

        DigitalWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Digital");
                startActivity(intent);
            }
        });

        ChainWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Rantai");
                startActivity(intent);
            }
        });

        FemaleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Wanita");
                startActivity(intent);
            }
        });

        SmartlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Pintar");
                startActivity(intent);
            }
        });

        DigitalAnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Digital Analog");
                startActivity(intent);
            }
        });

        CoupleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Couple");
                startActivity(intent);
            }
        });

        AlarmlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Alarm");
                startActivity(intent);
            }
        });

        WeckerWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(KategoriAdmin.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Wecker");
                startActivity(intent);
            }
        });
    }
}
