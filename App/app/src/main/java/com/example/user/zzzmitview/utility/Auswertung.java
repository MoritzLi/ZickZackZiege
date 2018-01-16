package com.example.user.zzzmitview.utility;

abstract class Auswertung {
    static int auswertung(int pSpieler, int[][] feld) {

        int punkte = auswertungZiegenP(pSpieler, feld);
        punkte = punkte + auswertungRammbock(pSpieler, feld);
        punkte = punkte + auswertungZiegenM(pSpieler, feld);
        punkte = punkte + auswertungKorrupte(pSpieler, feld);
        return punkte;

    }

    private static int auswertungZiegenP(int pSpieler, int[][] feld) {
        int punkte = 0;

        for (int yKor = 0; yKor < feld.length; yKor++) {         //Ziegenp waagerecht

            for (int xKor = 0; xKor < feld.length - 3; xKor++) {

                for (int kast = 0; kast < 4; kast++) {

                    if (kast < 3) {

                        if (feld[xKor + kast][yKor] != pSpieler) {

                            break;

                        }

                    } else {

                        if (feld[xKor + kast][yKor] == pSpieler) {

                            punkte = punkte + 3;

                        }

                    }


                }

            }

        }

        // Ende waagerecht


        for (int xKor = 0; xKor < feld.length; xKor++) {         //Ziegenp senkrecht

            for (int yKor = 0; yKor < feld.length - 3; yKor++) {

                for (int kast = 0; kast < 4; kast++) {

                    if (kast < 3) {

                        if (feld[xKor][yKor + kast] != pSpieler) {

                            break;

                        }

                    } else {

                        if (feld[xKor][yKor + kast] == pSpieler) {

                            punkte = punkte + 3;

                        }

                    }


                }

            }

        }

        return punkte;

    } // Ende senkrecht

    private static int auswertungRammbock(int pSpieler, int[][] feld) {
        int punkte = 0;

        for (int yKor = 0; yKor < feld.length - 1; yKor++) { //Anfang waagerecht

            for (int xKor = 0; xKor < feld.length - 1; xKor++) {

                if (feld[xKor][yKor] == pSpieler && feld[xKor + 1][yKor] == pSpieler) {

                    if (xKor < feld.length - 3) {

                        if (feld[xKor + 2][yKor + 1] == pSpieler && feld[xKor + 3][yKor + 1] == pSpieler) {

                            punkte = punkte + 2;

                        }

                    }

                    if (xKor > 1) {

                        if (feld[xKor - 1][yKor + 1] == pSpieler && feld[xKor - 2][yKor + 1] == pSpieler) {

                            punkte = punkte + 2;

                        }

                    }

                }

            }

        }// ende waagerecht


        for (int xKor = 0; xKor < feld.length - 1; xKor++) { //Anfang senkrecht

            for (int yKor = 0; yKor < feld.length - 1; yKor++) {

                if (feld[xKor][yKor] == pSpieler && feld[xKor][yKor + 1] == pSpieler) {

                    if (yKor < feld.length - 3) {

                        if (feld[xKor + 1][yKor + 2] == pSpieler && feld[xKor + 1][yKor + 3] == pSpieler) {

                            punkte = punkte + 2;

                        }

                    }

                    if (yKor > 1) {

                        if (feld[xKor + 1][yKor - 1] == pSpieler && feld[xKor + 1][yKor - 2] == pSpieler) {

                            punkte = punkte + 2;

                        }

                    }

                }

            }

        }// ende senkrecht

        return punkte;
    }

    private static int auswertungZiegenM(int pSpieler, int[][] feld) {
        int punkte = 0;

        for (int xKor = 0; xKor < feld.length - 2; xKor++) {

            for (int yKor = 1; yKor < feld.length - 1; yKor++) {

                if (feld[xKor][yKor] == pSpieler && feld[xKor + 1][yKor - 1] == pSpieler && feld[xKor + 2][yKor] == pSpieler && feld[xKor + 1][yKor + 1] == pSpieler && feld[xKor + 1][yKor] != pSpieler && feld[xKor + 1][yKor] != 0) {

                    punkte = punkte + 7;

                }

            }

        }

        return punkte;
    }

    private static int auswertungKorrupte(int pSpieler, int[][] feld) {
        int punkte = 0;

        for (int yKor = 0; yKor < feld.length; yKor++) { //Anfang waagerecht

            for (int xKor = 0; xKor < feld.length - 2; xKor++) {

                if (feld[xKor][yKor] == pSpieler && feld[xKor + 1][yKor] == pSpieler && feld[xKor + 2][yKor] == pSpieler) {

                    if (xKor < feld.length - 3) {
                        if (yKor < feld.length - 1)
                            if (feld[xKor + 3][yKor + 1] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        if (yKor > 0) {

                            if (feld[xKor + 3][yKor - 1] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        }

                    }

                    if (xKor > 0) {
                        if (yKor < feld.length - 1)
                            if (feld[xKor - 1][yKor + 1] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        if (yKor > 0) {

                            if (feld[xKor - 1][yKor - 1] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        }

                    }

                }

            }

        }// ende waagerecht


        for (int xKor = 0; xKor < feld.length; xKor++) { //Anfang waagerecht

            for (int yKor = 0; yKor < feld.length - 2; yKor++) {

                if (feld[xKor][yKor] == pSpieler && feld[xKor][yKor + 1] == pSpieler && feld[xKor][yKor + 2] == pSpieler) {

                    if (yKor < feld.length - 3) {
                        if (xKor < feld.length - 1)
                            if (feld[xKor + 1][yKor + 3] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        if (xKor > 0) {

                            if (feld[xKor - 1][yKor + 3] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        }

                    }

                    if (yKor > 0) {
                        if (xKor < feld.length - 1)
                            if (feld[xKor + 1][yKor - 1] == pSpieler) {

                                punkte = punkte + 3;

                            }

                        if (xKor > 0) {

                            if (feld[xKor - 1][yKor - 1] == pSpieler) {

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