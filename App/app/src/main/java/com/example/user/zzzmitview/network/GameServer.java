package com.example.user.zzzmitview.network;

import com.example.user.zzzmitview.utility.List;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;

public class GameServer extends Server {
    public static final int port = 5453;

    private       boolean               spielGestartet;
    private final List<NetzwerkSpieler> spielerList;
    private       NetzwerkSpieler[]     spielerArray;

    private int aktuellerSpieler;

    private NetzwerkListener listener;

    public GameServer() {
        super(port);

        spielGestartet = false;

        NetzwerkSpieler ich = new NetzwerkSpieler(1, "localhost", port);
        ich.setName("Du");
        spielerList = new List<>(ich);
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        int i1 = pMessage.indexOf(' ');
        int i2 = pMessage.indexOf(',');

        String befehl = (i1 > 0 ? pMessage.substring(0, i1) : pMessage).toUpperCase();

        switch (befehl) {
            case "REGISTER":
                if (!spielGestartet && spielerList.size() < 8) {
                    if (!spielerList.find(new NetzwerkSpieler(pClientIP))) {
                        NetzwerkSpieler s = new NetzwerkSpieler(spielerList.size() + 1, pClientIP, pClientPort);
                        s.setName(pMessage.substring(pMessage.indexOf(' ') + 1));

                        spielerList.append(s);

                        if (listener != null) {
                            listener.onPlayerRegister(s);
                        }
                    } else {
                        send(pClientIP, pClientPort, "-ERR es existiert bereits eine Registrierung für diese IP-Adresse");
                    }
                }
                break;

            case "SET":
                if (spielGestartet) {
                    int id = 1;
                    int x = Integer.parseInt(
                            pMessage.substring(i1 + 1, i2)
                    ), y = Integer.parseInt(
                            pMessage.substring(i2 + 1)
                    );

                    if (spielerArray[aktuellerSpieler].equals(pClientIP)) {
                        for (int j = 1; j < spielerArray.length; j++) {
                            if (j != aktuellerSpieler) {
                                send(
                                        spielerArray[j].getIP(),
                                        spielerArray[j].getPort(),
                                        "SET " + id + ',' + x + ',' + y
                                );
                            }
                        }

                        if (listener != null && aktuellerSpieler != 0) {
                            listener.onFieldSet(id, x, y);
                        }
                    }

                    aktuellerSpieler++;
                    if (aktuellerSpieler >= spielerArray.length) {
                        aktuellerSpieler = 0;
                        if (listener != null) {
                            listener.onYourTurn();
                        }
                    } else {
                        NetzwerkSpieler next = spielerArray[aktuellerSpieler];
                        send(
                                next.getIP(),
                                next.getPort(),
                                "GO"
                        );
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
        if (spielerList.find(new NetzwerkSpieler(pClientIP))) {
            if (spielGestartet) {
                NetzwerkSpieler[] old = spielerArray;
                spielerArray = new NetzwerkSpieler[old.length - 1];
                int place = spielerArray.length;
                int i     = 0;
                for (NetzwerkSpieler spieler : old) {
                    if (!spieler.equals(pClientIP)) {
                        spielerArray[i] = spieler;
                        i++;
                    } else {
                        place = i;
                    }
                }

                if (place == aktuellerSpieler) {
                    NetzwerkSpieler next = spielerArray[aktuellerSpieler];
                    send(
                            next.getIP(),
                            next.getPort(),
                            "GO"
                    );
                }
            } else {
                spielerList.toFirst();
                boolean removed = false;
                for (int id = 1; id <= spielerList.size(); spielerList.next()) {
                    if (removed) {
                        NetzwerkSpieler spieler = spielerList.getContent();
                        spielerList.remove();
                        spielerList.insertBefore(new NetzwerkSpieler(id, spieler.getIP(), spieler.getPort()));
                    } else if (spielerList.getContent().equals(pClientIP)) {
                        spielerList.remove();
                        removed = true;
                    }
                }
            }

            if (listener != null) {
                listener.onPlayerUnregister();
            }
        }
    }

    public void starteSpiel() {
        this.spielGestartet = true;

        spielerArray = new NetzwerkSpieler[spielerList.size()];
        spielerList.toFirst();

        for (int i = 0; i < spielerArray.length; i++, spielerList.next()) {
            NetzwerkSpieler spieler = spielerList.getContent();
            spielerArray[i] = spieler;

            if (!spieler.getIP().equals("localhost")) {
                send(spieler.getIP(), spieler.getPort(), "START " + spielerArray.length + ',' + spieler.getId());
            }
        }

        if (listener != null) {
            listener.onGameStarted(spielerArray.length, 1);
            listener.onYourTurn();
        }
    }

    public NetzwerkSpieler[] getSpieler() {
        if (spielGestartet) {
            return spielerArray;
        } else {
            return spielerList.fill(new NetzwerkSpieler[spielerList.size()]);
        }
    }

    public void setListener(NetzwerkListener listener) {
        this.listener = listener;
    }
}