public class GameServer extends Server {
    public static final int port = 5453;
    private boolean spielGestartet;
    private List<Spieler> spieler;

    public GameServer() {
        super(port);
        spielGestartet = false;
        spieler = new List<>();
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        String befehl = pMessage.contains(" ") ? pMessage.substring(0, pMessage.indexOf(' ')) : pMessage;
        switch (befehl) {
            case "REGISTER":
                Spieler neuerSpieler = new Spieler(-1, pClientIP, pClientPort);
                if (!spieler.find(neuerSpieler)) {
                    spieler.append(neuerSpieler);
                } else {
                    send(pClientIP, pClientPort, "-ERR es existiert bereits eine Registrierung für diese IP-Adresse");
                }
                break;
            case "SET":
                if (spieler.find(new Spieler(-1, pClientIP, pClientPort))) {
                    int id = spieler.getContent().getId();
                    //TODO GUI-Integration
                } else {
                    send(pClientIP, pClientPort, "-ERR bitte warte bis zum nächsten Spiel");
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
            if (spieler.find(new Spieler(-1, pClientIP, pClientPort))) {
                spieler.remove();
            }
        }
    }

    public void setSpielGestartet(boolean spielGestartet) {
        this.spielGestartet = spielGestartet;
    }
}