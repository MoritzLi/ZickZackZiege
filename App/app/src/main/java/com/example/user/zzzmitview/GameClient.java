package com.example.user.zzzmitview;

public class GameClient extends Client {
    private boolean      spielGestartet;
    boolean go;

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

                    // setzen
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
                //GUI

                break;

            case "GO":
                go = true;

            default:
                send("-ERR nicht Teil des Protokolls");
                break;
        }
    }
}