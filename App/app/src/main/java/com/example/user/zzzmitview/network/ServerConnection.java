package com.example.user.zzzmitview.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ServerConnection {
    private final Socket socket;

    private final Receiver receiver;

    private final BufferedWriter writer;
    private final BufferedReader reader;

    private final ServerReceiveListener listener;

    private final String ip;
    private final int port;

    ServerConnection(Socket socket, ServerReceiveListener listener) throws IOException {
        this.socket = socket;

        this.reader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );

        this.writer = new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()
                )
        );

        this.receiver = new Receiver();
        receiver.start();

        this.listener = listener;

        this.ip = socket.getInetAddress().toString();
        this.port = socket.getPort();
    }

    void send(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getIP() {
        return ip;
    }

    int getPort() {
        return port;
    }

    private class Receiver extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        break;
                    }
                    listener.received(message, socket.getInetAddress().toString(), socket.getPort());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            listener.closed(ip, port);
        }
    }
}