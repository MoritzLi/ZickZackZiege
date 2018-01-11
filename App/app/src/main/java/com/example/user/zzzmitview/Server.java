package com.example.user.zzzmitview;

import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class Server {
    // Objekte
    private ServerSocket serverSocket;
    private List<ServerConnection> verbindungen;
    private ServerSchleife schleife;

    private class ServerConnection extends Connection {
        // Objekte
        final Server server;

        public ServerConnection(Socket pSocket, Server pServer) {
            super(pSocket);
            server = pServer;
        }

        /**
         * Solange der Client Nachrichten sendete, wurden diese empfangen und an die Server weitergereicht.<br>
         * Abgebrochene Verbindungen wurden erkannt.
         */
        public void run() {
            String lNachricht;

            while (!this.isClosed()) {
                lNachricht = this.receive();
                if (lNachricht == null) {
                    if (!this.isClosed()) {
                        server.closeConnection(this.getRemoteIP(), this.getRemotePort());
                    }
                } else
                    server.processMessage(this.getRemoteIP(), this.getRemotePort(), lNachricht);
            }
        }

    }

    private class ServerSchleife extends Thread {
        private final Server server;

        public ServerSchleife(Server pServer) {
            server = pServer;
        }

        public void run() {
            while (isAlive()) {
                try {
                    Socket lClientSocket = server.serverSocket.accept();
                    ServerConnection lNeueSerververbindung = new ServerConnection(lClientSocket, server);
                    server.ergaenzeVerbindung(lNeueSerververbindung);
                    lNeueSerververbindung.start();
                } catch (Exception pFehler) {
                    System.err.println("Fehler beim Erwarten einer Verbindung in Server: ");
                    pFehler.printStackTrace();
                }
            }
        }
    }

    public Server(int pPortNr) {
        try {
            serverSocket = new ServerSocket(pPortNr);
            verbindungen = new List<>();
            schleife = new ServerSchleife(this);
            schleife.start();
        } catch (Exception pFehler) {
            System.err.println("Fehler beim öffnen der Server: " + pFehler);
        }
    }

    @Override
    public String toString() {
        return "Server von ServerSocket: " + serverSocket;
    }

    private void ergaenzeVerbindung(ServerConnection pVerbindung) {
        verbindungen.append(pVerbindung);
        this.processNewConnection(pVerbindung.getRemoteIP(), pVerbindung.getRemotePort());
    }

    private ServerConnection SerververbindungVonIPUndPort(String pClientIP, int pClientPort) {
        ServerConnection lSerververbindung;

        verbindungen.toFirst();

        while (verbindungen.hasAccess()) {
            lSerververbindung = verbindungen.getContent();
            if (lSerververbindung.getRemoteIP().equals(pClientIP) && lSerververbindung.getRemotePort() == pClientPort)
                return lSerververbindung;
            verbindungen.next();
        }

        return null; // IP nicht gefunden
    }

    public void send(String pClientIP, int pClientPort, String pMessage) {
        ServerConnection lSerververbindung = this.SerververbindungVonIPUndPort(pClientIP, pClientPort);
        if (lSerververbindung != null)
            lSerververbindung.send(pMessage);
        else
            System.err.println("Fehler beim Senden: IP " + pClientIP + " mit Port " + pClientPort + " nicht vorhanden.");
    }

    public void sendToAll(String pMessage) {
        ServerConnection lSerververbindung;
        verbindungen.toFirst();
        while (verbindungen.hasAccess()) {
            lSerververbindung = verbindungen.getContent();
            lSerververbindung.send(pMessage);
            verbindungen.next();
        }
    }

    public void closeConnection(String pClientIP, int pClientPort) {
        ServerConnection lSerververbindung = this.SerververbindungVonIPUndPort(pClientIP, pClientPort);
        if (lSerververbindung != null) {
            this.processClosedConnection(pClientIP, pClientPort);
            lSerververbindung.close();
            this.loescheVerbindung(lSerververbindung);
        } else
            System.err.println("Fehler beim Schließen der Verbindung: IP " + pClientIP + " mit Port " + pClientPort + " nicht vorhanden.");

    }

    private void loescheVerbindung(ServerConnection pVerbindung) {
        verbindungen.toFirst();
        while (verbindungen.hasAccess()) {
            ServerConnection lClient = verbindungen.getContent();
            if (lClient == pVerbindung)
                verbindungen.remove();
            verbindungen.next();
        }
    }

    public abstract void processNewConnection(String pClientIP, int pClientPort);

    public abstract void processMessage(String pClientIP, int pClientPort, String pMessage);

    public abstract void processClosedConnection(String pClientIP, int pClientPort);

    public void close() {
        try {
            serverSocket.close();
            serverSocket = null;
        } catch (Exception pFehler) {
            System.err.println("Fehler beim Schließen des Servers: " + pFehler);
        }
    }
}