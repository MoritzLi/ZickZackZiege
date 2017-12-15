public class Spielfeld {
    private final int[][] feld;
    private final int spieler;

    public Spielfeld(int spieler) {
        this.spieler = spieler;
        this.feld = new int[3 + spieler][3 + spieler];
    }

    public void setze(int spielerId, int x, int y) {
        feld[x][y] = spielerId;
    }

    public int[][] getFeld() {
        return feld;
    }
}