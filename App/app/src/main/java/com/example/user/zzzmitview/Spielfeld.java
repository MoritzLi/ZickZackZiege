package com.example.user.zzzmitview;

public class Spielfeld {
    private final int[][] feld;

    public Spielfeld(int spielerCount) {
        feld = new int[3 + spielerCount][3 + spielerCount];
    }

    boolean leer(int x, int y) {
        return feld[x][y] == 0;
    }

    int gib(int x, int y) {
        return feld[x][y];
    }

    void setze(int spielerId, int x, int y) {
        if (feld[x][y] == 0) {
            feld[x][y] = spielerId;
        }
    }

    void loesche(int x, int y) {
        feld[x][y] = 0;
    }

    int auswertung(int spielerID) {
        return Auswertung.auswertung(spielerID, feld);
    }
}