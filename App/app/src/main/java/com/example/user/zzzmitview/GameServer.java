package com.example.user.zzzmitview;

public class GameServer extends Server {
    public static final int port = 5453;

    private       boolean       spielGestartet;
    private final List<Spieler> spielerList;
    private       Spieler[]     spielerArray;

    private ServerListener listener;

    boolean go;

    private int spielzuege;

    GameServer() {
        super(port);
        spielGestartet = false;
        spielerList = new List<>();
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        String befehl = pMessage.contains(" ") ? pMessage.substring(0, pMessage.indexOf(' ')) : pMessage;
        switch (befehl) {
            case "REGISTER":
                if (!spielGestartet) {
                    if (!spielerList.find(new Spieler(pClientIP))) {
                        Spieler s = new Spieler(-1, pClientIP, pClientPort);
                        spielerList.append(s);
                        if (listener != null)
                            listener.onPlayerRegister(s);
                    } else {
                        send(pClientIP, pClientPort, "-ERR es existiert bereits eine Registrierung für diese IP-Adresse");
                    }
                }
                break;

            case "SET":
                if (spielGestartet) {
                    spielzuege++;

                    if (pClientIP.equals("localhost")) {
                        int id = 1;
                        int i1 = pMessage.indexOf(' ');
                        int i2 = pMessage.indexOf(',');
                        int x = Integer.parseInt(
                                pMessage.substring(i1 + 1, i2)
                        ), y = Integer.parseInt(
                                pMessage.substring(i2 + 1)
                        );
                        sendToAll("SET " + id + ',' + x + ',' + y);

                        spielerList.toFirst();
                        Spieler next = spielerList.getContent();
                        send(next.getIP(), next.getPort(), "GO");
                    } else {
                        for (int i = 1; i < spielerArray.length; i++) {
                            if (spielerArray[i].getIP().equals(pClientIP)) {
                                int i1 = pMessage.indexOf(' ');
                                int i2 = pMessage.indexOf(',');

                                int id = spielerArray[i].getId();
                                int x = Integer.parseInt(
                                        pMessage.substring(i1 + 1, i2)
                                );
                                int y = Integer.parseInt(
                                        pMessage.substring(i2 + 1)
                                );
                                //setzen

                                for (int j = 1; j < spielerArray.length; j++) {
                                    if (j != i) {
                                        send(
                                                spielerArray[j].getIP(),
                                                spielerArray[j].getPort(),
                                                "SET " + id + ',' + x + ',' + y
                                        );
                                    }
                                }

                                if (i < spielerArray.length - 1) {
                                    Spieler next = spielerArray[i + 1];
                                    send(
                                            next.getIP(),
                                            next.getPort(),
                                            "GO"
                                    );
                                } else {
                                    go = true;
                                }
                                break;
                            }
                        }
                    }

                    if (spielzuege == (3 + spielerArray.length) * (3 + spielerArray.length)) {
                        //Spielende
                    }
                }
                break;

            default:
                send(pClientIP, pClientPort, "-ERR nicht Teil des Protokolls");
                break;
        }
    }

    @Override
    public void processClosedConnection(String pClientIP, int pClientPort) {
        if (!spielGestartet) {
            if (spielerList.find(new Spieler(-1, pClientIP, pClientPort))) {
                spielerList.remove();
            }
        }
    }

    void starteSpiel() {
        this.spielGestartet = true;
        spielerArray = new Spieler[spielerList.size() + 1];
        spielerList.toFirst();

        for (int i = 1; i < spielerArray.length; i++, spielerList.next()) {
            Spieler old = spielerList.getContent();
            spielerArray[i] = new Spieler(i + 1, old.getIP(), old.getPort());
            send(old.getIP(), old.getPort(), "START " + spielerArray.length + ',' + (i + 1));
        }

        spielerArray[0] = new Spieler(1, "localhost", port);

        //GUI

        spielzuege = 0;

        go = true;
    }

    Spieler[] getSpieler() {
        return spielerList.fill(new Spieler[spielerList.size()]);
    }

    void setListener(ServerListener listener) {
        this.listener = listener;
    }
}