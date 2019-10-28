package com.pasarkaget.fajar.pasarkaget.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pasarkaget.fajar.pasarkaget.Model.Payments;
import com.pasarkaget.fajar.pasarkaget.R;
import com.pasarkaget.fajar.pasarkaget.ViewHolder.PaymentsViewHolder;


public class AdminUserPaymentActivity extends AppCompatActivity {
    private RecyclerView paymentsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference paymentsRef;
    private Task<Void> confirmsPaymentsRef;
    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_payments);

        userID = getIntent().getStringExtra("uid");

        paymentsList = findViewById(R.id.payments_list);
        paymentsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        paymentsList.setLayoutManager(layoutManager);


        paymentsRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userID).child("Payments");


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Payments> options =
                new FirebaseRecyclerOptions.Builder<Payments>()
                        .setQuery(paymentsRef, Payments.class)
                        .build();


        FirebaseRecyclerAdapter<Payments, PaymentsViewHolder> adapter = new FirebaseRecyclerAdapter<Payments, PaymentsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PaymentsViewHolder holder, final int position, @NonNull Payments model) {
                holder.txtMyBankName.setText(" Bank Pengirim : " + model.getBuyerBank());
                holder.txtNumberBank.setText(" Nomor Rekening : "+model.getNumberBuyerBank());
                holder.txtBankAccountName.setText(" A/n : " + model.getBuyerAccount());
                holder.txtBankName.setText(" Bank Penerima : " + model.getBank());
                holder.txtMetods.setText(" Dengan Metode : " + model.getMetods());
                holder.txtTotalTransfer.setText("Rp. " + model.getNominal());
                holder.txtDate.setText("Dibayar Pada Tanggal : " + model.getDate());

                holder.confirmPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmsPaymentsRef = FirebaseDatabase.getInstance().getReference()
                                .child("Orders").child(userID).child("state").setValue("Payments");

                        Intent intent = new Intent(AdminUserPaymentActivity.this, BerandaAdmin.class);
                        startActivity(intent);
                        Toast.makeText(AdminUserPaymentActivity.this, "Konfirmasi Status Pembayaran Pelanggan Sukses", Toast.LENGTH_SHORT).show();
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Ya",
                                        "Tidak"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminUserPaymentActivity.this);
                        builder.setTitle("Hapus Data Pembayaran ini ?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    String uID = getRef(position).getKey();


                                    RemoverOrder(uID);
                                } else {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public PaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_items_layout, parent, false);
                PaymentsViewHolder holder = new PaymentsViewHolder(view);
                return holder;
            }
        };

        paymentsList.setAdapter(adapter);
        adapter.startListening();

    }

    private void RemoverOrder(String uID)
    {
        paymentsRef.child(uID).removeValue();
    }
}
