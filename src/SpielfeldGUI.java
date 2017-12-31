import com.sun.istack.internal.Nullable;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SpielfeldGUI extends JFrame implements ActionListener {
    private static final int fieldSize = 60;
    private static final int padding   = 30;

    private final Spielfeld   spielfeld;
    private final JButton[][] buttons;

    private final int myID;

    private final GameServer server;
    private final GameClient client;

    SpielfeldGUI(int spieler, int myID, @Nullable GameServer server, @Nullable GameClient client) {
        super("ZickZackZiege");
        this.server = server;
        this.client = client;

        setLayout(null);

        spielfeld = new Spielfeld(spieler);
        setSize(2 * padding + (3 + spieler) * fieldSize);

        buttons = new JButton[3 + spieler][3 + spieler];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton b = new JButton();
                b.setOpaque(false);
                b.setContentAreaFilled(false);
                b.setBounds(padding + i * fieldSize, padding + j * fieldSize, fieldSize, fieldSize);
                b.addActionListener(this);
                add(b);

                buttons[i][j] = b;
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
        if (!go())
            return;

        Object source = e.getSource();
        for (int i = 0; i < buttons.length; i++)
            for (int j = 0; j < buttons[i].length; j++)
                if (buttons[i][j] == source && buttons[i][j].isEnabled()) {
                    setze(myID, i, j);
                    if (server == null) {
                        client.go = false;
                        client.send("SET " + i + ',' + j);
                    } else {
                        server.go = false;
                        server.processMessage("localhost", GameServer.port, "SET " + i + ',' + j);
                    }
                    return;
                }
    }

    void setze(int id, int x, int y) {
        try {
            spielfeld.setze(id, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buttons[x][y].setText(String.valueOf(id));
        buttons[x][y].setEnabled(false);
    }

    private boolean go() {
        return server == null ? client.go : server.go;
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
    }
}