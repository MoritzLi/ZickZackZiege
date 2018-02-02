package com.example.user.zzzmitview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.dialog.NicknameDialog;
import com.example.user.zzzmitview.dialog.SchwierigkeitDialog;
import com.example.user.zzzmitview.dialog.SpielerzahlDialog;
import com.example.user.zzzmitview.utility.Spielmodus;

public class MainActivity extends AppCompatActivity {
    static final String INTENT_EXTRA_SPIELMODUS = "spielmodus";
    public static final String INTENT_EXTRA_SPIELERZAHL = "spielerzahl";
    public static final String INTENT_EXTRA_SCHWIERIGKEIT = "schwierigkeit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.einzelspieler).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new SchwierigkeitDialog(
                        MainActivity.this,
                        new Intent(
                                getApplicationContext(),
                                SpielfeldActivity.class
                        )
                                .putExtra(
                                        INTENT_EXTRA_SPIELMODUS,
                                        Spielmodus.EINZELSPIELER.toString()
                                )
                ).show();
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
                                        INTENT_EXTRA_SPIELMODUS,
                                        Spielmodus.MEHRSPIELER.toString()
                                )
                ).show();
            }
        });

        findViewById(R.id.netzwerk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getApplicationContext(),
                                SpielfeldActivity.class
                        )
                                .putExtra(
                                        INTENT_EXTRA_SPIELMODUS,
                                        Spielmodus.NETZWERK_LOKAL.toString()
                                )
                );
            }
        });

        findViewById(R.id.tutorial).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast("Demnächst verfügbar.");
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
                new NicknameDialog(
                        MainActivity.this
                ).show();
            }
        });

        findViewById(R.id.storymodus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity
                        (new Intent(
                                        getApplicationContext(),
                                        SpielfeldActivity.class
                                )
                                        .putExtra(
                                                "spielmodus",
                                                Spielmodus.TUTORIAL.toString()
                                        )
                        );
            }


        });
    }


    public void toast(String pText) {
        Toast t = Toast.makeText(getApplicationContext(), pText, Toast.LENGTH_SHORT);
        t.show();
    }
}