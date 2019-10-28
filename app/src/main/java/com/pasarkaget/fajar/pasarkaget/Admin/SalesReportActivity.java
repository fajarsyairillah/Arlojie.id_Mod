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
import android.widget.Button;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pasarkaget.fajar.pasarkaget.Model.UserOrders;
import com.pasarkaget.fajar.pasarkaget.R;

public class SalesReportActivity extends AppCompatActivity {
    private RecyclerView salesList;
    private Query salesRef;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

        userID = getIntent().getStringExtra("uid");

        salesRef = FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("state").equalTo("Complete");

        salesList = findViewById(R.id.sales_report_list);
        salesList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<UserOrders> options =
                new FirebaseRecyclerOptions.Builder<UserOrders>()
                        .setQuery(salesRef, UserOrders.class)
                        .build();

        FirebaseRecyclerAdapter<UserOrders,SalesReportActivity.UserOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<UserOrders, SalesReportActivity.UserOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull SalesReportActivity.UserOrdersViewHolder holder, final int position, @NonNull final UserOrders model)
                    {
                        holder.salesUserName.setText("Nama : " + model.getName());
                        holder.salesUserPhoneNumber.setText("Nomor Telepon : " + model.getPhone());
                        holder.salesUserTotalprice.setText("Total Harga = Rp " + model.getTotalAmount());
                        holder.salesUserDateTime.setText("Dipesan Pada: " + model.getDate() + " " + model.getTime());
                        holder.salesUsershippingAddress.setText("Alamat: " + model.getAddress() + ", " + model.getCity());
                        holder.salesOrderstate.setText("Status: "+ model.getState());

                        holder.salesShowOrderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(SalesReportActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Ya",
                                                "Tidak"
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(SalesReportActivity.this);
                                builder.setTitle("Hapus Laporan Penjualan ini ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            String uID = getRef(position).getKey();


                                            RemoverOrder(uID);
                                        }
                                        else
                                        {
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
                    public SalesReportActivity.UserOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_report_layout, parent, false);
                        return new SalesReportActivity.UserOrdersViewHolder(view);
                    }
                };
        salesList.setAdapter(adapter);
        adapter.startListening();
    }


    public static class UserOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView salesUserName, salesUserPhoneNumber, salesUserTotalprice, salesUserDateTime, salesUsershippingAddress, salesOrderstate;
        public Button salesShowOrderBtn;
        public UserOrdersViewHolder(View itemView)

        {
            super(itemView);


            salesUserName = itemView.findViewById(R.id.sales_user_name);
            salesUserPhoneNumber = itemView.findViewById(R.id.sales_phone_number);
            salesUserTotalprice = itemView.findViewById(R.id.sales_total_price);
            salesUserDateTime = itemView.findViewById(R.id.sales_date_time);
            salesUsershippingAddress = itemView.findViewById(R.id.sales_address_city);
            salesOrderstate = itemView.findViewById(R.id.sales_state);
            salesShowOrderBtn= itemView.findViewById(R.id.sales_all_products);
        }
    }

    private void RemoverOrder(String uID)
    {

    }
}

