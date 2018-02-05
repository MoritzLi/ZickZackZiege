package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.utility.CallbackListener;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;
import com.example.user.zzzmitview.view.NetzwerkSpielerAdapter;

public class ServerDialog extends CallbackDialog {
    private ListView listView;
    private View confirm;

    private GameServer server;

    public ServerDialog(Activity context, CallbackListener listener, GameServer server) {
        super(context, listener);
        this.server = server;

        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_server);

        listView = findViewById(R.id.listView);

        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(true);
                server.starteSpiel();
            }
        });

        WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        TextView textView = findViewById(R.id.ipAdresse);
        textView.setText(ip);
    }

    public void setListData(final NetzwerkSpieler[] data, Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(new NetzwerkSpielerAdapter(getContext(), data));
                confirm.setEnabled(data.length > 1);
            }
        });
    }

    @Override
    public void cancel() {
        setResult(false);
        super.cancel();
    }
}