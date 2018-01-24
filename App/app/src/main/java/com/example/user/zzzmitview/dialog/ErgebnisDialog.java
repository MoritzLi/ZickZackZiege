package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Spieler;

public class ErgebnisDialog extends AppCompatDialog {
    private final Spieler[] spieler;

    public ErgebnisDialog(Activity context, Spieler[] spieler) {
        super(context);
        this.spieler = spieler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ergebnis);

        sort();

        ViewGroup viewGroup = findViewById(R.id.linearLayout);
        for (Spieler s : spieler) {
            View v = getLayoutInflater().inflate(R.layout.list_item_spieler, viewGroup, false);

            TextView name   = v.findViewById(R.id.spieler);
            TextView punkte = v.findViewById(R.id.punkte);

            name.setText(s.getName());
            punkte.setText(s.getPunkteString());

            viewGroup.addView(v);
        }

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getOwnerActivity().finish();
            }
        });

        findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sort() {
        for (int i = 0; i < spieler.length - 1; i++) {
            int min = i;
            for (int a = i + 1; a < spieler.length; a++) {
                if (spieler[a].getPunkte() < spieler[min].getPunkte()) {
                    min = a;
                }
            }
            swap(i, min);
        }
    }

    private void swap(int a, int b) {
        Spieler x = spieler[a];
        spieler[a] = spieler[b];
        spieler[b] = x;
    }
}