package com.pasarkaget.fajar.pasarkaget.Admin;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.pasarkaget.fajar.pasarkaget.R;
import com.rey.material.widget.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import me.abhinay.input.CurrencyEditText;

public class AdminMaintainProductsActivity extends AppCompatActivity
{
   private Button applyChangeBtn;
   private FloatingActionButton deleteBtn;
   private EditText name, description, stock;
   private CurrencyEditText price;
   private ImageView imageView;
   private String productID = "";
   private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);

        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        applyChangeBtn = findViewById(R.id.all_changes_btn);
        name = findViewById(R.id.product_name_maintain);
        price = findViewById(R.id.product_price_maintain);
        description = findViewById(R.id.product_description_maintain);
        stock = findViewById(R.id.product_stock_maintain);
        imageView = findViewById(R.id.product_image_maintain);
        deleteBtn = findViewById(R.id.delete_product_btn);

        price.setDelimiter(false);
        price.setSpacing(false);
        price.setDecimals(false);
        price.setSeparator(".");

        displaySpecificProductInfo();

        applyChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChangeBtn();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
             deleteThisProduct();
            }
        });
    }

    private void deleteThisProduct()
    {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Intent intent = new Intent(AdminMaintainProductsActivity.this, BerandaAdmin.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainProductsActivity.this, "Produk Telah Terhapus...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyChangeBtn()
    {
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();
        String pStock = stock.getText().toString();

        if (pName.equals(""))
        {
            Toast.makeText(this,"Tuliskan Nama Produk...",Toast.LENGTH_SHORT).show();
        }
        else  if (pPrice.equals(""))
        {
            Toast.makeText(this,"Tuliskan Harga Produk...",Toast.LENGTH_SHORT).show();
        }
        else  if (pDescription.equals(""))
        {
            Toast.makeText(this,"Tuliskan Deskripsi Produk...",Toast.LENGTH_SHORT).show();
        }
        else  if (pDescription.equals(""))
        {
            Toast.makeText(this,"Tuliskan Stok Produk...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pDescription);
            productMap.put("price", pPrice);
            productMap.put("pname", pName);
            productMap.put("stock", pStock);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                  if (task.isSuccessful())
                  {
                      Toast.makeText(AdminMaintainProductsActivity.this, "Perubahan Data Sukses...", Toast.LENGTH_SHORT).show();

                      Intent intent = new Intent(AdminMaintainProductsActivity.this, BerandaAdmin.class);
                      startActivity(intent);
                      finish();
                  }
                }
            });
        }
    }

    private void displaySpecificProductInfo()
    {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String pName = dataSnapshot.child("pname").getValue().toString();
                    String pPrice = dataSnapshot.child("price").getValue().toString();
                    String pDescription = dataSnapshot.child("description").getValue().toString();
                    String pStock = dataSnapshot.child("stock").getValue().toString();
                    String pImage = dataSnapshot.child("image").getValue().toString();


                    name.setText(pName);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    stock.setText(pStock);
                    Picasso.get().load(pImage).into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }
}
