package com.example.user.zzzmitview.dialog;

import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;

public interface NetzwerkDialogListener {
    void notify(GameClient client, GameServer server);
}