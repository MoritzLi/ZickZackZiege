public class Spiel {
    private int spieleranzahl;
    private List<Spieler> list = new List<>();
    Spielfeld feld;

    public Spiel(int pSpieleranzahl) {
        spieleranzahl = pSpieleranzahl;
        if (spieleranzahl == 1) {
            feld = new Spielfeld(2);
        } else {
            feld = new Spielfeld(spieleranzahl);
            for (int i = 1; spieleranzahl - i >= 0; i++) {
                Spieler spieler = new Spieler(i, null, 0);
                list.append(spieler);
            }
        }
    }

    public void spielen(int xKor, int yKor, int spielzug) {
        if (spielzug == 1)
            list.toFirst();
        if (feld.leer(xKor, yKor)) {
            if (!list.hasAccess())
                list.toFirst();
            feld.setze(list.getContent().getId(), xKor, yKor);
            if (list.hasAccess())
                list.next();
            else
                list.toFirst();
            spielzug++;
        }
        if (spielzug > (spieleranzahl + 3) * (spieleranzahl + 3)) {
            list.toFirst();
            while (list.hasAccess()) {
                list.getContent().setPunkte(feld.auswertung(list.getContent().getId()));
                list.next();
            }
        }
    }
}