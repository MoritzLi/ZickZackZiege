package com.example.user.zzzmitview.network;

public class AndroidGameClient extends GameClient {
    public AndroidGameClient(String pIPAdresse, String nickname) {
        super(pIPAdresse, nickname);
    }

    @Override
    public void send(final String pMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AndroidGameClient.super.send(pMessage);
            }
        }).start();
    }
}