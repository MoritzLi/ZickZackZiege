import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
class MenuGUI extends JFrame {
    private final JPanel panelMenu;
    private final JPanel panelSettings;
    private final JPanel panelStart;

    private final JButton buttonNeuesSpiel;
    private final JButton buttonEinstellungen;
    private final JButton buttonSingleplayer;
    private final JButton buttonServerErstellen;
    private final JButton buttonServerBeitreten;
    private final JButton buttonSpielStarten;

    private String ipAdresse;

    MenuGUI() {
        super("ZickZackZiege");

        setSize(400, 800);
        setLayout(null);

        panelMenu = new JPanel();
        panelMenu.setSize(getWidth(), getHeight());
        panelMenu.setLayout(null);

        panelSettings = new JPanel();
        panelSettings.setSize(getWidth(), getHeight());
        panelSettings.setLayout(null);

        panelStart = new JPanel();
        panelStart.setSize(getWidth(), getHeight());
        panelStart.setLayout(null);

        buttonNeuesSpiel = new JButton();
        buttonNeuesSpiel.setBounds(40, 80, 200, 75);
        buttonNeuesSpiel.setText("Neues Spiel");
        buttonNeuesSpiel.addActionListener(e -> setContentPane(panelStart));
        buttonEinstellungen = new JButton();
        buttonEinstellungen.setBounds(40, 200, 200, 75);
        buttonEinstellungen.setText("Einstellungen");
        buttonEinstellungen.addActionListener(e -> setContentPane(panelSettings));

        panelMenu.add(buttonNeuesSpiel);
        panelMenu.add(buttonEinstellungen);

        buttonSingleplayer = new JButton();
        buttonSingleplayer.setBounds(40, 80, 200, 75);
        buttonSingleplayer.setText("Singleplayer");
        buttonSingleplayer.addActionListener(e -> System.out.println("TODO"));
        buttonServerBeitreten = new JButton();
        buttonServerBeitreten.setBounds(40, 200, 200, 75);
        buttonServerBeitreten.setText("Netzwerkspiel beitreten");
        buttonServerBeitreten.addActionListener(e -> {
            JFrame input = new JFrame("Server beitreten");
            input.setSize(200, 150);
            input.setLayout(null);
            JTextField ip = new JTextField();
            ip.setBounds(20, 20, 150, 35);
            JButton ok = new JButton();
            ok.setBounds(65, 70, 60, 25);
            ok.setText("OK");
            ok.addActionListener(e1 -> {
                ipAdresse = ip.getText();
                input.setVisible(false);
                System.out.println(ipAdresse);
            });
            input.add(ip);
            input.add(ok);
            input.setVisible(true);
        });
        buttonServerErstellen = new JButton();
        buttonServerErstellen.setBounds(40, 320, 200, 75);
        buttonServerErstellen.setText("Neues Spiel erstellen");

        panelStart.add(buttonSingleplayer);
        panelStart.add(buttonServerBeitreten);
        panelStart.add(buttonServerErstellen);

        buttonSpielStarten = new JButton();

        setContentPane(panelMenu);
    }
}