public class Spielfeld {
    private final int[][] feld;
    private final int spielerCount;
    private final Spieler[] spieler;

    public Spielfeld(int spielerCount) {
        this.spielerCount = spielerCount;
        this.feld = new int[3 + spielerCount][3 + spielerCount];
        this.spieler = new Spieler[spielerCount];
        for (int i = 0; i < spielerCount; i++) {
            spieler[i] = new Spieler(i + 1);
        }
    }

    public void setze(int spielerId, int x, int y) {
        feld[x][y] = spielerId;
    }

    public int[][] getFeld() {
        return feld;
    }
}