package com.example.user.zzzmitview;

@SuppressWarnings("unused")
abstract class Client {
    //Objektbeziehungen
    private final Connection       hatVerbindung;
    private       Clientempfaenger hatEmpfaenger;

    class Clientempfaenger extends Thread {
        // Objekte
        private final Client     kenntClient;
        private final Connection kenntVerbindung;

        // Attribute
        private boolean zVerbindungAktiv;

        /**
         * Der ClientEmpfaenger hat den zugeh&ouml;rigen Client und die zugeh&ouml;rige Connection kennen gelernt.<br>
         *
         * @param pClient     zugeh&ouml;riger Client, der die einkommenden Nachrichten bearbeitet
         * @param pConnection zugeh&ouml;rige Connection, die die einkommenden Nachrichten empf�ngt
         */
        Clientempfaenger(Client pClient, Connection pConnection) {
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
        void gibFrei() {
            zVerbindungAktiv = false;
        }

    }

    Client(String pIPAdresse, int pPortNr) {
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
        return hatEmpfaenger != null && hatEmpfaenger.zVerbindungAktiv;
    }

    public String toString() {
        return "Verbindung mit Socket: " + hatVerbindung.verbindungsSocket();
    }

    protected abstract void processMessage(String pMessage);

    public void close() {
        if (hatEmpfaenger != null)
            hatEmpfaenger.gibFrei();
        hatEmpfaenger = null;
        hatVerbindung.close();
    }
}