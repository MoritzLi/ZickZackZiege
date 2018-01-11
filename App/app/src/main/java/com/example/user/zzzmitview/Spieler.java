package com.example.user.zzzmitview;

class Spieler {
    private final int id;
    private final String IP;
    private final int port;
    private int punkte;

    public Spieler(int id, String IP, int port) {
        this.id = id;
        this.IP = IP;
        this.port = port;
        this.punkte = 0;
    }

    public Spieler(String IP) {
        this.id = 0;
        this.IP = IP;
        this.port = 0;
        this.punkte = 0;
    }

    int getId() {
        return id;
    }

    String getIP() {
        return IP;
    }

    int getPort() {
        return port;
    }

    void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Spieler) {
            Spieler other = (Spieler) obj;
            return other.getIP().equals(this.getIP());
        }
        return false;
    }

    @Override
    public String toString() {
        return getIP() + ':' + getPort();
    }
}