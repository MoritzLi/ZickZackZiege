package com.example.user.zzzmitview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@SuppressWarnings("unused")
class Connection extends Thread {
    private       Socket         s;
    private       BufferedReader vomHost;
    private       PrintWriter    zumHost;
    private       String         serverName;
    private final int            port;

    public Connection(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
        connect();
    }

    Connection(Socket socket) {
        s = socket;
        port = s.getLocalPort();
        try {
            //Objekt zum Versenden von Nachrichten ueber den Socket erzeugen
            zumHost = new PrintWriter(s.getOutputStream(), true);
            //Objekt zum Empfangen von Nachrichten ueber das Socketobjekt erzeugen
            vomHost = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String connect() {
        try {
            s = new Socket(serverName, port);
            //Objekt zum Versenden von Nachrichten ueber den Socket erzeugen
            zumHost = new PrintWriter(s.getOutputStream(), true);
            //Objekt zum Empfangen von Nachrichten ueber das Socketobjekt erzeugen
            vomHost = new BufferedReader(new InputStreamReader(s.getInputStream()));
            return "Verbindung : " + s;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String receive() {
        try {
            return vomHost.readLine();
        } catch (IOException e) {
            System.out.println("Verbindung zu " + getRemoteIP() + " " + getLocalPort() + " ist unterbrochen");
        }
        return null;
    }

    public void send(String nachricht) {
        zumHost.println(nachricht);
        zumHost.flush();
    }

    @SuppressWarnings("unused")
    public boolean isConnected() {
        return s.isConnected();
    }

    boolean isClosed() {
        return s.isClosed();
    }


    public String getRemoteIP() {
        return "" + s.getInetAddress();
    }

    public String getLocalIP() {
        return "" + s.getLocalAddress();
    }

    public int getRemotePort() {
        return s.getPort();
    }

    private int getLocalPort() {
        return s.getLocalPort();
    }

    public void close() {
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket verbindungsSocket() {
        return s;
    }
}

