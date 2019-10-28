package com.pasarkaget.fajar.pasarkaget.Admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.pasarkaget.fajar.pasarkaget.R;

public class AdminCategoryActivity extends AppCompatActivity
{
    private ImageView AnalogWatch, DigitalWatch, ChainWatch;
    private ImageView FemaleWatch, SmartlWatch, DigitalAnalogWatch;
    private ImageView CoupleWatch, AlarmlWatch, WeckerWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        AnalogWatch = (ImageView) findViewById(R.id.analog_watch);
        DigitalWatch = (ImageView) findViewById(R.id.digital_watch);
        ChainWatch = (ImageView) findViewById(R.id.chain_watch);
        FemaleWatch = (ImageView) findViewById(R.id.female_watch);
        SmartlWatch = (ImageView) findViewById(R.id.smart_watch);
        DigitalAnalogWatch = (ImageView) findViewById(R.id.analog_digital_watch);
        CoupleWatch = (ImageView) findViewById(R.id.couple_watch);
        AlarmlWatch = (ImageView) findViewById(R.id.alarm_watch);
        WeckerWatch = (ImageView) findViewById(R.id.wecker_watch);

        AnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Analog");
                startActivity(intent);
            }
        });

        DigitalWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Digital");
                startActivity(intent);
            }
        });

        ChainWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Rantai");
                startActivity(intent);
            }
        });

        FemaleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Wanita");
                startActivity(intent);
            }
        });

        SmartlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Pintar");
                startActivity(intent);
            }
        });

        DigitalAnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Digital Analog");
                startActivity(intent);
            }
        });

        CoupleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Couple");
                startActivity(intent);
            }
        });

        AlarmlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Alarm");
                startActivity(intent);
            }
        });

        WeckerWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Jam Wecker");
                startActivity(intent);
            }
        });
    }
}
