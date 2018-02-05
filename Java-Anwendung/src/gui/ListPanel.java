package gui;

import javax.swing.*;
import java.awt.event.ActionListener;

class ListPanel<Type> extends JPanel {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 80;
    private static final int paddingCenter = 45;

    private final JList<Type> list;

    private final JButton button;

    private final JButton back;

    ListPanel(int width, int height) {
        setLayout(null);
        setSize(width, height);

        back = new JButton("Zurück");
        back.setBounds(10, 10, buttonWidth / 2, buttonHeight / 2);
        add(back);

        list = new JList<>();
        list.setBounds(paddingLeft, 3 * paddingTop / 4, buttonWidth, 250);
        add(list);

        button = new JButton();
        button.setBounds(paddingLeft, list.getY() + paddingCenter + list.getHeight(), buttonWidth, buttonHeight);
        button.setText("OK");
        add(button);
    }

    JButton getButton() {
        return button;
    }

    void setListData(Type[] data) {
        list.setListData(data);
    }

    void addBackListener(ActionListener listener) {
        back.addActionListener(listener);
    }
}
