import java.io.IOException;

import javax.swing.JFrame;

import network.GameClient;
import network.GameServer;
import network.NetzwerkListener;
import utility.NetzwerkSpieler;
import utility.Schwierigkeit;

class MenuGUI extends JFrame {
    private SelectPanel                panelMenu;
    private SelectPanel                panelSettings;
    private SelectPanel                panelNewGame;
    private SelectPanel                panelSingleplayer;
    private SelectPanel                panelNetwork;
    private TextInputPanel             panelCount;
    private TextInputPanel             panelJoin;
    private ListPanel<NetzwerkSpieler> panelServer;

    private String ipAdresse;

    private GameServer server;
    private GameClient client;

    MenuGUI() {
        super("ZickZackZiege");

        setSize(290, 500);
        setLayout(null);

        initMenuPanel();
        initStartPanel();
        initSingleplayerPanel();
        initCountPanel();
        initNetworkPanel();
        initJoinPanel();
        initServerPanel();
        initSettingsPanel();

        setContentPane(panelMenu);
    }

    private void initMenuPanel() {
        panelMenu = new SelectPanel(2, getWidth(), getHeight());

        panelMenu.getButton1().setText("Neues Spiel");
        panelMenu.getButton1().addActionListener(e -> setContentPane(panelNewGame));

        panelMenu.getButton2().setText("Einstellungen");
        panelMenu.getButton2().addActionListener(e -> setContentPane(panelSettings));
    }

    private void initStartPanel() {
        panelNewGame = new SelectPanel(3, getWidth(), getHeight());

        panelNewGame.getButton1().setText("Singleplayer");
        panelNewGame.getButton1().addActionListener(e -> setContentPane(panelSingleplayer));

        panelNewGame.getButton2().setText("Multiplayer");
        panelNewGame.getButton2().addActionListener(e -> setContentPane(panelCount));

        panelNewGame.getButton3().setText("Netzwerk");
        panelNewGame.getButton3().addActionListener(e -> setContentPane(panelNetwork));
    }

    private void initSingleplayerPanel() {
        panelSingleplayer = new SelectPanel(2, getWidth(), getHeight());

        panelSingleplayer.getButton1().setText("Einfach");
        panelSingleplayer.getButton1().addActionListener(e -> new SingleSpielfeldGUI(Schwierigkeit.EINFACH));

        panelSingleplayer.getButton2().setText("Schwierig");
        panelSingleplayer.getButton2().addActionListener(e -> new SingleSpielfeldGUI(Schwierigkeit.SCHWIERIG));
    }

    private void initCountPanel() {
        panelCount = new TextInputPanel(getWidth(), getHeight());

        panelCount.getButton().addActionListener(e -> {
            try {
                int spielerCount = Integer.parseInt(
                        panelCount.getTextInput()
                );
                new MultiSpielfeldGUI(spielerCount);
            } catch (NumberFormatException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void initNetworkPanel() {
        panelNetwork = new SelectPanel(2, getWidth(), getHeight());

        panelNetwork.getButton1().setText("Netzwerkspiel betreten");
        panelNetwork.getButton1().addActionListener(e -> setContentPane(panelJoin));

        panelNetwork.getButton2().setText("Netzwerkspiel erstellen");
        panelNetwork.getButton2().addActionListener(e -> {
            try {
                server = new GameServer();
                server.setListener(new NetzwerkListener() {
                    @Override
                    public void onPlayersChanged() {
                        panelServer.setListData(server.getSpieler());
                    }

                    @Override
                    public void onGameStarted(int spielerCount, int myID) {
                        new NetzwerkSpielfeldGUI(spielerCount, myID, server, client);
                    }

                    @Override
                    public void onFieldSet(int id, int x, int y) {

                    }

                    @Override
                    public void onYourTurn() {

                    }
                });
                panelServer.setListData(server.getSpieler());
                setContentPane(panelServer);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void initJoinPanel() {
        panelJoin = new TextInputPanel(getWidth(), getHeight());

        panelJoin.getButton().addActionListener(e -> {
            try {
                ipAdresse = panelJoin.getTextInput();
                client = new GameClient(ipAdresse, null);
                client.setListener(new NetzwerkListener() {
                    @Override
                    public void onPlayersChanged() {

                    }

                    @Override
                    public void onGameStarted(int spielerCount, int myID) {
                        new NetzwerkSpielfeldGUI(spielerCount, myID, server, client);
                    }

                    @Override
                    public void onFieldSet(int id, int x, int y) {

                    }

                    @Override
                    public void onYourTurn() {

                    }
                });
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void initServerPanel() {
        panelServer = new ListPanel<>(getWidth(), getHeight());

        panelServer.setListData(new NetzwerkSpieler[0]);

        panelServer.getButton().addActionListener(e -> server.starteSpiel());
    }

    private void initSettingsPanel() {
//        panelSettings = new JPanel();
//        panelSettings.setSize(getWidth(), getHeight());
//        panelSettings.setLayout(null);
    }
}