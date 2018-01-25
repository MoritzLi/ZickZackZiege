package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import utility.ArtificialIntelligence;
import utility.Schwierigkeit;
import utility.Spielfeld;

public class SingleSpielfeldGUI extends JFrame implements ActionListener {
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

                    return;
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