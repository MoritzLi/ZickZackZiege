public class Feld {
    private int spieleranzahl;
    private int feldgröße;
    int[][] feld;

    public Feld(int pSpieleranzahl){
        spieleranzahl = pSpieleranzahl;
        if(spieleranzahl == 1) {
            //einzelspielermodus
            spieleranzahl++;
        }
        feldgröße = spieleranzahl+3;
        feld = new int[feldgröße][feldgröße];
    }

    public int auswertung(int pSpieler){
        int punkte = auswertungZiegenP(pSpieler, spieleranzahl,feld);
        System.out.println("ZiegenP: " + punkte);
        punkte = punkte + auswertungRammbock(pSpieler,spieleranzahl,feld);
        System.out.println("ZiegenRamm: " + punkte);
        punkte = punkte + auswertungZiegenM(pSpieler,spieleranzahl,feld);
        System.out.println("ZiegenM: " + punkte);
        punkte = punkte + auswertungKorrupte(pSpieler,spieleranzahl,feld);
        System.out.println("ZiegenKorrupte: " + punkte);

        return punkte;
    }

    private int auswertungZiegenP(int pSpieler, int pSpielerAnzahl, int[][] feld){
        int punkte = 0;
        int spieler = pSpieler;
        int spieleranzahl = pSpielerAnzahl;
        int feldgröße = spieleranzahl + 3;

        for (int yKor = 0; yKor < feldgröße; yKor++) {         //Ziegenp waagerecht
            for (int xKor = 0; xKor < feldgröße - 3; xKor++) {
                for (int kast = 0; kast < 4; kast++) {
                    if (kast < 3) {
                        if (feld[xKor + kast][yKor] != spieler) {
                            break;
                        }
                    } else {
                        if (feld[xKor + kast][yKor] == spieler) {
                            punkte = punkte + 3;
                        }
                    }

                }
            }
        }
        // Ende waagerecht


        for(int xKor = 0; xKor < feldgröße; xKor++) {         //Ziegenp senkrecht
            for(int yKor = 0; yKor < feldgröße-3; yKor++) {
                for(int kast = 0; kast < 4; kast++) {
                    if(kast < 3) {
                        if(feld[xKor][yKor+kast]!=spieler){
                            break;
                        }
                    }else {
                        if(feld[xKor][yKor+kast]==spieler) {
                            punkte = punkte +3;
                        }
                    }

                }
            }
        }
        return punkte;
    } // Ende senkrecht



    private int auswertungRammbock(int pSpieler, int pSpieleranzahl, int[][] feld){
        int punkte = 0;
        int spieler = pSpieler;
        int spieleranzahl = pSpieleranzahl;
        int feldgröße = spieleranzahl + 3;

        for(int yKor = 0; yKor < feldgröße-1; yKor++){ //Anfang waagerecht
            for(int xKor = 0; xKor < feldgröße-1; xKor++){
                if(feld[xKor][yKor] == spieler && feld[xKor+1][yKor] == spieler){
                    if(xKor<feldgröße-3){
                        if(feld[xKor+2][yKor+1]==spieler && feld[xKor+3][yKor+1]==spieler){
                            punkte = punkte+2;
                        }
                    }
                    if(xKor > 1){
                        if(feld[xKor-1][yKor+1]==spieler && feld[xKor-2][yKor+1]==spieler){
                            punkte = punkte+2;
                        }
                    }
                }
            }
        }// ende waagerecht

        for(int xKor = 0; xKor < feldgröße-1; xKor++){ //Anfang senkrecht
            for(int yKor = 0; yKor < feldgröße-1; yKor++){
                if(feld[xKor][yKor] == spieler && feld[xKor][yKor+1] == spieler){
                    if(yKor<feldgröße-3){
                        if(feld[xKor+1][yKor+2]==spieler && feld[xKor+1][yKor+3]==spieler){
                            punkte = punkte+2;
                        }
                    }
                    if(yKor > 1){
                        if(feld[xKor+1][yKor-1]==spieler && feld[xKor+1][yKor-2]==spieler){
                            punkte = punkte+2;
                        }
                    }
                }
            }
        }// ende senkrecht
        return punkte;
    }



    private int auswertungZiegenM(int pSpieler, int pSpieleranzahl, int[][] feld){
        int punkte = 0;
        int spieler = pSpieler;
        int spieleranzahl = pSpieleranzahl;
        int feldgröße = spieleranzahl + 3;


        for(int xKor=0; xKor<feldgröße-2; xKor++){
            for(int yKor=1; yKor<feldgröße-1; yKor++){
                if(feld[xKor][yKor]==spieler && feld[xKor+1][yKor-1]==spieler && feld[xKor+2][yKor]==spieler && feld[xKor+1][yKor]==spieler && feld[xKor+1][yKor]!=spieler){
                    punkte = punkte+7;
                }
            }
        }
        return punkte;
    }

    private int auswertungKorrupte(int pSpieler, int pSpieleranzahl, int[][] feld){
        int punkte = 0;
        int spieler = pSpieler;
        int spieleranzahl = pSpieleranzahl;
        int feldgröße = spieleranzahl + 3;


        for(int yKor = 0; yKor < feldgröße-1; yKor++){ //Anfang waagerecht
            for(int xKor = 0; xKor < feldgröße-2; xKor++){
                if(feld[xKor][yKor] == spieler && feld[xKor+1][yKor] == spieler && feld[xKor+2][yKor]==spieler){
                    if(xKor<feldgröße-3){
                        if(feld[xKor+3][yKor+1]==spieler){
                            punkte = punkte+3;
                        }
                        if(yKor > 0){
                            if(feld[xKor+3][yKor-1] == spieler){
                                punkte = punkte+3;
                            }
                        }
                    }
                    if(xKor > 0){
                        if(feld[xKor-1][yKor+1]==spieler){
                            punkte = punkte+3;
                        }
                        if(yKor > 0){
                            if(feld[xKor-1][yKor-1] == spieler){
                                punkte = punkte+3;
                            }
                        }
                    }
                }
            }
        }// ende waagerecht

        for(int xKor = 0; xKor < feldgröße-1; xKor++){ //Anfang waagerecht
            for(int yKor = 0; yKor < feldgröße-2; yKor++){
                if(feld[xKor][yKor] == spieler && feld[xKor][yKor+1] == spieler && feld[xKor][yKor+2]==spieler){
                    if(yKor<feldgröße-3){
                        if(feld[xKor+1][yKor+3]==spieler){
                            punkte = punkte+3;
                        }
                        if(xKor > 0){
                            if(feld[xKor-1][yKor+3] == spieler){
                                punkte = punkte+3;
                            }
                        }
                    }
                    if(yKor > 0){
                        if(feld[xKor+1][yKor-1]==spieler){
                            punkte = punkte+3;
                        }
                        if(xKor > 0){
                            if(feld[xKor-1][yKor-1] == spieler){
                                punkte = punkte+3;
                            }
                        }
                    }
                }
            }
        }//ende waagerecht

        return punkte;
    }

}