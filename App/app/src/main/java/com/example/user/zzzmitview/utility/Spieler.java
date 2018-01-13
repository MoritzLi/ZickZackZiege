package com.example.user.zzzmitview.utility;

public class Spieler {
    private final int    id;
    private       int    punkte;
    private       String name;

    public Spieler(int id) {
        this.id = id;
        this.punkte = 0;
    }

    public int getId() {
        return id;
    }

    void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public int getPunkte() {
        return punkte;
    }

    public String getPunkteString() {
        return getPunkte() + " Punkte";
    }

    public String getName() {
        if (name == null) {
            return "Spieler " + id;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}