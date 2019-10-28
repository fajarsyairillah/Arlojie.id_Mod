package com.pasarkaget.fajar.pasarkaget;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pasarkaget.fajar.pasarkaget.Prevalent.Prevalent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;
import me.abhinay.input.CurrencyEditText;

public class ConfirmPaymentsActivity extends AppCompatActivity
{
    private EditText YourBank, NameAccountBank, NumberBank;
    private CurrencyEditText Nominal;
    private Button confirmPay;
    private static final String[] ITEMS = {"BCA", "Mandiri", "BNI"};
    private static final String[] ITEMS2 = {"Transfer ATM", "M-Banking","Transfer Bank"};
    private ArrayAdapter<String> adapterBank, adapterMetods;
    private MaterialSpinner spinnerBank, spinnerMetods;
    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payments);

        userID = getIntent().getStringExtra("uid");

        adapterBank = new ArrayAdapter<>(this, R.layout.spinner_item2, ITEMS);
        adapterBank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMetods = new ArrayAdapter<>(this, R.layout.spinner_item, ITEMS2);
        adapterMetods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confirmPay = (Button) findViewById(R.id.confirm_payment_btn);
        spinnerBank = (MaterialSpinner) findViewById(R.id.spinnerBank);
        YourBank = (EditText)  findViewById(R.id.your_bank);
        NumberBank = (EditText) findViewById(R.id.your_number_bank);
        NameAccountBank = (EditText)  findViewById(R.id.name_account_bank);
        Nominal = (CurrencyEditText)  findViewById(R.id.transfer_nominal);
        spinnerMetods = (MaterialSpinner)  findViewById(R.id.spinnerMetods);

        Nominal.setDelimiter(false);
        Nominal.setSpacing(false);
        Nominal.setDecimals(false);
        Nominal.setSeparator(".");

        confirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

        initSpinnerMultiline();
        initSpinnerMultiline1();
    }

    private void initSpinnerMultiline1()
    {
        spinnerMetods = findViewById(R.id.spinnerMetods);
        spinnerMetods.setAdapter(adapterMetods);
        spinnerMetods.setHint("Metode Transfer");
    }

    private void initSpinnerMultiline()
    {
        spinnerBank = findViewById(R.id.spinnerBank);
        spinnerBank.setAdapter(adapterBank);
        spinnerBank.setHint("Bank Tujuan");
}

    private void Check()
    {
        if (TextUtils.isEmpty(spinnerBank.getSelectedItem().toString()))
        {
            Toast.makeText(this, "Lengkapi Tujuan Bank Anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(YourBank.getText().toString()))
        {
            Toast.makeText(this, "Lengkapi Bank Anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(NumberBank.getText().toString()))
        {
            Toast.makeText(this, "Lengkapi Nomor Rekening Bank Anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(NameAccountBank.getText().toString()))
        {
            Toast.makeText(this, "Lengkapi Atas Nama Bank Anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Nominal.getText().toString()))
        {
            Toast.makeText(this, "Lengkapi Nominal Transfer Anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(spinnerMetods.getSelectedItem().toString()))
        {
            Toast.makeText(this, "Lengkapi Metode Transfer Anda...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmPaymentsOrder();
        }
    }

    private void ConfirmPaymentsOrder()
    {
        final String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference paymentsRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Payments").child(userID);


        final HashMap<String, Object> paymentsMap = new HashMap<>();
        paymentsMap.put("uid", userID);
        paymentsMap.put("bank", spinnerBank.getSelectedItem().toString());
        paymentsMap.put("buyerBank",YourBank.getText().toString());
        paymentsMap.put("numberBuyerBank",NumberBank.getText().toString());
        paymentsMap.put("buyerAccount", NameAccountBank.getText().toString());
        paymentsMap.put("nominal", Nominal.getText().toString());
        paymentsMap.put("metods", spinnerMetods.getSelectedItem().toString());
        paymentsMap.put("date", saveCurrentDate);
        paymentsMap.put("time", saveCurrentTime);
        paymentsMap.put("state", "Waiting Payments Confirm");

        paymentsRef.updateChildren(paymentsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone()).child("state").setValue("Waiting Payments Confirm")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmPaymentsActivity.this, "Anda Telah Sukses Konfirmasi Pembayaran", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmPaymentsActivity.this, HomeActivity.class);
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
