package com.example.user.zzzmitview;

/**
 * Created by User on 30.12.2017.
 */

public class Umrechner {

    public Umrechner() {

    }

    public int toXarray(int pX, int kastenBreite) {
        return pX / kastenBreite;
    }

    public int toYarray(int pY, int kastenBreite) {
        if (pY / kastenBreite < 5)
            return pY / kastenBreite;
        return -1;
    }

    public int getEcken(int pX, int pY, int kastenbreite, String ort) {
        int konstante = 20;
        switch (ort) {
            case "xLinksOben":
                return kastenbreite * pX + konstante;
            case "yLinksOben":
                return kastenbreite * pY + konstante;
            case "xRechtsUnten":
                return kastenbreite * (pX + 1) - konstante;
            case "yRechtsUnten":
                return kastenbreite * (pY + 1) - konstante;
            case "xRechtsOben":
                return kastenbreite * (pX + 1) - konstante;
            case "yRechtsOben":
                return kastenbreite * pY + konstante;
            case "xLinksUnten":
                return kastenbreite * pX + konstante;
            case "yLinksUnten":
                return kastenbreite * (pY + 1) - konstante;
            case "xMitte":
                return (kastenbreite * pX + kastenbreite / 2);
            case "yMitte":
                return (kastenbreite * pY + kastenbreite / 2);
            case "kastenbreiteRadius":
                return kastenbreite / 2 - konstante;
        }
        return 0;
    }
}
