package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;
import com.example.user.zzzmitview.view.NetzwerkSpielerAdapter;

public class ServerDialog extends AppCompatDialog {
    private ListView   listView;
    private GameServer server;

    public ServerDialog(Activity context, GameServer server) {
        super(context);
        this.server = server;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_server);

        listView = findViewById(R.id.listView);

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.starteSpiel();
            }
        });

        WifiManager wm       = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String      ip       = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TextView    textView = findViewById(R.id.ipAdresse);
        textView.setText(ip);
    }

    public void setListData(final NetzwerkSpieler[] data, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new NetzwerkSpielerAdapter(getContext(), data));
            }
        });
    }
}