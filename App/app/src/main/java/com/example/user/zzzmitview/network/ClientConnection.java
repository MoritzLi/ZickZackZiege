package com.example.user.zzzmitview.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class ClientConnection {
    private final Socket socket;

    private final Receiver receiver;

    private BufferedWriter writer;
    private BufferedReader reader;

    private final ClientReceiveListener listener;

    ClientConnection(Socket socket, ClientReceiveListener listener) throws IOException {
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

    private class Receiver extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();
                    if (message != null) {
                        listener.received(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}