package com.example.user.zzzmitview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
                                        "contentView",
                                        R.layout.activity_spielfeld_singleplayer
                                )
                );
            }
        });

        findViewById(R.id.einsGegenEins).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getApplicationContext(),
                                SpielfeldActivity.class
                        )
                                .putExtra(
                                        "contentView",
                                        R.layout.activity_spielfeld_1v1
                                )
                );
            }
        });

        findViewById(R.id.mehrspieler).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(
                        new Intent(
                                getApplicationContext(),
                                SpielfeldActivity.class
                        )
                                .putExtra(
                                        "contentView",
                                        R.layout.activity_spielfeld_multiplayer
                                )
                );
            }
        });

        findViewById(R.id.online).setOnClickListener(new View.OnClickListener() {
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
                new ChangeNameDialog(MainActivity.this).show();
            }
        });
    }

    void toast(String pText) {
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