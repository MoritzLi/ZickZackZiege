package com.example.user.zzzmitview.network;

public interface ClientListener {
    public void onGameStarted(int spielerCount, int myID);

    public void onFieldSet(int id, int x, int y);

    public void onYourTurn();
}