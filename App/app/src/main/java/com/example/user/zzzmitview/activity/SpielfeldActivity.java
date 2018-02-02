package com.example.user.zzzmitview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.dialog.ErgebnisDialog;
import com.example.user.zzzmitview.dialog.IdleDialog;
import com.example.user.zzzmitview.dialog.NetzwerkDialog;
import com.example.user.zzzmitview.dialog.ServerDialog;
import com.example.user.zzzmitview.dialog.VerlassenDialog;
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.network.NetzwerkListener;
import com.example.user.zzzmitview.utility.CallbackListener;
import com.example.user.zzzmitview.utility.Schwierigkeit;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;
import com.example.user.zzzmitview.utility.Spielmodus;
import com.example.user.zzzmitview.view.MultiplayerView;
import com.example.user.zzzmitview.view.NetzwerkView;
import com.example.user.zzzmitview.view.SingleplayerView;
import com.example.user.zzzmitview.view.SpielListener;
import com.example.user.zzzmitview.view.SpielerAdapter;
import com.example.user.zzzmitview.view.SpielfeldView;
import com.example.user.zzzmitview.view.StoryView;

public class SpielfeldActivity extends AppCompatActivity implements SpielListener, CallbackListener {
    private Spieler[] spieler;
    private Spielfeld spielfeld;
    private SpielerAdapter adapter;
    private SpielfeldView view;

    private Spielmodus spielmodus;

    private GameServer server;
    private GameClient client;

    private IdleDialog idleDialog;
    private ServerDialog serverDialog;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spielmodus = getIntent().hasExtra(MainActivity.INTENT_EXTRA_SPIELMODUS) ?
                Spielmodus.valueOf(
                        getIntent()
                                .getStringExtra(
                                        MainActivity.INTENT_EXTRA_SPIELMODUS
                                )
                ) :
                Spielmodus.EINZELSPIELER;

        setContentView();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        spieler = new Spieler[getIntent().getIntExtra(MainActivity.INTENT_EXTRA_SPIELERZAHL, 2)];
        for (int i = 0; i < spieler.length; i++) {
            spieler[i] = new Spieler(i + 1);
        }

        spielfeld = new Spielfeld(spieler.length);

        view = findViewById(R.id.view);
        view.setSpielfeld(spielfeld);
        view.setSpieler(spieler);
        view.setListener(this);

        adapter = new SpielerAdapter(getApplicationContext(), spieler);

        switch (spielmodus) {
            case EINZELSPIELER:
                SingleplayerView singleplayerView = (SingleplayerView) view;
                singleplayerView.setSchwierigkeit(Schwierigkeit.valueOf(getIntent().getStringExtra(MainActivity.INTENT_EXTRA_SCHWIERIGKEIT)));
                break;

            case MEHRSPIELER:
                MultiplayerView multiplayerView = (MultiplayerView) view;
                multiplayerView.setAdapter(adapter);
                adapter.setCurrent(0);
                break;

            case NETZWERK_LOKAL:
                NetzwerkDialog netzwerkDialog = new NetzwerkDialog(this, this);
                netzwerkDialog.show();
                break;

            case STORYMODUS:
                break;

            case TUTORIAL:
                final StoryView v = findViewById(R.id.storyView);
                v.setSpielfeld(spielfeld);
                v.setSpielfeldView(view);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (v.tapZaehler < v.d1.length) {
                            v.invalidate();
                        } else {
                            findViewById(R.id.layout1).setVisibility(View.VISIBLE);
                            v.invalidate();
                        }
                        v.tapZaehler++;
                    }
                });
                break;
        }

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (spielmodus == Spielmodus.MEHRSPIELER || spielmodus == Spielmodus.NETZWERK_LOKAL) {
            new VerlassenDialog(this, this).show();
        } else {
            super.onBackPressed();
        }
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
        if (idleDialog != null && idleDialog.isShowing()) {
            idleDialog.dismiss();
        }
        if (serverDialog != null && serverDialog.isShowing()) {
            serverDialog.dismiss();
        }
    }

    @Override
    public void round() {
        spielfeld.getPoints(spieler);
        spielfeld.nextRound();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

        if (!spielfeld.isPlaying())
            end();
    }

    @Override
    public void notify(Class source, boolean success, Object... results) {
        if (success) {
            if (source == ErgebnisDialog.class) {
                spielfeld.clear();

                spielfeld.getPoints(spieler);
                adapter.notifyDataSetChanged();

                view.reset();
                view.invalidate();

                if (spielmodus == Spielmodus.NETZWERK_LOKAL) {
                    if (client != null) {
                        idleDialog = new IdleDialog(this, this);
                        idleDialog.show();
                    }
                }
            } else if (source == NetzwerkDialog.class) {
                Object networkComponent = results[0];

                final NetzwerkView netzwerkView = (NetzwerkView) view;

                NetzwerkListener listener = new NetzwerkListener() {
                    @Override
                    public void onPlayersChanged() {
                        if (serverDialog.isShowing()) {
                            serverDialog.setListData(server.getSpieler(), SpielfeldActivity.this);
                        } else {
                            spieler = server.getSpieler();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new SpielerAdapter(getApplicationContext(), spieler);
                                    listView.setAdapter(adapter);
                                }
                            });
                        }
                    }

                    @Override
                    public void onGameStarted(int spielerCount, int myID) {
                        if (idleDialog != null) {
                            idleDialog.dismiss();
                        } else if (serverDialog != null) {
                            serverDialog.dismiss();
                        }

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
                        round();
                    }

                    @Override
                    public void onYourTurn() {
                        netzwerkView.setGo(true);
                    }
                };

                if (networkComponent instanceof GameClient) {
                    client = (GameClient) networkComponent;

                    client.setListener(listener);

                    netzwerkView.setClient(client);

                    idleDialog = new IdleDialog(this, this);
                    idleDialog.show();
                } else {
                    server = (GameServer) networkComponent;

                    server.setListener(listener);

                    netzwerkView.setServer(server);

                    serverDialog = new ServerDialog(this, this, server);
                    serverDialog.show();
                    serverDialog.setListData(server.getSpieler(), this);
                }
            }
        } else {
            finish();
        }
    }

    public void end() {
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        ErgebnisDialog ergebnisDialog = new ErgebnisDialog(SpielfeldActivity.this, spieler, SpielfeldActivity.this);
                        ergebnisDialog.show();
                    }
                }
        );
    }

    private void setContentView() {
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

            case TUTORIAL:
                setContentView(R.layout.tutorial_feld);
                break;

        }
    }
}
