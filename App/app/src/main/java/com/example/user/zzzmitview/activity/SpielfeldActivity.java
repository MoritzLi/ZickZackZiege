package com.example.user.zzzmitview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Schwierigkeit;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;
import com.example.user.zzzmitview.utility.Spielmodus;
import com.example.user.zzzmitview.view.MultiplayerView;
import com.example.user.zzzmitview.view.SingleplayerView;
import com.example.user.zzzmitview.view.SpielerAdapter;

public class SpielfeldActivity extends AppCompatActivity {
    private Spieler[]      spieler;
    private Spielfeld      spielfeld;
    private SpielerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spielmodus spielmodus = getIntent().hasExtra("spielmodus") ?
                Spielmodus.valueOf(
                        getIntent()
                                .getStringExtra(
                                        "spielmodus"
                                )
                ) :
                Spielmodus.EINZELSPIELER;

        spieler = new Spieler[getIntent().getIntExtra("spielerzahl", 2)];
        for (int i = 0; i < spieler.length; i++) {
            spieler[i] = new Spieler(i + 1);
        }

        spielfeld = new Spielfeld(spieler.length);

        switch (spielmodus) {
            case EINZELSPIELER:
                setContentView(R.layout.activity_spielfeld_singleplayer);

                SingleplayerView singleplayerView = (SingleplayerView) findViewById(R.id.view);
                singleplayerView.setSpielfeld(spielfeld);
                singleplayerView.setSpieler(spieler);
                singleplayerView.setActivity(this);
                singleplayerView.setSchwierigkeit(Schwierigkeit.SCHWIERIG);
                break;

            case MEHRSPIELER:
                setContentView(R.layout.activity_spielfeld_multiplayer);

                MultiplayerView multiplayerView = (MultiplayerView) findViewById(R.id.view);
                multiplayerView.setSpielfeld(spielfeld);
                multiplayerView.setSpieler(spieler);
                multiplayerView.setActivity(this);
                break;
        }

        adapter = new SpielerAdapter(getApplicationContext(), spieler);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void refreshPunkte(int current) {
        spielfeld.getPoints(spieler[current]);
        adapter.notifyDataSetChanged();
    }
}
