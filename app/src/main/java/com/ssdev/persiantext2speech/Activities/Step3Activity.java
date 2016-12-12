package com.ssdev.persiantext2speech.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ssdev.persiantext2speech.R;

public class Step3Activity extends AppCompatActivity {

    Button btnMMS,btnWhatsapp,btnTelegram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);
        btnMMS=(Button)findViewById(R.id.btnMMS);
        btnWhatsapp=(Button)findViewById(R.id.btnWhatsapp);
        btnTelegram=(Button)findViewById(R.id.btnTelegram);
        btnTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appName = "org.telegram.messenger";
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                myIntent.setPackage(appName);
                myIntent.putExtra(Intent.EXTRA_TEXT, "hello");
                startActivity(myIntent);
            }
        });
    }
}
