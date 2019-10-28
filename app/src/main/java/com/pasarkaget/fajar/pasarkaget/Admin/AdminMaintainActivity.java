package com.pasarkaget.fajar.pasarkaget.Admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pasarkaget.fajar.pasarkaget.HomeActivity;
import com.pasarkaget.fajar.pasarkaget.R;

public class AdminMaintainActivity extends AppCompatActivity
{

    private Button maintainProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain);



        maintainProducts = (Button) findViewById(R.id.maintain_products_btn);

        maintainProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminMaintainActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
                finish();
            }
        });
    }
}
