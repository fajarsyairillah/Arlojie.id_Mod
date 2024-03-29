package com.pasarkaget.fajar.pasarkaget;

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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pasarkaget.fajar.pasarkaget.Model.Cart;
import com.pasarkaget.fajar.pasarkaget.Model.Products;
import com.pasarkaget.fajar.pasarkaget.Prevalent.Prevalent;
import com.pasarkaget.fajar.pasarkaget.ViewHolder.CartViewHolder;

import java.text.NumberFormat;
import java.util.Locale;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;
    private double overTotalPrice = 0;
    private String totalPrice = "";
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price_btn);
        txtMsg1 = (TextView) findViewById(R.id.message1);

        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference();

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                txtTotalAmount.setText("Harga Total = Rp. " + String.valueOf(overTotalPrice) + "00");

                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(totalPrice));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        CheckOrderState();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone()).child("Products"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtProductQuantity.setText("Jumlah = " + model.getQuantity());
                holder.txtProductPrice.setText("Harga = Rp. " + model.getPrice());
                holder.txtProductName.setText(model.getPname());


                double oneTypeProductPrice = (((Double.parseDouble(model.getPrice()))) * Double.valueOf(model.getQuantity()));
                overTotalPrice = overTotalPrice + oneTypeProductPrice;
                totalPrice = overTotalPrice + "00";



                   holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Hapus"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Pilihan Data Keranjang");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                               if (i == 0)
                               {
                                   Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                   intent.putExtra("pid", model.getPid());
                                   startActivity(intent);
                               }
                               if (i == 1)
                               {
                                   cartListRef.child("User View")
                                           .child(Prevalent.currentOnlineUser.getPhone())
                                           .child("Products")
                                           .child(model.getPid())
                                           .removeValue()
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task)
                                               {
                                                   if (task.isSuccessful())
                                                   {
                                                       Toast.makeText(CartActivity.this, "Hapus Item Sukses", Toast.LENGTH_SHORT).show();
                                                       Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                       startActivity(intent);
                                                   }
                                               }
                                           });
                               }
                            }
                        });

                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
               if (dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if (shippingState.equals("Payments"))
                    {
                        txtTotalAmount.setText("Kepada " + userName + "\n Pesanan Anda Telah Di Proses.");
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Selamat Pesanan Anda Telah Sukses, Barang Akan Dikirim Ke Alamat Anda");
                        NextProcessBtn.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this, "Anda Dapat Memesan Produk Lain", Toast.LENGTH_SHORT).show();
                    }
                    else if(shippingState.equals("Not Payments"))
                    {
                        txtTotalAmount.setText("Status Pemesanan = Anda Sedang Melakukan Pemesanan");
                        recyclerView.setVisibility(View.GONE);

                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "Anda Dapat Memesan Produk Lain, Setelah Order Pertama Anda Telah Sampai", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
