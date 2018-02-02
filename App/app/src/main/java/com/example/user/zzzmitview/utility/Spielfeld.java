package com.example.user.zzzmitview.utility;

public class Spielfeld {
    private final int[][] feld;
    private       int     spielrunde;

    public Spielfeld(int spielerCount) {
        feld = new int[3 + spielerCount][3 + spielerCount];
        spielrunde = 0;
    }

    public int getValue(int x, int y) {
        return feld[x][y];
    }

    public void setValue(int spielerId, int x, int y) {
        if (isEmpty(x, y)) {
            feld[x][y] = spielerId;
        }
    }

    public void getPoints(Spieler... spieler) {
        for (Spieler s : spieler) {
            s.setPunkte(getPoints(s.getId()));
        }
    }

    public int getPoints(int spielerID) {
        return Auswertung.auswertung(spielerID, feld);
    }

    public int getFieldCount() {
        return feld.length;
    }

    void clear(int x, int y) {
        feld[x][y] = 0;
    }

    public void clear() {
        for (int x = 0; x < feld.length; x++)
            for (int y = 0; y < feld.length; y++)
                clear(x, y);

        spielrunde = 0;
    }

    public void nextRound() {
        spielrunde++;
    }

    public int getRound() {
        return spielrunde;
    }

    public boolean isPlaying() {
        return spielrunde < feld.length * feld.length;
    }

    public boolean isEmpty(int x, int y) {
        return getValue(x, y) == 0;
    }

    public int[][] getFeld() {
        return feld;
    }
}