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
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;
import com.example.user.zzzmitview.view.NetzwerkSpielerAdapter;

import java.io.IOException;

import static android.content.Context.WIFI_SERVICE;

public class NetzwerkDialog extends AppCompatDialog {
    private final String                 nickname;
    private final NetzwerkDialogListener listener;
    private       GameServer             server;
    private       GameClient             client;

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
            server = null;
            setContentView(R.layout.dialog_netzwerk);
            initButtons();
            setCanceledOnTouchOutside(true);
        } else if (client != null) {
            client.close();
            client = null;
            setContentView(R.layout.dialog_netzwerk);
            initButtons();
            setCanceledOnTouchOutside(true);
        }
    }

    private void initButtons() {
        findViewById(R.id.betreten).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.dialog_client);
                setCanceledOnTouchOutside(false);
                findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText     editText = findViewById(R.id.editText);
                        final String text     = editText.getText().toString();
                        if (text.length() > 0) {
                            setContentView(R.layout.dialog_warten);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        client = new GameClient(text, nickname);
                                        listener.notify(client, server);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                });
            }
        });

        findViewById(R.id.erstellen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCanceledOnTouchOutside(false);
                try {
                    server = new GameServer();
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
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