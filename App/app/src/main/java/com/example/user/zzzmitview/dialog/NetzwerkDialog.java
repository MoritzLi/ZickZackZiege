package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialog;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.network.AndroidGameClient;
import com.example.user.zzzmitview.network.AndroidGameServer;
import com.example.user.zzzmitview.network.ClientTask;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;
import com.example.user.zzzmitview.view.NetzwerkSpielerAdapter;

import static android.content.Context.WIFI_SERVICE;

public class NetzwerkDialog extends AppCompatDialog {
    private final String                 nickname;
    private final NetzwerkDialogListener listener;
    private       AndroidGameServer      server;
    private       AndroidGameClient      client;

    public NetzwerkDialog(Activity context, NetzwerkDialogListener listener) {
        super(context);

        nickname = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("nickname", null);

        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_netzwerk);

        initButtons();
    }

    @Override
    public void onBackPressed() {
        if (server != null) {
            server.close();
            setContentView(R.layout.dialog_netzwerk);
            initButtons();
        } else if (client != null) {
            client.close();
            setContentView(R.layout.dialog_netzwerk);
            initButtons();
        } else {
            cancel();
            getOwnerActivity().finish();
        }
    }

    private void initButtons() {
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
                            setContentView(R.layout.dialog_warten);
                            ClientTask task = new ClientTask();
                            task.execute(text, nickname);
                            try {
                                client = task.get();
                                listener.notify(client, server);
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
                server = new AndroidGameServer();
                listener.notify(client, server);
                setContentView(R.layout.dialog_server);

                setListData(server.getSpieler());

                WifiManager wm       = (WifiManager) getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
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

    public void setListData(final NetzwerkSpieler[] spieler) {
        ((Activity) listener).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listView = findViewById(R.id.listView);
                listView.setAdapter(new NetzwerkSpielerAdapter(getContext(), spieler));
            }
        });
    }
}