package com.example.user.zzzmitview.dialog;

import com.example.user.zzzmitview.network.AndroidGameClient;
import com.example.user.zzzmitview.network.AndroidGameServer;

public interface NetzwerkDialogListener {
    void notify(AndroidGameClient client, AndroidGameServer server);
}