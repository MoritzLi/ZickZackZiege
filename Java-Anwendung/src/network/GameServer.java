package network;

import utility.List;
import utility.NetzwerkSpieler;

import java.io.IOException;

public class GameServer extends Server {
    public static final int port = 5453;

    private       boolean               spielGestartet;
    private final List<NetzwerkSpieler> spielerList;
    private       NetzwerkSpieler[]     spielerArray;

    private int aktuellerSpieler;

    private NetzwerkListener listener;

    public GameServer() throws IOException {
        super(port);

        spielGestartet = false;

        NetzwerkSpieler ich = new NetzwerkSpieler(1, "localhost", port);
        ich.setName("Du");
        spielerList = new List<>(ich);
    }

    @Override
    public void received(String message, String ip, int port) {
        int i1 = message.indexOf(' ');
        int i2 = message.indexOf(',');

        String befehl = (i1 > 0 ? message.substring(0, i1) : message).toUpperCase();

        switch (befehl) {
            case "REGISTER":
                if (!spielGestartet && spielerList.size() < 8) {
                    if (!spielerList.find(new NetzwerkSpieler(ip))) {
                        NetzwerkSpieler s = new NetzwerkSpieler(spielerList.size() + 1, ip, port);
                        if (i1 > 0) {
                            s.setName(message.substring(i1 + 1));
                        }

                        spielerList.append(s);

                        if (listener != null) {
                            listener.onPlayersChanged();
                        }
                    } else {
                        send(ip, port, "-ERR es existiert bereits eine Registrierung für diese IP-Adresse");
                    }
                }
                break;

            case "SET":
                if (spielGestartet) {
                    if (spielerArray[aktuellerSpieler].equals(ip)) {
                        int id = spielerArray[aktuellerSpieler].getId();
                        int x = Integer.parseInt(
                                message.substring(i1 + 1, i2)
                        ), y = Integer.parseInt(
                                message.substring(i2 + 1)
                        );

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

                        aktuellerSpieler++;
                        if (aktuellerSpieler == spielerArray.length) {
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
                }
                break;

            default:
                send(ip, port, "-ERR nicht Teil des Protokolls");
                break;
        }
    }

    @Override
    public void closed(String ip, int port) {
        super.closed(ip, port);
        if (spielerList.find(new NetzwerkSpieler(ip))) {
            if (spielGestartet) {
                NetzwerkSpieler[] old = spielerArray;
                spielerArray = new NetzwerkSpieler[old.length - 1];
                int place = spielerArray.length;
                int i     = 0;
                for (NetzwerkSpieler spieler : old) {
                    if (!spieler.equals(ip)) {
                        spielerArray[i] = spieler;
                        i++;
                    } else {
                        place = i;
                    }
                }

                if (place == aktuellerSpieler) {
                    if (aktuellerSpieler == spielerArray.length) {
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
            } else {
                spielerList.toFirst();
                boolean removed = false;
                for (int id = 1; id <= spielerList.size(); ) {
                    if (removed) {
                        NetzwerkSpieler spieler = spielerList.getContent();
                        spielerList.remove();
                        spielerList.insertBefore(new NetzwerkSpieler(id, spieler.getIP(), spieler.getPort()));
                        id++;
                    } else if (spielerList.getContent().equals(ip)) {
                        spielerList.remove();
                        removed = true;
                    } else {
                        spielerList.next();
                        id++;
                    }
                }
            }

            if (listener != null) {
                listener.onPlayersChanged();
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