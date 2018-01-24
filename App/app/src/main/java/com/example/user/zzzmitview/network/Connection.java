package com.example.user.zzzmitview.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Connection extends Thread {
    private       Socket         socket;
    private       BufferedReader vomHost;
    private       PrintWriter    zumHost;
    private       String         serverName;
    private final int            port;

    public Connection(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;

        connect();

        new Thread(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }).start();
    }

    Connection(Socket socket) {
        this.socket = socket;
        port = this.socket.getLocalPort();
        try {
            //Objekt zum Versenden von Nachrichten ueber den Socket erzeugen
            zumHost = new PrintWriter(this.socket.getOutputStream(), true);
            //Objekt zum Empfangen von Nachrichten ueber das Socketobjekt erzeugen
            vomHost = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void connect() {
        try {
            socket = new Socket(serverName, port);
            //Objekt zum Versenden von Nachrichten ueber den Socket erzeugen
            zumHost = new PrintWriter(socket.getOutputStream(), true);
            //Objekt zum Empfangen von Nachrichten ueber das Socketobjekt erzeugen
            vomHost = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
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
        return socket.isConnected();
    }

    boolean isClosed() {
        return socket.isClosed();
    }


    public String getRemoteIP() {
        return "" + socket.getInetAddress();
    }

    public String getLocalIP() {
        return "" + socket.getLocalAddress();
    }

    public int getRemotePort() {
        return socket.getPort();
    }

    private int getLocalPort() {
        return socket.getLocalPort();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket verbindungsSocket() {
        return socket;
    }
}

