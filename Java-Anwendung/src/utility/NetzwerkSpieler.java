package utility;

public class NetzwerkSpieler extends Spieler {
    private final String IP;
    private final int    port;

    public NetzwerkSpieler(int id, String IP, int port) {
        super(id);
        this.IP = IP;
        this.port = port;
    }

    public NetzwerkSpieler(String IP) {
        super(0);
        this.IP = IP;
        this.port = 0;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NetzwerkSpieler) {
            return equals(((NetzwerkSpieler) obj).getIP());
        } else if (obj instanceof String) {
            return getIP().equals(obj);
        }
        return false;
    }

    @Override
    public String toString() {
        return getIP() + ':' + getPort();
    }

    public String getIPString() {
        return IP + ':' + port;
    }
}