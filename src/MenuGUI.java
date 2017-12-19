import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
class MenuGUI extends JFrame {
    private static final int buttonHeight = 75;
    private static final int buttonWidth = 200;

    private final JPanel panelMenu;
    private final JPanel panelSettings;
    private final JPanel panelStart;

    private final JButton buttonNeuesSpiel;
    private final JButton buttonEinstellungen;
    private final JButton buttonSingleplayer;
    private final JButton buttonServerErstellen;
    private final JButton buttonServerBeitreten;
    private final JButton buttonSpielStarten;

    private final JFrame ipInput;
    private final JTextField textFieldIp;
    private final JButton buttonIpInput;

    private String ipAdresse;

    MenuGUI() {
        super("ZickZackZiege");

        setSize(290, 500);
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
        buttonNeuesSpiel.setBounds(40, 80, buttonWidth, buttonHeight);
        buttonNeuesSpiel.setText("Neues Spiel");
        buttonNeuesSpiel.addActionListener(e -> setContentPane(panelStart));
        buttonEinstellungen = new JButton();
        buttonEinstellungen.setBounds(40, 200, buttonWidth, buttonHeight);
        buttonEinstellungen.setText("Einstellungen");
        buttonEinstellungen.addActionListener(e -> setContentPane(panelSettings));

        panelMenu.add(buttonNeuesSpiel);
        panelMenu.add(buttonEinstellungen);

        ipInput = new JFrame("Server beitreten");
        ipInput.setSize(buttonWidth, 150);
        ipInput.setLayout(null);

        textFieldIp = new JTextField();
        textFieldIp.setBounds(20, 20, 150, 35);

        buttonIpInput = new JButton();
        buttonIpInput.setBounds(65, 70, 60, 25);
        buttonIpInput.setText("OK");
        buttonIpInput.addActionListener(e1 -> {
            ipAdresse = textFieldIp.getText();
            ipInput.setVisible(false);
            System.out.println(ipAdresse);
        });

        ipInput.add(textFieldIp);
        ipInput.add(buttonIpInput);

        buttonSingleplayer = new JButton();
        buttonSingleplayer.setBounds(40, 80, buttonWidth, buttonHeight);
        buttonSingleplayer.setText("Singleplayer");
        buttonSingleplayer.addActionListener(e -> System.out.println("TODO"));
        buttonServerBeitreten = new JButton();
        buttonServerBeitreten.setBounds(40, 200, buttonWidth, buttonHeight);
        buttonServerBeitreten.setText("Netzwerkspiel beitreten");
        buttonServerBeitreten.addActionListener(e -> {
            ipInput.setVisible(true);
        });
        buttonServerErstellen = new JButton();
        buttonServerErstellen.setBounds(40, 320, buttonWidth, buttonHeight);
        buttonServerErstellen.setText("Neues Spiel erstellen");

        panelStart.add(buttonSingleplayer);
        panelStart.add(buttonServerBeitreten);
        panelStart.add(buttonServerErstellen);

        buttonSpielStarten = new JButton();

        setContentPane(panelMenu);
    }
}