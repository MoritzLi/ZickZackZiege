package com.example.user.zzzmitview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.network.ClientListener;
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.network.ServerListener;
import com.example.user.zzzmitview.utility.Schwierigkeit;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;
import com.example.user.zzzmitview.utility.Spielmodus;
import com.example.user.zzzmitview.view.MultiplayerView;
import com.example.user.zzzmitview.view.NetzwerkView;
import com.example.user.zzzmitview.view.SingleplayerView;
import com.example.user.zzzmitview.view.SpielerAdapter;
import com.example.user.zzzmitview.view.SpielfeldView;

public class SpielfeldActivity extends AppCompatActivity {
    private Spieler[]      spieler;
    private Spielfeld      spielfeld;
    private SpielerAdapter adapter;

    private GameServer server;
    private GameClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spielmodus spielmodus = getIntent().hasExtra(MainActivity.INTENT_EXTRA_SPIELMODUS) ?
                Spielmodus.valueOf(
                        getIntent()
                                .getStringExtra(
                                        MainActivity.INTENT_EXTRA_SPIELMODUS
                                )
                ) :
                Spielmodus.EINZELSPIELER;

        spieler = new Spieler[getIntent().getIntExtra(MainActivity.INTENT_EXTRA_SPIELERZAHL, 2)];
        for (int i = 0; i < spieler.length; i++) {
            spieler[i] = new Spieler(i + 1);
        }

        spielfeld = new Spielfeld(spieler.length);

        final SpielfeldView view = (SpielfeldView) findViewById(R.id.view);
        view.setSpielfeld(spielfeld);
        view.setSpieler(spieler);
        view.setActivity(this);

        switch (spielmodus) {
            case EINZELSPIELER:
                setContentView(R.layout.activity_spielfeld_singleplayer);

                SingleplayerView singleplayerView = (SingleplayerView) view;
                singleplayerView.setSchwierigkeit(Schwierigkeit.valueOf(getIntent().getStringExtra(MainActivity.INTENT_EXTRA_SCHWIERIGKEIT)));
                break;

            case MEHRSPIELER:
                setContentView(R.layout.activity_spielfeld_multiplayer);
                break;

            case NETZWERK_LOKAL:
                setContentView(R.layout.activity_spielfeld_netzwerk);

                final NetzwerkView netzwerkView = (NetzwerkView) view;
                netzwerkView.setClient(client);
                netzwerkView.setServer(server);

                if (client != null) {
                    client.setListener(new ClientListener() {
                        @Override
                        public void onGameStarted(int spielerCount, int myID) {
                            netzwerkView.setMyID(myID);
                        }

                        @Override
                        public void onFieldSet(int id, int x, int y) {
                            spielfeld.setValue(id, x, y);
                        }

                        @Override
                        public void onYourTurn() {
                            netzwerkView.setGo(true);
                        }
                    });
                } else {
                    server.setListener(new ServerListener() {
                        @Override
                        public void onPlayerRegister(Spieler spieler) {

                        }

                        @Override
                        public void onFieldSet(int id, int x, int y) {
                            spielfeld.setValue(id, x, y);
                        }
                    });
                }
                break;
        }

        adapter = new SpielerAdapter(getApplicationContext(), spieler);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public void refreshPunkte(int... indices) {
        for (int index : indices) {
            spielfeld.getPoints(spieler[index]);
        }

        adapter.notifyDataSetChanged();
    }
}
