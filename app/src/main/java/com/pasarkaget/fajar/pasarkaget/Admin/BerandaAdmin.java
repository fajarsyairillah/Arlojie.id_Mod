package com.pasarkaget.fajar.pasarkaget.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pasarkaget.fajar.pasarkaget.LoginActivity;
import com.pasarkaget.fajar.pasarkaget.MainActivity;
import com.pasarkaget.fajar.pasarkaget.R;

public class BerandaAdmin extends AppCompatActivity {
 Button btn_tambah, btn_kelola, btn_laporan, btn_pesanan, btn_keluar, btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_admin);

        btn_tambah = (Button)findViewById(R.id.tambah_barang);
        btn_kelola = (Button)findViewById(R.id.kelola_barang);
        btn_laporan =(Button)findViewById(R.id.laporan_penjualan);
        btn_pesanan =(Button)findViewById(R.id.pesanan_barang);
        btn_keluar =(Button)findViewById(R.id.keluar_app);
        btn_logout=(Button)findViewById(R.id.logout_app);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaAdmin.this, "Tambah Barang", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BerandaAdmin.this, KategoriAdmin.class);
                startActivity(intent);
            }
        });

        btn_kelola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaAdmin.this, "Kelola Barang", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BerandaAdmin.this, AdminMaintainActivity.class);
                startActivity(intent);
            }
        });

        btn_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaAdmin.this, "Laporan Penjualan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BerandaAdmin.this, SalesReportActivity.class);
                startActivity(intent);
            }
        });

        btn_pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaAdmin.this, "Pesanan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BerandaAdmin.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaAdmin.this, "Keluar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BerandaAdmin.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BerandaAdmin.this, "Logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BerandaAdmin.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
