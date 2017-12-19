public abstract class Client {
    //Objektbeziehungen
    private Connection hatVerbindung;
    private Clientempfaenger hatEmpfaenger;

    class Clientempfaenger extends Thread {
        // Objekte
        private Client kenntClient;
        private Connection kenntVerbindung;

        // Attribute
        private boolean zVerbindungAktiv;

        /**
         * Der ClientEmpfaenger hat den zugeh&ouml;rigen Client und die zugeh&ouml;rige Connection kennen gelernt.<br>
         *
         * @param pClient     zugeh&ouml;riger Client, der die einkommenden Nachrichten bearbeitet
         * @param pConnection zugeh&ouml;rige Connection, die die einkommenden Nachrichten empf�ngt
         */
        public Clientempfaenger(Client pClient, Connection pConnection) {
            kenntClient = pClient;
            kenntVerbindung = pConnection;
            zVerbindungAktiv = true;
        }

        /**
         * Solange der Server Nachrichten sendete, wurden diese empfangen und an die ClientVerbinedung weitergereicht.
         */
        public void run() {
            String lNachricht;
            boolean lNachrichtEmpfangen = true;

            do
                if (zVerbindungAktiv) {
                    lNachricht = kenntVerbindung.receive();
                    lNachrichtEmpfangen = (lNachricht != null);
                    if (lNachrichtEmpfangen)
                        kenntClient.processMessage(lNachricht);
                }
            while (zVerbindungAktiv && lNachrichtEmpfangen);
        }

        /**
         * Der ClientEmpfaenger arbeitet nicht mehr
         */
        public void gibFrei() {
            zVerbindungAktiv = false;
        }

    }

    public Client(String pIPAdresse, int pPortNr) {
        hatVerbindung = new Connection(pIPAdresse, pPortNr);

        try {
            hatEmpfaenger = new Clientempfaenger(this, hatVerbindung);
            hatEmpfaenger.start();
        } catch (Exception pFehler) {
            System.err.println("Fehler beim \u00D6ffnen des Clients: " + pFehler);
        }

    }

    public void send(String pMessage) {
        hatVerbindung.send(pMessage);
    }

    public boolean istVerbunden() {
        if (hatEmpfaenger != null)
            return hatEmpfaenger.zVerbindungAktiv;
        else
            return false;
    }

    public String toString() {
        return "Verbindung mit Socket: " + hatVerbindung.verbindungsSocket();
    }

    public abstract void processMessage(String pMessage);

    public void close() {
        if (hatEmpfaenger != null)
            hatEmpfaenger.gibFrei();
        hatEmpfaenger = null;
        hatVerbindung.close();
    }
}