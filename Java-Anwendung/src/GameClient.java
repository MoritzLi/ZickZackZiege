public class GameClient extends Client {
    private boolean      spielGestartet;
    private SpielfeldGUI spielfeldGUI;
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
                    spielfeldGUI.setze(
                            Integer.parseInt(
                                    pMessage.substring(i1 + 1, i2)
                            ),
                            Integer.parseInt(
                                    pMessage.substring(i2 + 1, i3)
                            ),
                            Integer.parseInt(
                                    pMessage.substring(i3 + 1)
                            )
                    );
                }
                break;

            case "START":
                spielGestartet = true;

                int i1 = pMessage.indexOf(' ');
                int i2 = pMessage.indexOf(',');

                spielfeldGUI = new SpielfeldGUI(
                        Integer.parseInt(
                                pMessage.substring(i1 + 1, i2)
                        ),
                        Integer.parseInt(
                                pMessage.substring(i2 + 1)
                        ),
                        null,
                        this);
                spielfeldGUI.setVisible(true);
                break;

            case "GO":
                go = true;

            default:
                send("-ERR nicht Teil des Protokolls");
                break;
        }
    }
}