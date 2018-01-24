package com.example.user.zzzmitview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.dialog.NetzwerkDialog;
import com.example.user.zzzmitview.dialog.NetzwerkDialogListener;
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.network.NetzwerkListener;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;
import com.example.user.zzzmitview.utility.Schwierigkeit;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;
import com.example.user.zzzmitview.utility.Spielmodus;
import com.example.user.zzzmitview.view.NetzwerkView;
import com.example.user.zzzmitview.view.SingleplayerView;
import com.example.user.zzzmitview.view.SpielListener;
import com.example.user.zzzmitview.view.SpielerAdapter;
import com.example.user.zzzmitview.view.SpielfeldView;

public class SpielfeldActivity extends AppCompatActivity implements SpielListener, NetzwerkDialogListener {
    private Spieler[]      spieler;
    private Spielfeld      spielfeld;
    private SpielerAdapter adapter;
    private SpielfeldView  view;
    private NetzwerkDialog dialog;

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

        setContentView(spielmodus);

        spieler = new Spieler[getIntent().getIntExtra(MainActivity.INTENT_EXTRA_SPIELERZAHL, 2)];
        for (int i = 0; i < spieler.length; i++) {
            spieler[i] = new Spieler(i + 1);
        }

        spielfeld = new Spielfeld(spieler.length);

        view = findViewById(R.id.view);
        view.setSpielfeld(spielfeld);
        view.setSpieler(spieler);
        view.setListener(this);

        switch (spielmodus) {
            case EINZELSPIELER:
                SingleplayerView singleplayerView = (SingleplayerView) view;
                singleplayerView.setSchwierigkeit(Schwierigkeit.valueOf(getIntent().getStringExtra(MainActivity.INTENT_EXTRA_SCHWIERIGKEIT)));
                break;

            case MEHRSPIELER:
                break;

            case NETZWERK_LOKAL:
                dialog = new NetzwerkDialog(this, this);
                dialog.setCancelable(false);
                dialog.show();
                break;

            case ONLINE:
                break;
        }

        adapter = new SpielerAdapter(getApplicationContext(), spieler);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();

        if (server != null) {
            server.close();
        }
        if (client != null) {
            client.close();
        }
    }

    private void setContentView(Spielmodus spielmodus) {
        switch (spielmodus) {
            case EINZELSPIELER:
                setContentView(R.layout.activity_spielfeld_singleplayer);
                break;

            case MEHRSPIELER:
                setContentView(R.layout.activity_spielfeld_multiplayer);
                break;

            case NETZWERK_LOKAL:
                setContentView(R.layout.activity_spielfeld_netzwerk);
                break;

            case ONLINE:
                break;
        }
    }

    @Override
    public void notify(GameClient client, final GameServer server) {
        this.client = client;
        this.server = server;

        final NetzwerkView netzwerkView = (NetzwerkView) view;
        netzwerkView.setClient(client);
        netzwerkView.setServer(server);

        NetzwerkListener listener = new NetzwerkListener() {
            @Override
            public void onPlayerRegister(NetzwerkSpieler spieler) {
                dialog.setListData(server.getSpieler());
            }

            @Override
            public void onPlayerUnregister() {
                dialog.setListData(server.getSpieler());
            }

            @Override
            public void onGameStarted(int spielerCount, int myID) {
                dialog.dismiss();

                spielfeld = new Spielfeld(spielerCount);
                netzwerkView.setSpielfeld(spielfeld);
                netzwerkView.setMyID(myID);
                netzwerkView.setGo(SpielfeldActivity.this.server != null);
            }

            @Override
            public void onFieldSet(int id, int x, int y) {
                spielfeld.setValue(id, x, y);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        netzwerkView.invalidate();
                    }
                });
            }

            @Override
            public void onYourTurn() {
                netzwerkView.setGo(true);
            }
        };

        if (server == null) {
            client.setListener(listener);
        } else {
            server.setListener(listener);
        }
    }

    @Override
    public void round() {
        spielfeld.getPoints(spieler);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void end() {

    }
}
