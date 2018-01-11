import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
class MenuGUI extends JFrame {
    private static final int buttonHeight = 75;
    private static final int buttonWidth  = 200;

    private static final int paddingLeft   = 40;
    private static final int paddingTop    = 80;
    private static final int paddingCenter = 45;

    private JPanel panelMenu;
    private JPanel panelSettings;
    private JPanel panelStart;
    private JPanel panelServer;

    private JButton buttonNeuesSpiel;
    private JButton buttonEinstellungen;

    private JButton buttonSingleplayer;
    private JButton buttonServerErstellen;
    private JButton buttonServerBeitreten;

    private JList<Spieler> listSpieler;
    private JButton        buttonSpielStarten;

    private JFrame     ipInput;
    private JTextField textFieldIp;
    private JButton    buttonIpInput;

    private String ipAdresse;

    private GameServer server;
    private GameClient client;

    MenuGUI() {
        super("ZickZackZiege");

        setSize(290, 500);
        setLayout(null);

        initMenuPanel();
        initStartPanel();
        initServerPanel();
        initSettingsPanel();

        setContentPane(panelMenu);
    }

    private void initMenuPanel() {
        panelMenu = new JPanel();
        panelMenu.setSize(getWidth(), getHeight());
        panelMenu.setLayout(null);

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
    }

    private void initStartPanel() {
        panelStart = new JPanel();
        panelStart.setSize(getWidth(), getHeight());
        panelStart.setLayout(null);

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
            client = new GameClient(ipAdresse);
        });

        ipInput.add(textFieldIp);
        ipInput.add(buttonIpInput);

        buttonSingleplayer = new JButton();
        buttonSingleplayer.setBounds(paddingLeft, paddingTop, buttonWidth, buttonHeight);
        buttonSingleplayer.setText("Singleplayer");
        buttonSingleplayer.addActionListener(e -> System.out.println("TODO"));
        buttonServerBeitreten = new JButton();
        buttonServerBeitreten.setBounds(paddingLeft, paddingTop + buttonHeight + paddingCenter, buttonWidth, buttonHeight);
        buttonServerBeitreten.setText("Netzwerkspiel beitreten");
        buttonServerBeitreten.addActionListener(e -> {
            ipInput.setVisible(true);
        });
        buttonServerErstellen = new JButton();
        buttonServerErstellen.setBounds(paddingLeft, paddingTop + (buttonHeight + paddingCenter) * 2, buttonWidth, buttonHeight);
        buttonServerErstellen.setText("Neues Spiel erstellen");
        buttonServerErstellen.addActionListener(e -> {
            server = new GameServer();
            server.setListener(spieler -> listSpieler.setListData(server.getSpieler()));
            setContentPane(panelServer);
        });

        panelStart.add(buttonSingleplayer);
        panelStart.add(buttonServerBeitreten);
        panelStart.add(buttonServerErstellen);
    }

    private void initServerPanel() {
        panelServer = new JPanel();
        panelServer.setSize(getWidth(), getHeight());
        panelServer.setLayout(null);

        listSpieler = new JList<>();
        listSpieler.setListData(new Spieler[0]);
        listSpieler.setBounds(paddingLeft, paddingTop / 4, buttonWidth, (int) (buttonWidth * 1.5));

        buttonSpielStarten = new JButton();
        buttonSpielStarten.setBounds(paddingLeft, paddingTop / 4 + paddingCenter + listSpieler.getHeight(), buttonWidth, buttonHeight);
        buttonSpielStarten.setText("Spiel starten");
        buttonSpielStarten.addActionListener(e -> {
            server.starteSpiel();
        });

        panelServer.add(listSpieler);
        panelServer.add(buttonSpielStarten);
    }

    private void initSettingsPanel() {
        panelSettings = new JPanel();
        panelSettings.setSize(getWidth(), getHeight());
        panelSettings.setLayout(null);
    }
}