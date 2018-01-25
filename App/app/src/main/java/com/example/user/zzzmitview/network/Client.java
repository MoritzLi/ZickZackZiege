package com.example.user.zzzmitview.network;

import java.io.IOException;
import java.net.Socket;

abstract class Client implements ClientReceiveListener {
    private Socket           socket;
    private ClientConnection connection;

    public Client(final String ip, final int port) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(
                            ip,
                            port
                    );

                    connection = new ClientConnection(socket, Client.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (connection == null)
            ;

        if (!socket.isConnected())
            throw new IOException("Socket is not connected");
    }

    public void send(String message) {
        connection.send(message);
    }

    public void close() {
        connection.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}