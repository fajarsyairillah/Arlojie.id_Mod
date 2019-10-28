package com.pasarkaget.fajar.pasarkaget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryAlarmActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryAnalogActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryCoupleActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryDigitalActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryDigitalAnalogActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryPintarActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryRantaiActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryWanitaActivity;
import com.pasarkaget.fajar.pasarkaget.Category.MainCategoryWeckerActivity;

public class KategoriUser extends AppCompatActivity {
    private ImageView AnalogWatch, DigitalWatch, ChainWatch;
    private ImageView FemaleWatch, SmartlWatch, DigitalAnalogWatch;
    private ImageView CoupleWatch, AlarmlWatch, WeckerWatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori_user);


        AnalogWatch = (ImageView) findViewById(R.id.jam_analog);
        DigitalWatch = (ImageView) findViewById(R.id.jam_digital);
        ChainWatch = (ImageView) findViewById(R.id.jam_rantai);
        FemaleWatch = (ImageView) findViewById(R.id.jam_wanita);
        SmartlWatch = (ImageView) findViewById(R.id.jam_pintar);
        DigitalAnalogWatch = (ImageView) findViewById(R.id.jam_digital_analog);
        CoupleWatch = (ImageView) findViewById(R.id.jam_couple);
        AlarmlWatch = (ImageView) findViewById(R.id.jam_alarm);
        WeckerWatch = (ImageView) findViewById(R.id.jam_wecker);

        AnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Analog", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryAnalogActivity.class);
                intent.putExtra("category","Jam Analog");
                startActivity(intent);
            }
        });

        DigitalWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Digital", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryDigitalActivity.class);
                intent.putExtra("category","Jam Digital");
                startActivity(intent);
            }
        });

        ChainWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Rantai", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryRantaiActivity.class);
                intent.putExtra("category","Jam Rantai");
                startActivity(intent);
            }
        });

        FemaleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Wanita", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryWanitaActivity.class);
                intent.putExtra("category","Jam Wanita");
                startActivity(intent);
            }
        });

        SmartlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Pintar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryPintarActivity.class);
                intent.putExtra("category","Jam Pintar");
                startActivity(intent);
            }
        });

        DigitalAnalogWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Digital Analog", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryDigitalAnalogActivity.class);
                intent.putExtra("category","Jam Digital Analog");
                startActivity(intent);
            }
        });

        CoupleWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Couple", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryCoupleActivity.class);
                intent.putExtra("category","Jam Couple");
                startActivity(intent);
            }
        });

        AlarmlWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Alarm", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryAlarmActivity.class);
                intent.putExtra("category","Jam Alarm");
                startActivity(intent);
            }
        });

        WeckerWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(KategoriUser.this, "Jam Wecker", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(KategoriUser.this, MainCategoryWeckerActivity.class);
                intent.putExtra("category","Jam Wecker");
                startActivity(intent);
            }
        });
    }
}
