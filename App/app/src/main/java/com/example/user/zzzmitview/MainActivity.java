package com.example.user.zzzmitview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        SharedPreferences s = this.getSharedPreferences(
                "Datenspeicher", Context.MODE_PRIVATE);

        TextView ueberschrift = (TextView) findViewById(R.id.ueberschrift);
        ueberschrift.setTextSize(30);

        final Drawer        view  = new Drawer(this);
        final Einzelspieler viewE = new Einzelspieler(this);

        Button einzelSpieler = (Button) findViewById(R.id.einzelspieler);
        einzelSpieler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(viewE);
            }
        });

        Button einsGegenEins = (Button) findViewById(R.id.einsGegenEins);
        einsGegenEins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(view);
                //erstelleAuswaehler();
            }
        });

        Button mehrspieler = (Button) findViewById(R.id.mehrspieler);
        mehrspieler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                toast("Nicht verfügbar.");
            }
        });

        Button online = (Button) findViewById(R.id.online);
        online.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                toast("Nicht verfügbar.");
            }
        });

        Button regeln = (Button) findViewById(R.id.regeln);
        regeln.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                toast("Nicht verfügbar.");
            }
        });
    }

    public void toast(String pText) {
        Toast t = Toast.makeText(getApplicationContext(), pText, Toast.LENGTH_SHORT);
        t.show();
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public void onBackPressed() { //Activity wird neu geladen
        Intent neuLaden = new Intent(MainActivity.this, MainActivity.class);
        MainActivity.this.startActivity(neuLaden);
        finish();
    }

    public void erstelleAuswaehler() {
        ViewGroup r = (ViewGroup) findViewById(R.id.einsGegenEinsAuswaehlen);
        EditText  e = new EditText(this);
        r.addView(e);
    }

    public void speicherDaten(String speichername, String pDaten, SharedPreferences s) {
        s.edit().putString(speichername, pDaten).apply();
    }

    public String getDaten(String speichername, String pDaten, SharedPreferences s) {
        return s.getString(speichername, "");
    }
}
