import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextInputPanel extends JPanel {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 80;
    private static final int paddingCenter = 45;

    private final JTextField textField;

    private final JButton button;

    public TextInputPanel(int width, int height) {
        setLayout(null);
        setSize(width, height);

        textField = new JTextField();
        textField.setBounds(paddingLeft, paddingTop, buttonWidth, buttonHeight);
        add(textField);

        button = new JButton();
        button.setBounds(paddingLeft, paddingTop + buttonHeight + paddingCenter, buttonWidth, buttonHeight);
        button.setText("OK");
        add(button);
    }

    public JButton getButton() {
        return button;
    }

    public String getTextInput() {
        return textField.getText();
    }
}