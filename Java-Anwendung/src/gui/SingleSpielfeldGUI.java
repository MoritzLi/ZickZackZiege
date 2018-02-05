package gui;

import utility.ArtificialIntelligence;
import utility.Datenbank;
import utility.Schwierigkeit;
import utility.Spielfeld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static utility.Datenbank.username;

class SingleSpielfeldGUI extends JFrame implements ActionListener {
    static Color[] colors = {
            new Color(0, 0, 0, 0),
            new Color(183, 28, 28),
            new Color(13, 71, 161),
            new Color(255, 214, 0),
            new Color(93, 182, 30),
            new Color(255, 87, 34),
            new Color(156, 39, 176),
            new Color(0, 191, 165),
            new Color(255, 64, 129)
    };

    private static final int fieldSize = 60;
    private static final int padding   = 30;

    private final Spielfeld   spielfeld;
    private final JButton[][] buttons;

    private final ArtificialIntelligence ai;

    SingleSpielfeldGUI(Schwierigkeit schwierigkeit) {
        super("ZickZackZiege");

        setLayout(null);

        spielfeld = new Spielfeld(2);
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

        setVisible(true);

        ai = new ArtificialIntelligence(spielfeld, schwierigkeit);

        if (schwierigkeit == Schwierigkeit.SCHWIERIG) {
            ai.spielzug();
            spielfeld.nextRound();

            refreshUI();
        }
    }

    private void setSize(int size) {
        setSize(size, 20 + size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[x].length; y++) {
                if (buttons[x][y] == source && buttons[x][y].isEnabled()) {
                    spielfeld.setValue(ArtificialIntelligence.playerID, x, y);
                    spielfeld.nextRound();

                    ai.spielzug();
                    spielfeld.nextRound();

                    refreshUI();

                    if (!spielfeld.isPlaying()) {
                        String users = username;
                        int spielerpunkte = spielfeld.getPoints(ArtificialIntelligence.playerID);
                        int botpunkte = spielfeld.getPoints(ArtificialIntelligence.kiID);
                        Datenbank hochladen = new Datenbank();
                        hochladen.botSpiel(users, spielerpunkte, botpunkte);
                    }


                    return;
                }
            }
        }
    }

    private void refreshUI() {
        for (int x = 0; x < buttons.length; x++) {
            for (int y = 0; y < buttons[x].length; y++) {
                int value = spielfeld.getValue(x, y);

                buttons[x][y].setBackground(colors[value]);
                buttons[x][y].setContentAreaFilled(value != 0);
                buttons[x][y].setEnabled(value == 0);
            }
        }
    }
}