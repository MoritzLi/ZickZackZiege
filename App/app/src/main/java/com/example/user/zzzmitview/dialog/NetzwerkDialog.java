package com.example.user.zzzmitview.dialog;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialog;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.activity.SpielfeldActivity;
import com.example.user.zzzmitview.network.ClientTask;
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;

import static android.content.Context.WIFI_SERVICE;

public class NetzwerkDialog extends AppCompatDialog {
    private final SpielfeldActivity activity;
    private final String            nickname;
    private       GameServer        server;
    private       GameClient        client;

    public NetzwerkDialog(SpielfeldActivity context) {
        super(context);

        nickname = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("nickname", null);

        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_netzwerk);

        findViewById(R.id.betreten).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.dialog_client);
                findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = findViewById(R.id.editText);
                        String   text     = editText.getText().toString();
                        if (text.length() > 0) {
                            ClientTask task = new ClientTask();
                            task.execute(text, nickname);
                            try {
                                client = task.get();
                                activity.notifyNetzwerk(client, server);
                                setContentView(R.layout.dialog_warten);
                                findViewById(R.id.start).setVisibility(View.GONE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
            }
        });

        findViewById(R.id.erstellen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server = new GameServer();
                activity.notifyNetzwerk(client, server);
                setContentView(R.layout.dialog_server);

                WifiManager wm       = (WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE);
                String      ip       = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
                TextView    textView = findViewById(R.id.ipAdresse);
                textView.setText(ip);

                findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        server.starteSpiel();
                    }
                });
            }
        });
    }

    public void setListData(NetzwerkSpieler[] spieler) {

    }
}