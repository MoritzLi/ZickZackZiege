package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.utility.CallbackListener;

import java.io.IOException;

public class NetzwerkDialog extends CallbackDialog {
    private View select;
    private View enterIP;
    private View contentView;

    private final String     nickname;
    private       GameServer server;
    private       GameClient client;

    public NetzwerkDialog(Activity context, CallbackListener listener) {
        super(context, listener);

        nickname = PreferenceManager.getDefaultSharedPreferences(context)
                .getString("nickname", null);

        this.server = null;
        this.client = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        select = getLayoutInflater().inflate(R.layout.dialog_netzwerk, null);
        enterIP = getLayoutInflater().inflate(R.layout.dialog_client, null);

        initButtons();

        setContentView(select);
    }

    @Override
    public void onBackPressed() {
        if (contentView == enterIP) {
            setContentView(select);
        } else {
            dismiss();
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        contentView = view;
    }

    private void initButtons() {
        select.findViewById(R.id.betreten).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(enterIP);
            }
        });

        final View progressBar = enterIP.findViewById(R.id.progressBar);
        enterIP.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getVisibility() != View.VISIBLE) {
                    progressBar.setVisibility(View.VISIBLE);
                    EditText     editText = enterIP.findViewById(R.id.editText);
                    final String text     = editText.getText().toString().replace(" ", "");
                    if (text.length() > 0) {
                        try {
                            client = new GameClient(text, nickname);
                            setResult(true, client);
                            dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        select.findViewById(R.id.erstellen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    server = new GameServer();
                    setResult(true, server);
                    dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}