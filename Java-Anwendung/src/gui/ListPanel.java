package gui;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

class ListPanel<Type> extends JPanel {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 80;
    private static final int paddingCenter = 45;

    private final JList<Type> list;

    private final JButton button;

    ListPanel(int width, int height) {
        setLayout(null);
        setSize(width, height);

        list = new JList<>();
        list.setBounds(paddingLeft, paddingTop / 4, buttonWidth, (int) (buttonWidth * 1.5));
        add(list);

        button = new JButton();
        button.setBounds(paddingLeft, paddingTop / 4 + paddingCenter + list.getHeight(), buttonWidth, buttonHeight);
        button.setText("OK");
        add(button);
    }

    JButton getButton() {
        return button;
    }

    void setListData(Type[] data) {
        list.setListData(data);
    }
}
