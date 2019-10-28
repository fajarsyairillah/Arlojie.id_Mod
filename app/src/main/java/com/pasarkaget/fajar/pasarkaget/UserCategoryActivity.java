package com.pasarkaget.fajar.pasarkaget;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryAlarmActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryAnalogActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryCoupleActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryDigitalActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryDigitalAnalogActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryPintarActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryRantaiActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryWanitaActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryWeckerActivity;

public class UserCategoryActivity extends AppCompatActivity
{
    private ImageView AnalogWatch, DigitalWatch, ChainWatch;
    private ImageView FemaleWatch, SmartlWatch, DigitalAnalogWatch;
    private ImageView CoupleWatch, AlarmlWatch, WeckerWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

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
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryAnalogActivity.class);
                intent.putExtra("category","Jam Analog");
                startActivity(intent);
            }
        });

        DigitalWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryDigitalActivity.class);
                intent.putExtra("category","Jam Digital");
                startActivity(intent);
            }
        });

        ChainWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryRantaiActivity.class);
                intent.putExtra("category","Jam Rantai");
                startActivity(intent);
            }
        });

        FemaleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryWanitaActivity.class);
                intent.putExtra("category","Jam Wanita");
                startActivity(intent);
            }
        });

        SmartlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryPintarActivity.class);
                intent.putExtra("category","Jam Pintar");
                startActivity(intent);
            }
        });

        DigitalAnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryDigitalAnalogActivity.class);
                intent.putExtra("category","Jam Digital Analog");
                startActivity(intent);
            }
        });

        CoupleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryCoupleActivity.class);
                intent.putExtra("category","Jam Couple");
                startActivity(intent);
            }
        });

        AlarmlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryAlarmActivity.class);
                intent.putExtra("category","Jam Alarm");
                startActivity(intent);
            }
        });

        WeckerWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(UserCategoryActivity.this, MainCategoryWeckerActivity.class);
                intent.putExtra("category","Jam Wecker");
                startActivity(intent);
            }
        });
    }
}
