package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import network.GameClient;
import network.GameServer;
import network.NetzwerkListener;
import utility.Spielfeld;

class NetzwerkSpielfeldGUI extends JFrame implements ActionListener {
    private static final int fieldSize = 60;
    private static final int padding   = 30;

    private final Spielfeld   spielfeld;
    private final JButton[][] buttons;

    private final int     myID;
    private       boolean go;

    private final GameServer server;
    private final GameClient client;

    NetzwerkSpielfeldGUI(int spielerCount, int myID, GameServer server, GameClient client) {
        super("ZickZackZiege");
        this.server = server;
        this.client = client;

        NetzwerkListener listener = new NetzwerkListener() {
            @Override
            public void onPlayersChanged() {

            }

            @Override
            public void onGameStarted(int spielerCount, int myID) {

            }

            @Override
            public void onFieldSet(int id, int x, int y) {
                spielfeld.setValue(id, x, y);
                spielfeld.nextRound();

                refreshUI();
            }

            @Override
            public void onYourTurn() {
                go = true;
            }
        };

        if (server == null) {
            client.setListener(listener);
        } else {
            server.setListener(listener);
        }

        setLayout(null);

        spielfeld = new Spielfeld(spielerCount);

        setSize(2 * padding + spielfeld.getFieldCount() * fieldSize);

        buttons = new JButton[spielfeld.getFieldCount()][spielfeld.getFieldCount()];
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[x].length; y++) {
                JButton b = new JButton();
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBounds(padding + x * fieldSize, padding + y * fieldSize, fieldSize, fieldSize);
                b.addActionListener(this);
                add(b);

                buttons[x][y] = b;
            }
        }

        this.myID = myID;

        setVisible(true);
    }

    private void setSize(int size) {
        setSize(size, 20 + size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (go) {
            Object source = e.getSource();
            for (int x = 0; x < buttons.length; x++) {
                for (int y = 0; y < buttons[x].length; y++) {
                    if (buttons[x][y] == source && buttons[x][y].isEnabled()) {
                        spielfeld.setValue(myID, x, y);
                        spielfeld.nextRound();

                        refreshUI();

                        if (server == null) {
                            go = false;
                            client.send("SET " + x + ',' + y);
                        } else {
                            go = false;
                            server.received("SET " + x + ',' + y, "localhost", GameServer.port);
                        }
                        return;
                    }
                }
            }
        }
    }

    private void refreshUI() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[x].length; y++) {
                int value = spielfeld.getValue(x, y);
                if (value != 0) {
                    buttons[x][y].setText(String.valueOf(value));
                    buttons[x][y].setEnabled(false);
                }
            }
        }
    }
}