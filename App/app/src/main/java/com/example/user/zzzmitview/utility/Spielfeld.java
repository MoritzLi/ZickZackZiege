package com.example.user.zzzmitview.utility;

public class Spielfeld {
    private final int[][] feld;

    public Spielfeld(int spielerCount) {
        feld = new int[3 + spielerCount][3 + spielerCount];
    }

    public boolean leer(int x, int y) {
        return feld[x][y] == 0;
    }

    public int gib(int x, int y) {
        return feld[x][y];
    }

    public void setze(int spielerId, int x, int y) {
        if (feld[x][y] == 0) {
            feld[x][y] = spielerId;
        }
    }

    void loesche(int x, int y) {
        feld[x][y] = 0;
    }

    public void auswertung(Spieler spieler) {
        spieler.setPunkte(Auswertung.auswertung(spieler.getId(), feld));
    }

    public int getBreite() {
        return feld.length;
    }
}