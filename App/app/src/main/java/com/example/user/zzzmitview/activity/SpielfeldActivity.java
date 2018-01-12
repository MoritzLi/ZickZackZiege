package com.example.user.zzzmitview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;
import com.example.user.zzzmitview.view.MultiplayerView;
import com.example.user.zzzmitview.view.SpielerAdapter;

public class SpielfeldActivity extends AppCompatActivity {
    private Spieler[]      spieler;
    private Spielfeld      spielfeld;
    private SpielerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contenView = getIntent()
                .getIntExtra(
                        "contentView",
                        R.layout.activity_spielfeld_singleplayer
                );
        setContentView(
                contenView
        );

        switch (contenView) {
            case R.layout.activity_spielfeld_multiplayer:
                spieler = new Spieler[getIntent().getIntExtra("spielerzahl", 2)];
                for (int i = 0; i < spieler.length; i++) {
                    spieler[i] = new Spieler(i + 1, null, 0);
                    spieler[i].setPunkte(0);
                }

                spielfeld = new Spielfeld(spieler.length);

                adapter = new SpielerAdapter(getApplicationContext(), spieler);
                ListView listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);

                MultiplayerView view = (MultiplayerView) findViewById(R.id.view);
                view.setSpielfeld(spielfeld);
                view.setSpieler(spieler);
                view.setActivity(this);
                break;
        }
    }

    public void resetPunkte(int current) {
        spielfeld.auswertung(spieler[current]);
        adapter.notifyDataSetChanged();
    }
}
