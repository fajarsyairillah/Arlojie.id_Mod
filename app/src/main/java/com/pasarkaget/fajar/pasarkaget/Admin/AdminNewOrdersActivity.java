package com.pasarkaget.fajar.pasarkaget.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pasarkaget.fajar.pasarkaget.Model.AdminOrders;
import com.pasarkaget.fajar.pasarkaget.R;

public class AdminNewOrdersActivity extends AppCompatActivity
{
    private RecyclerView orderList;
    private DatabaseReference ordersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList = findViewById(R.id.orders_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef, AdminOrders.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
                    {
                        holder.userName.setText("Nama : " + model.getName());
                        holder.userPhoneNumber.setText("Nomor Telepon : " + model.getPhone());
                        holder.userTotalprice.setText("Total Harga = Rp. " + model.getTotalAmount());
                        holder.userDateTime.setText("Dipesan Pada: " + model.getDate() + " " + model.getTime());
                        holder.usershippingAddress.setText("Alamat: " + model.getAddress() + ", " + model.getCity());
                        holder.userStatePayments.setText("Status: "+ model.getState());
                        holder.userDelivery.setText("Jasa Pengiriman: "+ model.getDelivery());

                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.checkPayments.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserPaymentActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.formDelivery.setOnClickListener(new View.OnClickListener() {
                        @Override
                            public void onClick(View v)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrdersActivity.this, AdminDeliveryActivity.class);
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

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                        builder.setTitle("Sudah Mengirim Barang ini ?");

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
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_admin_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        orderList.setAdapter(adapter);
        adapter.startListening();
    }




    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalprice, userDateTime, usershippingAddress, userStatePayments, userDelivery;
        public Button showOrdersBtn, checkPayments, formDelivery;
        public AdminOrdersViewHolder(View itemView)

        {
            super(itemView);

            userDelivery = itemView.findViewById(R.id.orders_delivery);
            userName = itemView.findViewById(R.id.orders_user_name);
            userPhoneNumber = itemView.findViewById(R.id.orders_phone_number);
            userTotalprice = itemView.findViewById(R.id.orders_total_price);
            userDateTime = itemView.findViewById(R.id.orders_date_time);
            usershippingAddress = itemView.findViewById(R.id.orders_address_city);
            userStatePayments = itemView.findViewById(R.id.orders_state);
            showOrdersBtn = itemView.findViewById(R.id.show_all_products);
            checkPayments = itemView.findViewById(R.id.show_payment_status);
            formDelivery = itemView.findViewById(R.id.show_delivery);
        }
    }




    private void RemoverOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }
}
