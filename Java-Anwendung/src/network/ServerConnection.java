package network;

import java.io.*;
import java.net.Socket;

class ServerConnection {
    private final Socket socket;

    private final Receiver receiver;

    private final BufferedWriter writer;
    private final BufferedReader reader;

    private final ServerReceiveListener listener;

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
        return socket.getInetAddress().toString();
    }

    int getPort() {
        return socket.getPort();
    }

    private class Receiver extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();
                    if (message != null) {
                        listener.received(message, socket.getInetAddress().toString(), socket.getPort());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                listener.closed(socket.getInetAddress().toString(), socket.getPort());
            }
        }
    }
}