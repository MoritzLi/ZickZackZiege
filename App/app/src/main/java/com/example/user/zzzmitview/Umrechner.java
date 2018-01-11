package com.example.user.zzzmitview;

/**
 * Created by User on 30.12.2017.
 */

public class Umrechner {

    public Umrechner() {

    }

    public int toXarray(int pX, int kastenBreite) {
        if (pX >= 0 && pX <= kastenBreite)
            return 0;
        else if (pX >= kastenBreite && pX <= kastenBreite * 2)
            return 1;
        else if (pX >= kastenBreite * 2 && pX <= kastenBreite * 3)
            return 2;
        else if (pX >= kastenBreite * 3 && pX <= kastenBreite * 4)
            return 3;
        else if (pX >= kastenBreite * 4 && pX <= kastenBreite * 5)
            return 4;
        else return -1;
    }

    public int toYarray(int pY, int kastenBreite) {
        if (pY >= 0 && pY <= kastenBreite)
            return 0;
        else if (pY >= kastenBreite && pY <= kastenBreite * 2)
            return 1;
        else if (pY >= kastenBreite * 2 && pY <= kastenBreite * 3)
            return 2;
        else if (pY >= kastenBreite * 3 && pY <= kastenBreite * 4)
            return 3;
        else if (pY >= kastenBreite * 4 && pY <= kastenBreite * 5)
            return 4;
        else return -1;
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
