package com.example.user.zzzmitview.network;

import com.example.user.zzzmitview.utility.Spieler;

public interface ServerListener {
    public void onPlayerRegister(Spieler spieler);

    public void onFieldSet(int id, int x, int y);
}