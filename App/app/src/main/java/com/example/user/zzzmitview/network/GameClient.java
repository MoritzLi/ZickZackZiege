package com.example.user.zzzmitview.network;

public class GameClient extends Client {
    private ClientListener listener;
    private boolean spielGestartet;
    private boolean go;

    public GameClient(String pIPAdresse) {
        super(pIPAdresse, GameServer.port);
        spielGestartet = false;

        send("REGISTER");
    }

    @Override
    public void processMessage(String pMessage) {
        String befehl = pMessage.contains(" ") ? pMessage.substring(0, pMessage.indexOf(' ')) : pMessage;
        switch (befehl) {
            case "SET":
                if (spielGestartet) {
                    int i1 = pMessage.indexOf(' ');
                    int i2 = pMessage.indexOf(',');
                    int i3 = pMessage.indexOf(',', i2 + 1);

                    int id = Integer.parseInt(
                            pMessage.substring(i1 + 1, i2)
                    );
                    int x = Integer.parseInt(
                            pMessage.substring(i2 + 1, i3)
                    );
                    int y = Integer.parseInt(
                            pMessage.substring(i3 + 1)
                    );

                    if (listener != null) {
                        listener.onFieldSet(id, x, y);
                    }
                }
                break;

            case "START":
                spielGestartet = true;

                int i1 = pMessage.indexOf(' ');
                int i2 = pMessage.indexOf(',');

                int spielerCount = Integer.parseInt(
                        pMessage.substring(i1 + 1, i2)
                );
                int myID = Integer.parseInt(
                        pMessage.substring(i2 + 1)
                );

                if (listener != null) {
                    listener.onGameStarted(spielerCount, myID);
                }
                //GUI

                break;

            case "GO":
                go = true;

                if (listener != null) {
                    listener.onYourTurn();
                }

            default:
                send("-ERR nicht Teil des Protokolls");
                break;
        }
    }

    public void setListener(ClientListener listener) {
        this.listener = listener;
    }
}