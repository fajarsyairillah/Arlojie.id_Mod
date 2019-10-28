package com.pasarkaget.fajar.pasarkaget;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pasarkaget.fajar.pasarkaget.Model.Delivery;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryUserActivity extends AppCompatActivity {
    private RecyclerView deliveryList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference deliveryRef;
    private Task<Void> confirmsDeliveryRef;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_user);

        userID = getIntent().getStringExtra("uid");

        deliveryList = findViewById(R.id.user_delivery_list);
        deliveryList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        deliveryList.setLayoutManager(layoutManager);

        deliveryRef =  FirebaseDatabase.getInstance().getReference()
                .child("Cart List")
                .child("Admin View")
                .child(userID)
                .child("Delivery");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Delivery> options =
                new FirebaseRecyclerOptions.Builder<Delivery>()
                        .setQuery(deliveryRef, Delivery.class)
                        .build();


        FirebaseRecyclerAdapter<Delivery, DeliveryViewHolder> adapter = new FirebaseRecyclerAdapter<Delivery, DeliveryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DeliveryViewHolder holder, final int position, @NonNull Delivery model) {
                holder.txtDelivery.setText(" Pengiriman Melalui : " + model.getDelivery());
                holder.noResi.setText(model.getResi());
                holder.txtDate.setText(" Dikirim Pada Tanggal : " + model.getDate());
                holder.txtOngkir.setText(" Ongkos Kirim : Rp. "+ model.getOngkir());

                holder.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getApplicationContext(),holder.noResi);
                        copyButtonLibrary.init();
                    }
                });
                holder.jnt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent track = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jet.co.id/track"));
                        startActivity(track);
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

                        AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryUserActivity.this);
                        builder.setTitle("Hapus Data Pengiriman ini ?");

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
            public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_delivery_items_layout, parent, false);
                DeliveryViewHolder holder = new DeliveryViewHolder(view);
                return holder;
            }
        };

        deliveryList.setAdapter(adapter);
        adapter.startListening();

    }

    private void RemoverOrder(String uID) {
        deliveryRef.child(uID).removeValue();
    }

    public class DeliveryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtDate, txtDelivery, noResi, txtOngkir;
        public Button jnt, copy;

        public DeliveryViewHolder(View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.user_delivery_date);
            txtDelivery = itemView.findViewById(R.id.user_delivery_choose);
            noResi = itemView.findViewById(R.id.user_delivery_resi);
            jnt = itemView.findViewById(R.id.JNE);
            copy = itemView.findViewById(R.id.copy);
            txtOngkir = itemView.findViewById(R.id.user_delivery_price);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
