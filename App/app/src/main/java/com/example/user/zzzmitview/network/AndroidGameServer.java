package com.example.user.zzzmitview.network;

public class AndroidGameServer extends GameServer {
    @Override
    public void send(final String pClientIP, final int pClientPort, final String pMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AndroidGameServer.super.send(pClientIP, pClientPort, pMessage);
            }
        }).start();
    }
}