package com.example.user.zzzmitview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.dialog.ChangeNameDialog;
import com.example.user.zzzmitview.dialog.SpielerzahlDialog;
import com.example.user.zzzmitview.utility.Spielmodus;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.einzelspieler).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getApplicationContext(),
                                SpielfeldActivity.class
                        )
                                .putExtra(
                                        "spielmodus",
                                        Spielmodus.EINZELSPIELER.toString()
                                )
                );
            }
        });

        findViewById(R.id.mehrspieler).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SpielerzahlDialog(
                        MainActivity.this,
                        new Intent(
                                getApplicationContext(),
                                SpielfeldActivity.class
                        )
                                .putExtra(
                                        "spielmodus",
                                        Spielmodus.MEHRSPIELER.toString()
                                )
                ).show();
            }
        });

        findViewById(R.id.online).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast("Demnächst verfügbar.");
//                startActivity(
//                        new Intent(
//                                getApplicationContext(),
//                                SpielfeldActivity.class
//                        )
//                                .putExtra(
//                                        "spielmodus",
//                                        Spielmodus.ONLINE
//                                )
//                );
            }
        });

        findViewById(R.id.regeln).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getApplicationContext(),
                                RegelActivity.class
                        )
                );
            }
        });

        findViewById(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeNameDialog(MainActivity.this).show();
            }
        });
    }

    public void toast(String pText) {
        Toast t = Toast.makeText(getApplicationContext(), pText, Toast.LENGTH_SHORT);
        t.show();
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
}