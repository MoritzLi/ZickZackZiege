package com.example.user.zzzmitview.network;

import java.io.IOException;
import java.net.Socket;

abstract class Client implements ClientReceiveListener {
    private final Socket           socket;
    private final ClientConnection connection;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(
                ip,
                port
        );

        connection = new ClientConnection(socket, this);
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