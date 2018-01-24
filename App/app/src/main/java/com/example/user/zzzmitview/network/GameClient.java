package com.example.user.zzzmitview.network;

import java.io.IOException;

public class GameClient extends Client {
    private NetzwerkListener listener;
    private boolean          spielGestartet;

    public GameClient(String pIPAdresse, String nickname) throws IOException {
        super(pIPAdresse, GameServer.port);
        spielGestartet = false;

        String register = "REGISTER";
        if (nickname != null) {
            register += ' ' + nickname;
        }

        send(register);
    }

    @Override
    public void received(String message) {
        int i1 = message.indexOf(' ');
        int i2 = message.indexOf(',');
        int i3 = message.indexOf(',', i2 + 1);

        String befehl = i1 > 0 ? message.substring(0, i1) : message;
        switch (befehl.toUpperCase()) {
            case "SET":
                if (spielGestartet) {
                    int id = Integer.parseInt(
                            message.substring(i1 + 1, i2)
                    );
                    int x = Integer.parseInt(
                            message.substring(i2 + 1, i3)
                    );
                    int y = Integer.parseInt(
                            message.substring(i3 + 1)
                    );

                    if (listener != null) {
                        listener.onFieldSet(id, x, y);
                    }
                }
                break;

            case "START":
                spielGestartet = true;

                int spielerCount = Integer.parseInt(
                        message.substring(i1 + 1, i2)
                );
                int myID = Integer.parseInt(
                        message.substring(i2 + 1)
                );

                if (listener != null) {
                    listener.onGameStarted(spielerCount, myID);
                }

                break;

            case "GO":
                if (listener != null) {
                    listener.onYourTurn();
                }

            default:
                send("-ERR nicht Teil des Protokolls");
                break;
        }
    }

    public void setListener(NetzwerkListener listener) {
        this.listener = listener;
    }
}