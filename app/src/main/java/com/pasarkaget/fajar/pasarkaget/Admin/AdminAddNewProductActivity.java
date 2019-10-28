package com.pasarkaget.fajar.pasarkaget.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Currency;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pasarkaget.fajar.pasarkaget.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import me.abhinay.input.CurrencyEditText;

public class AdminAddNewProductActivity extends AppCompatActivity
{
    private String CategoryName, Description, Price, Pname, Stock, saveCurrentDate, saveCurrentTime;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductStock;
    private CurrencyEditText InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadimageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);


        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        AddNewProductButton = (Button) findViewById(R.id.add_new_product);
        InputProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (CurrencyEditText) findViewById(R.id.product_price);
        InputProductStock = (EditText) findViewById(R.id.product_stock);
        loadingBar = new ProgressDialog(this);

        InputProductPrice.setDelimiter(false);
        InputProductPrice.setSpacing(false);
        InputProductPrice.setDecimals(false);
        InputProductPrice.setSeparator(".");

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               OpenGallery();
            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateProductData();
            }
        });
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData()
    {
       Description = InputProductDescription.getText().toString();
       Price = InputProductPrice.getText().toString();
       Pname = InputProductName.getText().toString();
       Stock = InputProductStock.getText().toString();

       if (ImageUri == null)
       {
           Toast.makeText(this,"Wajib Masukkan Gambar Produk",Toast.LENGTH_SHORT).show();
       }
       else if (TextUtils.isEmpty(Description))
       {
           Toast.makeText(this,"Tolong Masukkan Deskripsi Produk",Toast.LENGTH_SHORT).show();
       }
       else if (TextUtils.isEmpty(Price))
       {
           Toast.makeText(this,"Tolong Masukkan Harga Produk",Toast.LENGTH_SHORT).show();
       }
       else if (TextUtils.isEmpty(Pname))
       {
           Toast.makeText(this,"Tolong Masukkan Nama Produk",Toast.LENGTH_SHORT).show();
       }
       else if (TextUtils.isEmpty(Stock))
       {
           Toast.makeText(this,"Tolong Masukkan Jumlah Stok Produk",Toast.LENGTH_SHORT).show();
       }
       else
           {
               StoreProductInformation();

           }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Tambah Produk Baru");
        loadingBar.setMessage("Tunggu Sebentar, Kami Sedang Menambah Produk Baru");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currenttime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
            String message = e.toString();
            Toast.makeText(AdminAddNewProductActivity.this, "Error: "+ message,Toast.LENGTH_SHORT).show();
            loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewProductActivity.this,"Upload Gambar Sukses", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                      if (!task.isSuccessful())
                      {
                          throw task.getException();
                      }

                      downloadimageUrl = filePath.getDownloadUrl().toString();
                      return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadimageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this, "Gambar Produk Telah Sukses Disimpan Di Database",Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }


    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadimageUrl);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);
        productMap.put("stock", Stock);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewProductActivity.this, BerandaAdmin.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Produk Sukses Ditambahkan...", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
