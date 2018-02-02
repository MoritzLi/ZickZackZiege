package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import utility.Spieler;
import utility.Spielfeld;

import static gui.SingleSpielfeldGUI.colors;

class MultiSpielfeldGUI extends JFrame implements ActionListener {
    private static final int fieldSize = 60;
    private static final int padding   = 30;

    private final Spielfeld   spielfeld;
    private final Spieler[]   spieler;
    private final JButton[][] buttons;

    private int current;

    MultiSpielfeldGUI(int spielerCount) {
        super("ZickZackZiege");

        setLayout(null);

        spieler = new Spieler[spielerCount];
        for (int i = 0; i < spielerCount; i++) {
            spieler[i] = new Spieler(i + 1);
        }

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

        current = 0;

        setVisible(true);
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
                    spielfeld.setValue(spieler[current].getId(), x, y);
                    spielfeld.nextRound();

                    current++;
                    if (current == spieler.length) {
                        current = 0;
                    }

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

                buttons[x][y].setBackground(colors[value]);
                buttons[x][y].setContentAreaFilled(value != 0);
                buttons[x][y].setEnabled(value == 0);
            }
        }
    }
}