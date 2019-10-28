package com.pasarkaget.fajar.pasarkaget.Admin;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pasarkaget.fajar.pasarkaget.Prevalent.Prevalent;
import com.pasarkaget.fajar.pasarkaget.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import fr.ganfra.materialspinner.MaterialSpinner;
import me.abhinay.input.CurrencyEditText;

public class AdminDeliveryActivity extends AppCompatActivity {
    private EditText no_resi;
    private CurrencyEditText hargaOngkir;
    private Button confirmDelivery;
    private static final String[] ITEMS = {"J&T Express"};
    private ArrayAdapter<String> adapterDelivery;
    private MaterialSpinner spinnerDelivery;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery);

        userID = getIntent().getStringExtra("uid");

        adapterDelivery = new ArrayAdapter<>(this, R.layout.spinner_item2, ITEMS);
        adapterDelivery.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDelivery = (MaterialSpinner) findViewById(R.id.spinner_admin_delivery);
        no_resi = (EditText)findViewById(R.id.name_delivery);
        confirmDelivery = (Button) findViewById(R.id.confirm_admin_delivry_btn) ;
        hargaOngkir = (CurrencyEditText) findViewById(R.id.ongkir) ;

        hargaOngkir.setDelimiter(false);
        hargaOngkir.setSpacing(false);
        hargaOngkir.setDecimals(false);
        hargaOngkir.setSeparator(".");
        confirmDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

        initSpinnerMultiline();
    }


    private void initSpinnerMultiline()
    {
        spinnerDelivery = findViewById(R.id.spinner_admin_delivery);
        spinnerDelivery.setAdapter(adapterDelivery);
        spinnerDelivery.setHint("Jasa Pengiriman");
    }

    private void Check()
    {
        if (TextUtils.isEmpty(spinnerDelivery.getSelectedItem().toString()))
        {
            Toast.makeText(this, "Lengkapi Jasa Pengiriman...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(no_resi.getText().toString()))
        {
            Toast.makeText(this, "Lengkapi No Resi...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hargaOngkir.getText().toString()))
        {
            Toast.makeText(this, "Lengkapi Harga Ongkir...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmDelivery();
        }
    }

    private void ConfirmDelivery()
    {
        final String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference deliverysRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List");

        final HashMap<String, Object> deliverysMap = new HashMap<>();
        deliverysMap.put("uid", userID);
        deliverysMap.put("delivery", spinnerDelivery.getSelectedItem().toString());
        deliverysMap.put("resi",no_resi.getText().toString());
        deliverysMap.put("ongkir",hargaOngkir.getText().toString());
        deliverysMap.put("date", saveCurrentDate);
        deliverysMap.put("time", saveCurrentTime);

        deliverysRef.updateChildren(deliverysMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    deliverysRef.child("Admin View").child(userID)
                            .child("Delivery").child(userID)
                            .updateChildren(deliverysMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("Orders").child(userID).child("state").setValue("Shipping");

                                        Toast.makeText(AdminDeliveryActivity.this, "Anda Telah Sukses Konfirmasi Pengiriman", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(AdminDeliveryActivity.this,BerandaAdmin.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
