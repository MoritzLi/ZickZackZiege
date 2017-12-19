public class GameClient extends Client {
    private boolean spielGestartet;

    public GameClient(String pIPAdresse) {
        super(pIPAdresse, GameServer.port);
        spielGestartet = false;

        send("REGISTER");
    }

    @Override
    public void processMessage(String pMessage) {
        String befehl = pMessage.contains(" ") ? pMessage.substring(0, pMessage.indexOf(' ')) : pMessage;
        switch (befehl) {
            case "START":
                spielGestartet = true;
                break;
            case "SET":
                if (spielGestartet) {
                    //TODO GUI-Integration
                }
                break;
            default:
                send("-ERR nicht Teil des Protokolls");
                break;
        }
    }
}