package com.pasarkaget.fajar.pasarkaget;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.pasarkaget.fajar.pasarkaget.Prevalent.Prevalent;


public class AboutUsActivity extends AppCompatActivity {
    private TextView about, rek, rekBank1, rekBank2, rekBank3,rek2,rek3,rek4;
    private ImageView logo1, logo2, logo3;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        about = findViewById(R.id.aboutUs);
        rek = findViewById(R.id.bankAccount);
        rek2 = findViewById(R.id.bankAccount2);
        rek3 = findViewById(R.id.bankAccount3);
        rek4 = findViewById(R.id.bankAccount4);
        rekBank1 = findViewById(R.id.bank1);
        rekBank2 = findViewById(R.id.bank2);
        rekBank3 = findViewById(R.id.bank3);
        logo1 = findViewById(R.id.logo_Bank1);
        logo2 = findViewById(R.id.logo_Bank2);
        logo3 = findViewById(R.id.logo_Bank3);
        materialDesignFAM = findViewById(R.id.fabMenu);
        floatingActionButton1 = findViewById(R.id.fab_Call);
        floatingActionButton2 = findViewById(R.id.fab_Whatsapp);
        floatingActionButton3 = findViewById(R.id.fab_Instagram);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
              Intent intent = new Intent(Intent.ACTION_DIAL);
              intent.setData(Uri.parse("tel:081319293069"));
              startActivity(intent);
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                openWhatsapp();
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Uri uri = Uri.parse("http://instagram.com/arlojistore_id");
                Intent instagram = new Intent(Intent.ACTION_VIEW);
                instagram.setPackage("com.instagram.android");
                try
                {
                    startActivity(instagram);
                } catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/arlojistore_id")));
                }
            }
        });
    }
    private void openWhatsapp()
    {
        String number = "6281319293069"; //without "+"
        try {
            Intent sendWA = new Intent("android.intent.action.MAIN");
            sendWA.setAction(Intent.ACTION_SEND);
            sendWA.setType("text/palain");
            sendWA.putExtra(Intent.EXTRA_TEXT, "Hai, Saya Ingin Bertanya Tentang Pemesanan dengan Nomor Telepon: " + Prevalent.currentOnlineUser.getPhone());
            sendWA.putExtra("jid", number + "@s.whatsapp.net");
            sendWA.setPackage("com.whatsapp");
            startActivity(sendWA);
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
