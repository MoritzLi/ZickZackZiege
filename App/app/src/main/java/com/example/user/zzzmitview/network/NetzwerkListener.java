package com.example.user.zzzmitview.network;

import com.example.user.zzzmitview.utility.Spieler;

public interface NetzwerkListener {
    void onGameStarted(int spielerCount, int myID);

    void onPlayerRegister(Spieler spieler);

    void onFieldSet(int id, int x, int y);

    void onYourTurn();
}