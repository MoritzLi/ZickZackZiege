package com.example.user.zzzmitview.network;

import com.example.user.zzzmitview.utility.NetzwerkSpieler;

public interface NetzwerkListener {
    void onPlayerRegister(NetzwerkSpieler spieler);

    void onPlayerUnregister();

    void onGameStarted(int spielerCount, int myID);

    void onFieldSet(int id, int x, int y);

    void onYourTurn();
}