package com.pasarkaget.fajar.pasarkaget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pasarkaget.fajar.pasarkaget.Model.Cart;
import com.pasarkaget.fajar.pasarkaget.ViewHolder.CartViewHolder;

public class UserProductsActivity extends AppCompatActivity
{
    private RecyclerView myProductsList;
    RecyclerView.LayoutManager myLayoutManager;
    private DatabaseReference myCartListRef;

    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_products);

        userID = getIntent().getStringExtra("uid");


        myProductsList = findViewById(R.id.products_list);
        myProductsList.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myProductsList.setLayoutManager(myLayoutManager);

        myCartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userID).child("Products");
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(myCartListRef, Cart.class)
                .build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {
                holder.txtProductQuantity.setText("Jumlah = " + model.getQuantity());
                holder.txtProductPrice.setText("Harga = Rp " + model.getPrice());
                holder.txtProductName.setText(model.getPname());
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

        myProductsList.setAdapter(adapter);
        adapter.startListening();
    }
}
