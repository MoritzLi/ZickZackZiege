package com.example.user.zzzmitview.network;

import com.example.user.zzzmitview.utility.List;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server implements ServerReceiveListener {
    private ServerSocket           serverSocket;
    private List<ServerConnection> connections;

    private Connector connector;

    Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        connections = new List<>();

        connector = new Connector();
        connector.start();
    }

    private ServerConnection getConnection(String ip, int port) {
        for (ServerConnection connection : connections) {
            if (ip.equals(connection.getIP()) && port == connection.getPort()) {
                return connection;
            }
        }

        return null;
    }

    public void send(String ip, int port, String message) {
        ServerConnection connection = getConnection(ip, port);
        if (connection != null) {
            connection.send(message);
        } else {
            System.err.println("Fehler beim Senden: IP " + ip + " mit Port " + port + " nicht vorhanden.");
        }
    }

    @Override
    public void closed(String ip, int port) {
        ServerConnection connection = getConnection(ip, port);
        if (connection != null) {
            connections.remove();
        }
    }

    public void close() {
        for (ServerConnection connection : connections) {
            connection.close();
        }

        connector.interrupt();

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Connector extends Thread {
        @Override
        public void run() {
            while (!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    connections.append(new ServerConnection(clientSocket, Server.this));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}