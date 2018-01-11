public class Einzelspiel extends Spiel {
    private int einzelXKor;
    private int einzelYKor;
    private BinaryTree<Position> kiBaum = new BinaryTree<>();

    public Einzelspiel(int pSpieleranzahl) {
        super(pSpieleranzahl);
    }

    void einzelspieler(int pSpielzug) {
        // switch case mit baum für die ersten 4 züge der Ki
        switch (pSpielzug) {
            case 1:
                baueSpielbaum();
                Position p = kiBaum.getContent();
                einzelXKor = p.getX();
                einzelYKor = p.getY();
                break;
            case 3:
                boolean besetzt = true;
                for (int x = 0; x < 5; x++) {
                    if (feld.gib(x, 2) == 2)
                        besetzt = false;
                }
                if (!besetzt)
                    kiBaum = kiBaum.getLeftTree();
                else
                    kiBaum = kiBaum.getRightTree();
                p = kiBaum.getContent();
                einzelXKor = p.getX();
                einzelYKor = p.getY();
                break;
            case 5:
                p = kiBaum.getContent();
                int xFrei = p.getXFrei();
                int yFrei = p.getYFrei();
                besetzt = feld.leer(xFrei, yFrei);
                if (!besetzt)
                    kiBaum = kiBaum.getLeftTree();
                else
                    kiBaum = kiBaum.getRightTree();
                p = kiBaum.getContent();
                einzelXKor = p.getX();
                einzelYKor = p.getY();
                break;
            default:
                int max = 0;
                int[][] ki = new int[5][5];
                for (int xKor = 0; xKor < 5; xKor++) {
                    for (int yKor = 0; yKor < 5; yKor++) {
                        if (feld.leer(xKor, yKor)) {
                            feld.setze(1, xKor, yKor);
                            ki[xKor][yKor] = feld.auswertung(1);
                            feld.setze(2, xKor, yKor);
                            ki[xKor][yKor] = ki[xKor][yKor] + feld.auswertung(2);
                            feld.loesche(xKor, yKor);
                        }
                    }
                }

                for (int xKor = 0; xKor < 5; xKor++) {
                    for (int yKor = 0; yKor < 5; yKor++) {
                        if (ki[xKor][yKor] > max) {
                            max = ki[xKor][yKor];
                            einzelXKor = xKor;
                            einzelYKor = yKor;
                        } else if (ki[xKor][yKor] == max) {
                            int zufall = (int) Math.random() * 2;
                            if (zufall > 1) {
                                max = ki[xKor][yKor];
                                einzelXKor = xKor;
                                einzelYKor = yKor;
                            }

                        }
                    }
                }
                break;
        }
        // im gui abwechselnd spielen mit vom spieler gewähltem x und y und danach mit von einzelspieler zugewiesenen x und y aufrufen
    }

    private void baueSpielbaum() {
        Position p0 = new Position(2, 2, 0, 0);
        kiBaum.setContent(p0);

        BinaryTree<Position> l = new BinaryTree<>();
        Position p1 = new Position(2, 3, 2, 1);
        l.setContent(p1);

        BinaryTree<Position> r = new BinaryTree<>();
        Position p2 = new Position(3, 2, 1, 2);
        r.setContent(p2);

        BinaryTree<Position> ll = new BinaryTree<>();
        Position p3 = new Position(2, 4, 1, 3);
        ll.setContent(p3);

        BinaryTree<Position> lr = new BinaryTree<>();
        Position p4 = new Position(2, 1, 2, 0);
        lr.setContent(p4);

        BinaryTree<Position> rl = new BinaryTree<>();
        Position p5 = new Position(4, 2, 3, 1);
        rl.setContent(p5);

        BinaryTree<Position> rr = new BinaryTree<>();
        Position p6 = new Position(1, 2, 0, 2);
        rr.setContent(p6);

        l.setLeftTree(ll);
        l.setRightTree(lr);
        r.setLeftTree(rl);
        r.setRightTree(rr);

        kiBaum.setLeftTree(l);
        kiBaum.setRightTree(r);
    }

    public int getEinzelXKor() {
        return einzelXKor;
    }

    public int getEinzelYKor() {
        return einzelYKor;
    }
}