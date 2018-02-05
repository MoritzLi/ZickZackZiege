package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Anmelden extends JFrame {
    private JLabel lEingabe;
    private JLabel lEingabe2;
    private JTextField tEingabe;
    private JButton erzeugeEcho;


    public Anmelden() {
        super("");

        setLayout(null);

        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lEingabe = new JLabel("Eingabe");
        lEingabe.setBounds(10, 10, 100, 20);
        setVisible(true);
        add(lEingabe);

        tEingabe = new JTextField();
        tEingabe.setBounds(10, 40, 100, 20);
        add(tEingabe);

        lEingabe2 = new JLabel("Echo");
        lEingabe2.setBounds(150, 10, 100, 20);
        add(lEingabe2);

        erzeugeEcho = new JButton("Echo bitte");
        erzeugeEcho.setBounds(10, 70, 100, 20);
        add(erzeugeEcho);


        setVisible(true);
    }

}
