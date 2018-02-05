package gui;

import javax.swing.*;
import java.awt.event.ActionListener;

class TextInputPanel extends JPanel {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 80;
    private static final int paddingCenter = 45;

    private final JTextField textField;

    private final JButton button;

    private final JButton back;

    TextInputPanel(int width, int height) {
        setLayout(null);
        setSize(width, height);

        back = new JButton("Zurück");
        back.setBounds(10, 10, buttonWidth / 2, buttonHeight / 2);
        add(back);

        textField = new JTextField();
        textField.setBounds(paddingLeft, paddingTop, buttonWidth, buttonHeight);
        add(textField);

        button = new JButton();
        button.setBounds(paddingLeft, paddingTop + buttonHeight + paddingCenter, buttonWidth, buttonHeight);
        button.setText("OK");
        add(button);
    }

    JButton getButton() {
        return button;
    }

    String getTextInput() {
        return textField.getText();
    }

    void addBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }
}