package com.example.user.zzzmitview.network;

import com.example.user.zzzmitview.utility.List;
import com.example.user.zzzmitview.utility.NetzwerkSpieler;

public class GameServer extends Server {
    public static final int port = 5453;

    private       boolean               spielGestartet;
    private final List<NetzwerkSpieler> spielerList;
    private       NetzwerkSpieler[]     spielerArray;

    private NetzwerkListener listener;

    public GameServer() {
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
                        NetzwerkSpieler next = spielerList.getContent();
                        send(
                                next.getIP(),
                                next.getPort(),
                                "GO"
                        );
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
                                    NetzwerkSpieler next = spielerArray[i + 1];
                                    send(
                                            next.getIP(),
                                            next.getPort(),
                                            "GO"
                                    );
                                } else {
                                }

                                if (listener != null) {
                                    listener.onFieldSet(id, x, y);
                                }
                                break;
                            }
                        }
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
            if (spielerList.find(new NetzwerkSpieler(-1, pClientIP, pClientPort))) {
                spielerList.remove();
            }
        }
    }

    public void starteSpiel() {
        this.spielGestartet = true;
        spielerArray = new NetzwerkSpieler[spielerList.size() + 1];
        spielerList.toFirst();

        for (int i = 1; i < spielerArray.length; i++, spielerList.next()) {
            NetzwerkSpieler old = spielerList.getContent();
            spielerArray[i] = new NetzwerkSpieler(i + 1, old.getIP(), old.getPort());
            spielerArray[i].setName(old.getName());
            send(old.getIP(), old.getPort(), "START " + spielerArray.length + ',' + (i + 1));
        }

        spielerArray[0] = new NetzwerkSpieler(1, "localhost", port);

        if (listener != null) {
            listener.onGameStarted(spielerArray.length, 1);
            listener.onYourTurn();
        }
    }

    public NetzwerkSpieler[] getSpieler() {
        return spielerList.fill(new NetzwerkSpieler[spielerList.size()]);
    }

    public void setListener(NetzwerkListener listener) {
        this.listener = listener;
    }
}