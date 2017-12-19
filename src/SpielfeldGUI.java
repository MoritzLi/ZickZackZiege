import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpielfeldGUI extends JFrame implements ActionListener {
    private static final int fieldSize = 60;
    private static final int padding = 30;

    private final Spielfeld spielfeld;
    private final JButton[][] buttons;

    public SpielfeldGUI(int spieler) {
        super("ZickZackZiege");

        setLayout(null);

        spielfeld = new Spielfeld(spieler);
        setSize(2 * padding + (3 + spieler) * fieldSize);

        buttons = new JButton[3 + spieler][3 + spieler];
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setOpaque(false);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBounds(padding + i * fieldSize, padding + j * fieldSize, fieldSize, fieldSize);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
    }

    private void setSize(int size) {
        setSize(size, 20 + size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == source) {
                    try {
                        spielfeld.setze(1, i, j);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    buttons[i][j].setText(String.valueOf(1));
                    buttons[i][j].setEnabled(false);
                    return;
                }
            }
        }
    }
}