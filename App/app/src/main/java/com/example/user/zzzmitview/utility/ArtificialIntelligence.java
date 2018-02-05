package com.example.user.zzzmitview.utility;

/**
 * KI für den Einzelspieler-Modus
 */
public class ArtificialIntelligence {
    public static final int kiID     = 2;
    public static final int playerID = 1;

    private final Spielfeld            spielfeld;
    private       BinaryTree<Position> kiBaum;
    private       int                  nextX;
    private       int                  nextY;

    public ArtificialIntelligence(Spielfeld spielfeld, Schwierigkeit schwierigkeit) {
        this.spielfeld = spielfeld;
        this.kiBaum = new BinaryTree<>();

        switch (schwierigkeit) {
            case SCHWIERIG:
                baueSpielbaum();
                break;
            case EINFACH:
                baueSpielbaum2();
        }
    }

    public void spielzug() {
        switch (spielfeld.getRound() + 1) {
            case 1:
                baueSpielbaum();
                Position p = kiBaum.getContent();

                nextX = p.getX();
                nextY = p.getY();
                break;

            case 2:
                baueSpielbaum2();
                boolean besetzt = false;
                for (int x = 0; x < spielfeld.getFieldCount(); x++) {
                    if (spielfeld.getValue(x, 1) != 0)
                        besetzt = true;
                }
                if (besetzt) {
                    kiBaum = kiBaum.getRightTree();
                } else {
                    kiBaum = kiBaum.getLeftTree();
                }

                besetzt = false;
                for (int y = 0; y < spielfeld.getFieldCount(); y++) {
                    if (spielfeld.getValue(1, y) != 0)
                        besetzt = true;
                }
                if (!besetzt) {
                    kiBaum = kiBaum.getRightTree();
                } else {
                    kiBaum = kiBaum.getLeftTree();
                }
                p = kiBaum.getContent();

                nextX = p.getX();
                nextY = p.getY();
                break;

            case 3:
                besetzt = true;
                for (int x = 0; x < spielfeld.getFieldCount(); x++) {
                    if (spielfeld.getValue(x, 2) == playerID)
                        besetzt = false;
                }
                if (besetzt) {
                    kiBaum = kiBaum.getRightTree();
                } else {
                    kiBaum = kiBaum.getLeftTree();
                }
                p = kiBaum.getContent();

                nextX = p.getX();
                nextY = p.getY();
                break;

            case 4:
                besetzt = true;
                p = kiBaum.getContent();
                int xFrei = p.getXFrei();
                int yFrei = p.getYFrei();
                if (!spielfeld.isEmpty(xFrei, yFrei))
                    besetzt = false;
                if (besetzt) {
                    kiBaum = kiBaum.getLeftTree();
                } else {
                    kiBaum = kiBaum.getRightTree();
                }
                p = kiBaum.getContent();

                nextX = p.getX();
                nextY = p.getY();
                break;

            case 5:
                p = kiBaum.getContent();
                xFrei = p.getXFrei();
                yFrei = p.getYFrei();
                besetzt = spielfeld.isEmpty(xFrei, yFrei);
                if (besetzt) {
                    kiBaum = kiBaum.getRightTree();
                } else {
                    kiBaum = kiBaum.getLeftTree();
                }
                p = kiBaum.getContent();

                nextX = p.getX();
                nextY = p.getY();
                break;

            case 6:
                besetzt = true;
                p = kiBaum.getContent();
                xFrei = p.getXFrei();
                yFrei = p.getYFrei();
                if (!spielfeld.isEmpty(xFrei, yFrei))
                    besetzt = false;
                if (besetzt) {
                    kiBaum = kiBaum.getLeftTree();
                } else {
                    kiBaum = kiBaum.getRightTree();
                }
                p = kiBaum.getContent();

                nextX = p.getX();
                nextY = p.getY();
                break;

            default:
                int max = 0;
                for (int x = 0; x < spielfeld.getFieldCount(); x++) {
                    for (int y = 0; y < spielfeld.getFieldCount(); y++) {
                        if (spielfeld.isEmpty(x, y)) {
                            spielfeld.setValue(kiID, x, y);
                            int profit = spielfeld.getPoints(kiID, 0);
                            spielfeld.clear(x, y);

                            spielfeld.setValue(playerID, x, y);
                            profit += spielfeld.getPoints(playerID, 0);
                            spielfeld.clear(x, y);

                            if (profit > max) {
                                max = profit;
                                nextX = x;
                                nextY = y;
                            } else if (profit == max && Math.random() > 0.5) {
                                nextX = x;
                                nextY = y;
                            }
                        }
                    }
                }
                break;
        }

        spielfeld.setValue(kiID, nextX, nextY);
    }

    private void baueSpielbaum() {
        Position p0 = new Position(2, 2, 0, 0);
        kiBaum.setContent(p0);

        BinaryTree<Position> l  = new BinaryTree<>();
        Position             p1 = new Position(2, 3, 2, 1);
        l.setContent(p1);

        BinaryTree<Position> r  = new BinaryTree<>();
        Position             p2 = new Position(3, 2, 1, 2);
        r.setContent(p2);

        BinaryTree<Position> ll = new BinaryTree<>();
        Position             p3 = new Position(2, 4, 1, 3);
        ll.setContent(p3);

        BinaryTree<Position> lr = new BinaryTree<>();
        Position             p4 = new Position(2, 1, 2, 0);
        lr.setContent(p4);

        BinaryTree<Position> rl = new BinaryTree<>();
        Position             p5 = new Position(4, 2, 3, 1);
        rl.setContent(p5);

        BinaryTree<Position> rr = new BinaryTree<>();
        Position             p6 = new Position(1, 2, 0, 2);
        rr.setContent(p6);

        l.setLeftTree(ll);
        l.setRightTree(lr);
        r.setLeftTree(rl);
        r.setRightTree(rr);

        kiBaum.setLeftTree(l);
        kiBaum.setRightTree(r);
    }

    private void baueSpielbaum2() {
        Position p0 = new Position(2, 2, 0, 0);
        kiBaum.setContent(p0);

        BinaryTree<Position> l  = new BinaryTree<>();
        Position             lp = new Position(0, 0, 0, 0);
        l.setContent(lp);

        BinaryTree<Position> r  = new BinaryTree<>();
        Position             rp = new Position(0, 0, 0, 0);
        r.setContent(rp);

        BinaryTree<Position> ll  = new BinaryTree<>();
        Position             llp = new Position(1, 1, 1, 2);
        ll.setContent(llp);

        BinaryTree<Position> lr  = new BinaryTree<>();
        Position             lrp = new Position(1, 3, 1, 2);
        lr.setContent(lrp);

        BinaryTree<Position> rl  = new BinaryTree<>();
        Position             rlp = new Position(3, 1, 3, 2);
        rl.setContent(rlp);

        BinaryTree<Position> rr  = new BinaryTree<>();
        Position             rrp = new Position(3, 3, 3, 2);
        rr.setContent(rrp);

        BinaryTree<Position> lll  = new BinaryTree<>();
        Position             lllp = new Position(1, 2, 1, 3);
        lll.setContent(lllp);

        BinaryTree<Position> llr  = new BinaryTree<>();
        Position             llrp = new Position(2, 1, 3, 1);
        llr.setContent(llrp);

        BinaryTree<Position> lrl  = new BinaryTree<>();
        Position             lrlp = new Position(1, 2, 1, 1);
        lrl.setContent(lrlp);

        BinaryTree<Position> lrr  = new BinaryTree<>();
        Position             lrrp = new Position(2, 3, 3, 3);
        lrr.setContent(lrrp);

        BinaryTree<Position> rll  = new BinaryTree<>();
        Position             rllp = new Position(3, 2, 3, 3);
        rll.setContent(rllp);

        BinaryTree<Position> rlr  = new BinaryTree<>();
        Position             rlrp = new Position(2, 1, 1, 1);
        rlr.setContent(rlrp);

        BinaryTree<Position> rrl  = new BinaryTree<>();
        Position             rrlp = new Position(3, 2, 3, 1);
        rrl.setContent(rrlp);

        BinaryTree<Position> rrr  = new BinaryTree<>();
        Position             rrrp = new Position(2, 3, 1, 3);
        rrr.setContent(rrrp);

        BinaryTree<Position> llll  = new BinaryTree<>();
        Position             llllp = new Position(1, 3, 0, 0);
        llll.setContent(llllp);

        BinaryTree<Position> lllr  = new BinaryTree<>();
        Position             lllrp = new Position(1, 0, 0, 0);
        lllr.setContent(lllrp);

        BinaryTree<Position> llrl  = new BinaryTree<>();
        Position             llrlp = new Position(3, 1, 0, 0);
        llrl.setContent(llrlp);

        BinaryTree<Position> llrr  = new BinaryTree<>();
        Position             llrrp = new Position(0, 1, 0, 0);
        llrr.setContent(llrrp);

        BinaryTree<Position> lrll  = new BinaryTree<>();
        Position             lrllp = new Position(1, 1, 0, 0);
        lrll.setContent(lrllp);

        BinaryTree<Position> lrlr  = new BinaryTree<>();
        Position             lrlrp = new Position(1, 4, 0, 0);
        lrlr.setContent(lrlrp);

        BinaryTree<Position> lrrl  = new BinaryTree<>();
        Position             lrrlp = new Position(3, 3, 0, 0);
        lrrl.setContent(lrrlp);

        BinaryTree<Position> lrrr  = new BinaryTree<>();
        Position             lrrrp = new Position(0, 3, 0, 0);
        lrrr.setContent(lrrrp);

        BinaryTree<Position> rlll  = new BinaryTree<>();
        Position             rlllp = new Position(3, 3, 0, 0);
        rlll.setContent(rlllp);

        BinaryTree<Position> rllr  = new BinaryTree<>();
        Position             rllrp = new Position(3, 0, 0, 0);
        rllr.setContent(rllrp);

        BinaryTree<Position> rlrl  = new BinaryTree<>();
        Position             rlrlp = new Position(2, 3, 2, 1);
        rlrl.setContent(rlrlp);

        BinaryTree<Position> rlrr  = new BinaryTree<>();
        Position             rlrrp = new Position(4, 0, 0, 0);
        rlrr.setContent(rlrrp);

        BinaryTree<Position> rrll  = new BinaryTree<>();
        Position             rrllp = new Position(3, 1, 0, 0);
        rrll.setContent(rrllp);

        BinaryTree<Position> rrlr  = new BinaryTree<>();
        Position             rrlrp = new Position(3, 4, 0, 0);
        rrlr.setContent(rrlrp);

        BinaryTree<Position> rrrl  = new BinaryTree<>();
        Position             rrrlp = new Position(1, 3, 0, 0);
        rrrl.setContent(rrrlp);

        BinaryTree<Position> rrrr  = new BinaryTree<>();
        Position             rrrrp = new Position(4, 3, 0, 0);
        rrrr.setContent(rrrrp);

        lll.setLeftTree(llll);
        lll.setRightTree(lllr);
        llr.setLeftTree(llrl);
        llr.setRightTree(llrr);
        lrl.setLeftTree(lrll);
        lrl.setRightTree(lrlr);
        lrr.setLeftTree(lrrl);
        lrr.setRightTree(lrrr);
        rll.setLeftTree(rlll);
        rll.setRightTree(rllr);
        rlr.setLeftTree(rlrl);
        rlr.setRightTree(rlrr);
        rrl.setLeftTree(rrll);
        rrl.setRightTree(rrlr);
        rrr.setLeftTree(rrrl);
        rrr.setRightTree(rrrr);

        ll.setLeftTree(lll);
        ll.setRightTree(llr);
        rl.setLeftTree(rll);
        rl.setRightTree(rlr);
        lr.setLeftTree(lrl);
        lr.setRightTree(lrr);
        rr.setLeftTree(rrl);
        rr.setRightTree(rrr);

        l.setLeftTree(ll);
        l.setRightTree(lr);
        r.setLeftTree(rl);
        r.setRightTree(rr);

        kiBaum.setLeftTree(l);
        kiBaum.setRightTree(r);
    }
}