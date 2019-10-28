package com.pasarkaget.fajar.pasarkaget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pasarkaget.fajar.pasarkaget.Admin.AdminUserProductsActivity;
import com.pasarkaget.fajar.pasarkaget.Model.UserOrders;
import com.pasarkaget.fajar.pasarkaget.Prevalent.Prevalent;

public class MyOrdersActivity extends AppCompatActivity {

    private RecyclerView myOrderList;
    private Query myOrdersRef;
    private Task<Void> myOrdersCompleteRef;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        final Button myConfirmPayments = findViewById(R.id.myConfirm_payments);

        database = FirebaseDatabase.getInstance().getReference();
        database.child("Orders")
                .child("State").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren())
                {


                    UserOrders order = userDataSnapshot.getValue(UserOrders.class);
                    String state = order.getState();

                    if (state.equals("Payments"))
                    {
                        myConfirmPayments.setVisibility(View.INVISIBLE);
                    }
                    else if (state.equals("Waiting Payments Confirm"))
                    {
                        myConfirmPayments.setVisibility(View.INVISIBLE);
                    }
                    else if (state.equals("Not Payments"))
                    {
                        myConfirmPayments.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("tag", "loadPost:onCancelled", databaseError.toException());
            }
        });

        myOrdersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .orderByKey().equalTo(Prevalent.currentOnlineUser.getPhone());

        myOrderList = findViewById(R.id.myOrders_list);
        myOrderList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<UserOrders> options =
                new FirebaseRecyclerOptions.Builder<UserOrders>()
                        .setQuery(myOrdersRef, UserOrders.class)
                        .build();

        FirebaseRecyclerAdapter<UserOrders,MyOrdersActivity.UserOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<UserOrders, MyOrdersActivity.UserOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyOrdersActivity.UserOrdersViewHolder holder, final int position, @NonNull final UserOrders model)
                    {
                        holder.MYuserName.setText("Nama : " + model.getName());
                        holder.MYuserPhoneNumber.setText("Nomor Telepon : " + model.getPhone());
                        holder.MYuserTotalprice.setText("Total Harga = Rp. " + model.getTotalAmount());
                        holder.MYuserDateTime.setText("Dipesan Pada: " + model.getDate() + " " + model.getTime());
                        holder.MYusershippingAddress.setText("Alamat: " + model.getAddress() + ", " + model.getCity());
                        holder.MYorderstate.setText("Status: "+ model.getState());
                        holder.MYuserdelivery.setText("Jasa Pengiriman: "+ model.getDelivery());

                        holder.MYconfirmPayments.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(MyOrdersActivity.this, ConfirmPaymentsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.MYshowOrderBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(MyOrdersActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.MYdelivery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(MyOrdersActivity.this, DeliveryUserActivity.class);
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
                                                "Tidak",
                                        };

                                AlertDialog.Builder builder = new AlertDialog.Builder(MyOrdersActivity.this);
                                builder.setTitle("Sudah Menerima Barang ini ?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                           salesReport();
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
                    public MyOrdersActivity.UserOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new MyOrdersActivity.UserOrdersViewHolder(view);
                    }
                };
        myOrderList.setAdapter(adapter);
        adapter.startListening();
    }

    private void salesReport()
    {
       myOrdersCompleteRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(Prevalent.currentOnlineUser.getPhone()).child("state").setValue("Complete");

        Intent intent = new Intent(MyOrdersActivity.this, HomeActivity.class);
        startActivity(intent);
        Toast.makeText(MyOrdersActivity.this, "Laporan Barang Di Terima Akan Dikirim Ke Admin", Toast.LENGTH_SHORT).show();
    }


    public static class UserOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView MYuserName, MYuserPhoneNumber, MYuserTotalprice, MYuserDateTime, MYusershippingAddress, MYorderstate, MYuserdelivery;
        public Button MYconfirmPayments, MYshowOrderBtn, MYdelivery;
        public UserOrdersViewHolder(View itemView)

        {
            super(itemView);

            MYuserdelivery = itemView.findViewById(R.id.myOrders_delivery);
            MYuserName = itemView.findViewById(R.id.myOrders_user_name);
            MYuserPhoneNumber = itemView.findViewById(R.id.myOrders_phone_number);
            MYuserTotalprice = itemView.findViewById(R.id.myOrders_total_price);
            MYuserDateTime = itemView.findViewById(R.id.myOrders_date_time);
            MYusershippingAddress = itemView.findViewById(R.id.myOrders_address_city);
            MYorderstate = itemView.findViewById(R.id.myOrders_state);
            MYconfirmPayments = itemView.findViewById(R.id.myConfirm_payments);
            MYshowOrderBtn= itemView.findViewById(R.id.myShow_all_products);
            MYdelivery = itemView.findViewById(R.id.myShow_delivery);
        }
    }

    private void RemoverOrder(String uID)
    {

    }
}
