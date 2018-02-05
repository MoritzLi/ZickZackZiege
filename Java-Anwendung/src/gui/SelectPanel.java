package gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

class SelectPanel extends JPanel {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 80;
    private static final int paddingCenter = 45;

    private final JButton button1;
    private final JButton button2;
    private final JButton button3;

    private final JButton back;

    SelectPanel(int buttonCount, int width, int height) {
        setLayout(null);
        setSize(width, height);

        back = new JButton("Zurück");
        back.setBounds(10, 10, buttonWidth / 2, buttonHeight / 2);
        add(back);

        button1 = new JButton();
        button1.setBounds(paddingLeft, paddingTop, buttonWidth, buttonHeight);
        if (buttonCount >= 1)
            add(button1);
        button2 = new JButton();
        button2.setBounds(paddingLeft, paddingTop + buttonHeight + paddingCenter, buttonWidth, buttonHeight);
        if (buttonCount >= 2)
            add(button2);
        button3 = new JButton();
        button3.setBounds(paddingLeft, paddingTop + (buttonHeight + paddingCenter) * 2, buttonWidth, buttonHeight);
        if (buttonCount >= 3)
            add(button3);
    }

    JButton getButton1() {
        return button1;
    }

    JButton getButton2() {
        return button2;
    }

    JButton getButton3() {
        return button3;
    }

    void addBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }

    void hideBack() {
        back.setVisible(false);
    }
}