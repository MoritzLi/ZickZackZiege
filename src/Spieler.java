public class Spieler {
    private final int id;
    private final String IP;
    private final int port;

    public Spieler(int id, String IP, int port) {
        this.id = id;
        this.IP = IP;
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        Spieler other = (Spieler) obj;
        return other.getIP().equals(this.getIP());
    }
}