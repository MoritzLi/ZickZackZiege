package com.example.user.zzzmitview.utility;

abstract class Auswertung {
    static int auswertung(int spielerID, int[][] feld) {
        return auswertung(spielerID, feld, true, true, true, true);
    }

    static int auswertung(int spielerID, int[][] feld, boolean rammbock, boolean korrupt, boolean papa, boolean mama) {
        int punkte = 0;

        if (rammbock) {
            punkte += rammbock(spielerID, feld);
        }
        if (korrupt) {
            punkte += korrupt(spielerID, feld);
        }
        if (papa) {
            punkte += papa(spielerID, feld);
        }
        if (mama) {
            punkte += mama(spielerID, feld);
        }

        return punkte;
    }

    private static int rammbock(int pSpieler, int[][] feld) {
        int punkte = 0;

        // Waagerecht
        for (int y = 0; y < feld.length - 1; y++) {
            for (int x = 0; x < feld.length - 1; x++) {
                if (feld[x][y] == pSpieler && feld[x + 1][y] == pSpieler) {
                    if (x < feld.length - 3) {
                        if (feld[x + 2][y + 1] == pSpieler && feld[x + 3][y + 1] == pSpieler) {
                            punkte = punkte + 2;
                        }
                    }
                    if (x > 1) {
                        if (feld[x - 1][y + 1] == pSpieler && feld[x - 2][y + 1] == pSpieler) {
                            punkte = punkte + 2;
                        }
                    }
                }
            }
        }

        // Senkrecht
        for (int x = 0; x < feld.length - 1; x++) {
            for (int y = 0; y < feld.length - 1; y++) {
                if (feld[x][y] == pSpieler && feld[x][y + 1] == pSpieler) {
                    if (y < feld.length - 3) {
                        if (feld[x + 1][y + 2] == pSpieler && feld[x + 1][y + 3] == pSpieler) {
                            punkte = punkte + 2;
                        }
                    }
                    if (y > 1) {
                        if (feld[x + 1][y - 1] == pSpieler && feld[x + 1][y - 2] == pSpieler) {
                            punkte = punkte + 2;
                        }
                    }
                }
            }
        }

        return punkte;
    }

    private static int korrupt(int pSpieler, int[][] feld) {
        int punkte = 0;

        // Waagerecht
        for (int y = 0; y < feld.length; y++) {
            for (int x = 0; x < feld.length - 2; x++) {
                if (feld[x][y] == pSpieler && feld[x + 1][y] == pSpieler && feld[x + 2][y] == pSpieler) {
                    if (x < feld.length - 3) {
                        if (y < feld.length - 1)
                            if (feld[x + 3][y + 1] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        if (y > 0) {
                            if (feld[x + 3][y - 1] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                    if (x > 0) {
                        if (y < feld.length - 1)
                            if (feld[x - 1][y + 1] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        if (y > 0) {
                            if (feld[x - 1][y - 1] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                }
            }
        }

        // Senkrecht
        for (int x = 0; x < feld.length; x++) {
            for (int y = 0; y < feld.length - 2; y++) {
                if (feld[x][y] == pSpieler && feld[x][y + 1] == pSpieler && feld[x][y + 2] == pSpieler) {
                    if (y < feld.length - 3) {
                        if (x < feld.length - 1)
                            if (feld[x + 1][y + 3] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        if (x > 0) {
                            if (feld[x - 1][y + 3] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                    if (y > 0) {
                        if (x < feld.length - 1)
                            if (feld[x + 1][y - 1] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        if (x > 0) {
                            if (feld[x - 1][y - 1] == pSpieler) {
                                punkte = punkte + 3;
                            }
                        }
                    }
                }
            }
        }

        return punkte;
    }

    private static int papa(int pSpieler, int[][] feld) {
        int punkte = 0;

        // Waagerecht
        for (int y = 0; y < feld.length; y++) {
            for (int x = 0; x < feld.length - 3; x++) {
                for (int kast = 0; kast < 4; kast++) {
                    if (kast < 3) {
                        if (feld[x + kast][y] != pSpieler) {
                            break;
                        }
                    } else {
                        if (feld[x + kast][y] == pSpieler) {
                            punkte = punkte + 3;
                        }
                    }
                }
            }
        }

        // Senkrecht
        for (int x = 0; x < feld.length; x++) {
            for (int y = 0; y < feld.length - 3; y++) {
                for (int kast = 0; kast < 4; kast++) {
                    if (kast < 3) {
                        if (feld[x][y + kast] != pSpieler) {
                            break;
                        }
                    } else {
                        if (feld[x][y + kast] == pSpieler) {
                            punkte = punkte + 3;
                        }
                    }
                }
            }
        }

        return punkte;
    }

    private static int mama(int pSpieler, int[][] feld) {
        int punkte = 0;

        for (int x = 0; x < feld.length - 2; x++) {
            for (int y = 1; y < feld.length - 1; y++) {
                if (feld[x][y] == pSpieler && feld[x + 1][y - 1] == pSpieler && feld[x + 2][y] == pSpieler && feld[x + 1][y + 1] == pSpieler && feld[x + 1][y] != pSpieler && feld[x + 1][y] != 0) {
                    punkte = punkte + 7;
                }
            }
        }

        return punkte;
    }
}