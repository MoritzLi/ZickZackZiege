package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import utility.Datenbank;

public class LoginGUI extends JFrame {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 40;
    private static final int paddingCenter = 30;

    public LoginGUI() {
        super("Anmelden");

        setLayout(null);

        setSize(290, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel lName = new JLabel("Nickname");
        lName.setBounds(paddingLeft, paddingTop - 25, buttonWidth, 25);
        add(lName);

        JTextField name = new JTextField();
        name.setBounds(paddingLeft, paddingTop, buttonWidth, buttonHeight);
        add(name);

        JLabel lPasswort = new JLabel("Passwort");
        lPasswort.setBounds(paddingLeft, paddingTop + buttonHeight + paddingCenter - 25, buttonWidth, 25);
        add(lPasswort);

        JTextField passwort = new JTextField();
        passwort.setBounds(paddingLeft, paddingTop + buttonHeight + paddingCenter, buttonWidth, buttonHeight);
        add(passwort);

        JButton login = new JButton("Anmelden");
        login.setBounds(paddingLeft, paddingTop + (buttonHeight + paddingCenter) * 2, buttonWidth, buttonHeight);
        login.addActionListener(e -> {
            String    nickname = name.getText();
            String    password = passwort.getText();
            Datenbank anmelden = new Datenbank();
            if (anmelden.anmelden(nickname, password)) {
                setVisible(false);
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
            }
        });
        add(login);

        JButton register = new JButton("Registrieren");
        register.setBounds(paddingLeft, paddingTop + (buttonHeight + paddingCenter) * 3, buttonWidth, buttonHeight);
        register.addActionListener(e -> {
            String    nickname = name.getText();
            String    password = passwort.getText();
            Datenbank anmelden = new Datenbank();
            if (anmelden.register(nickname, password)) {
                setVisible(false);
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
            }
        });
        add(register);

        setVisible(true);
    }

}
