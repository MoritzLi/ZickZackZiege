public class Spielfeld {
    private final int[][] feld;
    private final int spielerCount;

    public Spielfeld(int spielerCount) {
        this.spielerCount = spielerCount;
        this.feld = new int[3 + spielerCount][3 + spielerCount];
    }

    void setze(int spielerId, int x, int y) throws Exception {
        if (feld[x][y] == 0) {
            feld[x][y] = spielerId;
        } else {
            throw new Exception("Feld bereits besetzt");
        }
    }

    int auswertung(int spielerID) {
        int punkte = auswertungZiegenP(spielerID);
        System.out.println("ZiegenP: " + punkte);
        punkte = punkte + auswertungRammbock(spielerID);
        System.out.println("ZiegenRamm: " + punkte);
        punkte = punkte + auswertungZiegenM(spielerID);
        System.out.println("ZiegenM: " + punkte);
        punkte = punkte + auswertungKorrupte(spielerID);
        System.out.println("ZiegenKorrupte: " + punkte);

        return punkte;
    }

    private int auswertungZiegenP(int spielerID) {
        int punkte = 0;

        for (int y = 0; y < feld.length; y++) {         //Ziegenp waagerecht
            for (int x = 0; x < spielerCount; x++) {
                for (int kast = 0; kast < 4; kast++) {
                    if (kast < 3) {
                        if (feld[x + kast][y] != spielerID) {
                            break;
                        }
                    } else {
                        if (feld[x + kast][y] == spielerID) {
                            punkte = punkte + 3;
                        }
                    }
                }
            }
        }
        // Ende waagerecht

        for (int x = 0; x < feld.length; x++) {         //Ziegenp senkrecht
            for (int y = 0; y < feld.length - 3; y++) {
                for (int kast = 0; kast < 4; kast++) {
                    if (kast < 3) {
                        if (feld[x][y + kast] != spielerID) {
                            break;
                        }
                    } else {
                        if (feld[x][y + kast] == spielerID) {
                            punkte = punkte + 3;
                        }
                    }

                }
            }
        }

        return punkte;
    } // Ende senkrecht

    private int auswertungRammbock(int spielerID) {
        int punkte = 0;

        for (int y = 0; y < feld.length - 1; y++) { //Anfang waagerecht
            for (int x = 0; x < feld.length - 1; x++) {
                if (feld[x][y] == spielerID && feld[x + 1][y] == spielerID) {
                    if (x < feld.length - 3) {
                        if (feld[x + 2][y + 1] == spielerID && feld[x + 3][y + 1] == spielerID) {
                            punkte = punkte + 2;
                        }
                    }
                    if (x > 1) {
                        if (feld[x - 1][y + 1] == spielerID && feld[x - 2][y + 1] == spielerID) {
                            punkte = punkte + 2;
                        }
                    }
                }
            }
        }// ende waagerecht

        for (int x = 0; x < feld.length - 1; x++) { //Anfang senkrecht
            for (int y = 0; y < feld.length - 1; y++) {
                if (feld[x][y] == spielerID && feld[x][y + 1] == spielerID) {
                    if (y < feld.length - 3) {
                        if (feld[x + 1][y + 2] == spielerID && feld[x + 1][y + 3] == spielerID) {
                            punkte = punkte + 2;
                        }
                    }
                    if (y > 1) {
                        if (feld[x + 1][y - 1] == spielerID && feld[x + 1][y - 2] == spielerID) {
                            punkte = punkte + 2;
                        }
                    }
                }
            }
        }// ende senkrecht

        return punkte;
    }

    private int auswertungZiegenM(int spielerID) {
        int punkte = 0;

        for (int x = 0; x < feld.length - 2; x++) {
            for (int y = 1; y < feld.length - 1; y++) {
                if (feld[x][y] == spielerID && feld[x + 1][y - 1] == spielerID && feld[x + 2][y] == spielerID && feld[x + 1][y] == spielerID && feld[x + 1][y] != spielerID) {
                    punkte = punkte + 7;
                }
            }
        }

        return punkte;
    }

    private int auswertungKorrupte(int spielerID) {
        int punkte = 0;

        for (int y = 0; y < feld.length - 1; y++) { //Anfang waagerecht
            for (int x = 0; x < feld.length - 2; x++) {
                if (feld[x][y] == spielerID && feld[x + 1][y] == spielerID && feld[x + 2][y] == spielerID) {
                    if (x < feld.length - 3) {
                        if (feld[x + 3][y + 1] == spielerID) {
                            punkte = punkte + 3;
                        }
                        if (y > 0) {
                            if (feld[x + 3][y - 1] == spielerID) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                    if (x > 0) {
                        if (feld[x - 1][y + 1] == spielerID) {
                            punkte = punkte + 3;
                        }
                        if (y > 0) {
                            if (feld[x - 1][y - 1] == spielerID) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                }
            }
        }// ende waagerecht

        for (int x = 0; x < feld.length - 1; x++) { //Anfang waagerecht
            for (int y = 0; y < feld.length - 2; y++) {
                if (feld[x][y] == spielerID && feld[x][y + 1] == spielerID && feld[x][y + 2] == spielerID) {
                    if (y < feld.length - 3) {
                        if (feld[x + 1][y + 3] == spielerID) {
                            punkte = punkte + 3;
                        }
                        if (x > 0) {
                            if (feld[x - 1][y + 3] == spielerID) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                    if (y > 0) {
                        if (feld[x + 1][y - 1] == spielerID) {
                            punkte = punkte + 3;
                        }
                        if (x > 0) {
                            if (feld[x - 1][y - 1] == spielerID) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                }
            }
        }//ende waagerecht

        return punkte;
    }
}