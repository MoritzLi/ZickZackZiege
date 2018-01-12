package com.example.user.zzzmitview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Auswertung;
import com.example.user.zzzmitview.utility.BinaryTree;
import com.example.user.zzzmitview.utility.Position;
import com.example.user.zzzmitview.utility.Umrechner;

/**
 * Created by User on 02.01.2018.
 */

public class SingleplayerView extends View {

    Umrechner umrechner = new Umrechner();
    int[][]   feld      = new int[5][5];
    boolean   zug       = false, ziege = false;
    int xWert = -1, yWert = -1;
    Bitmap bmp;
    int    x, y;
    private final int liniendicke = 5;
    int canvasBreite;
    int canvasHoehe;
    int begrenzung;
    int kastenBreite;
    int         spielrunde  = 1;
    Einzelspiel einzelspiel = new Einzelspiel(2);

    public SingleplayerView(Context context) {
        super(context);
        for (int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                feld[i][t] = 0;
            }
        }

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ziege);
        x = 0;
        y = 0;
    }

    public SingleplayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        for (int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                feld[i][t] = 0;
            }
        }

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ziege);
        x = 0;
        y = 0;
    }

    public SingleplayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for (int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                feld[i][t] = 0;
            }
        }

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ziege);
        x = 0;
        y = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasBreite = canvas.getWidth();
        canvasHoehe = canvas.getHeight();
        kastenBreite = canvasBreite / 5;

        erstelleRaster(canvas); //Raster wird auf Canvas gezeichnet

        punkteAnzeiger(canvas); //Punkteanzeiger wird auf Canvas gezeichnet

        ziege(canvas); //Wenn Spiel vorbei, dann Ziege

        if (spielrunde % 2 != 0) {
            einzelspiel.einzelspieler(spielrunde);
            setFeld(einzelspiel.getEinzelXKor(), einzelspiel.getEinzelYKor(), 1);
        } else if (zug && spielrunde % 2 == 0) {
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(40);

            if (!spielBeendet()) {
                int yArray = umrechner.toYarray(getYstorage(), kastenBreite);
                int xArray = umrechner.toXarray(getXstorage(), kastenBreite);

                if (yArray != -1) {
                    canvas.drawText(Integer.toString(xArray)
                            , canvasBreite / 2, begrenzung + 60, p);
                    canvas.drawText(Integer.toString(yArray)
                            , canvasBreite / 2, begrenzung + 120, p);

                    if (feld[xArray][yArray] == 0) {
                        if (spielrunde % 2 == 0)
                            setFeld(xArray, yArray, 2);
                        spielrunde++;
                    }
                    zug = false;
                }
            } else {
                neuesSpiel();
            }
        }

        maleFeld(canvas);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        toStorage(x, y);
        return super.onTouchEvent(event);
    }

    public void toStorage(int pX, int pY) {
        xWert = pX;
        yWert = pY;
        zug = true;
    }

    public void drawX(int pX, int pY, Canvas canvas) {
        Paint spieler1 = new Paint();
        spieler1.setColor(Color.BLUE);
        spieler1.setStrokeWidth(5);

        canvas.drawLine(umrechner.getEcken(pX, pY, kastenBreite, "xLinksOben"),
                umrechner.getEcken(pX, pY, kastenBreite, "yLinksOben"),
                umrechner.getEcken(pX, pY, kastenBreite, "xRechtsUnten"),
                umrechner.getEcken(pX, pY, kastenBreite, "yRechtsUnten"), spieler1);
        canvas.drawLine(umrechner.getEcken(pX, pY, kastenBreite, "xRechtsOben"),
                umrechner.getEcken(pX, pY, kastenBreite, "yRechtsOben"),
                umrechner.getEcken(pX, pY, kastenBreite, "xLinksUnten"),
                umrechner.getEcken(pX, pY, kastenBreite, "yLinksUnten"), spieler1);
    }

    public void maleFeld(Canvas canvas) {
        for (int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                if (feld[i][t] == 2) {
                    drawX(i, t, canvas);
                } else if (feld[i][t] == 1) {
                    drawO(i, t, canvas);
                }
            }
        }
    }

    public void setFeld(int pX, int pY, int eingabe) {
        feld[pX][pY] = eingabe;
    }

    public int getXstorage() {
        return xWert;
    }

    public int getYstorage() {
        return yWert;
    }

    public void drawO(int pX, int pY, Canvas canvas) {
        Paint spieler2 = new Paint();
        spieler2.setColor(Color.RED);
        spieler2.setStyle(Paint.Style.STROKE);
        spieler2.setStrokeWidth(5);
        canvas.drawCircle(umrechner.getEcken(pX, pY, kastenBreite, "xMitte"), umrechner.getEcken(pX, pY, kastenBreite, "yMitte"), umrechner.getEcken(pX, pY, kastenBreite, "kastenbreiteRadius"), spieler2);
    }

    public boolean spielBeendet() {
        for (int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                if (feld[i][t] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void auszaehlen(Canvas canvas) {
        if (spielBeendet()) {
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(50);
            canvas.drawText("Spieler 1 (O): " + Integer.toString(Auswertung.auswertung(1, feld)), canvasBreite / 2, begrenzung + 100, p);
            canvas.drawText("Spieler 2 (X): " + Integer.toString(Auswertung.auswertung(2, feld)), canvasBreite / 2, begrenzung + 200, p);
            canvas.drawText("Spiel beendet"
                    , canvasBreite / 2, begrenzung + 60, p);
        }
    }

    public void ziege(Canvas canvas) {
        if (spielBeendet()) {
            canvas.drawBitmap(bmp, x, y, new Paint());
            x += 10;
            y += 10;
        }
    }

    public void punkteAnzeiger(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(15);
        p.setTextSize(40);
        canvas.drawLine(canvasBreite / 2, begrenzung + begrenzung / 3, canvasBreite / 2, canvasHoehe, p);
        canvas.drawLine(0, begrenzung + begrenzung / 3 + 40, canvasBreite, begrenzung + begrenzung / 3 + 40, p);

        canvas.drawText("Spieler 1 (O): ", 50, begrenzung + begrenzung / 3, p);
        canvas.drawText("Spieler 1 (X): ", canvasBreite / 2 + 50, begrenzung + begrenzung / 3, p);

        canvas.drawText(Integer.toString(Auswertung.auswertung(1, feld)), 0 + 50, begrenzung + begrenzung / 2, p);
        canvas.drawText(Integer.toString(Auswertung.auswertung(2, feld)), canvasBreite / 2 + 50, begrenzung + begrenzung / 2, p);
    }

    public void neuesSpiel() { //erstellt neues Spiel

        zug = false;
        spielrunde = 1;
        x = 0;
        y = 0;

        for (int i = 0; i < 5; i++) {
            for (int t = 0; t < 5; t++) {
                feld[i][t] = 0;
            }
        }
    }

    public void erstelleRaster(Canvas canvas) { //erstellt Raster
        Paint black = new Paint();
        black.setColor(Color.BLACK);
        black.setStrokeWidth(liniendicke);

        for (int i = 1; i < 6; i++) {
            int k = i * kastenBreite;
            canvas.drawLine(0, k, canvasBreite, k, black);
            begrenzung = k;
        }
        for (int i = 1; i < 6; i++) {
            int k = i * kastenBreite;
            canvas.drawLine(k, 0, k, begrenzung, black);
        }
    }

    public int[][] getFeld() {
        return feld;
    }

    public boolean feldFrei(int pX, int pY) {
        if (feld[pX][pY] == 0)
            return true;
        else
            return false;
    }
    //Unterklasse KI

    public class Einzelspiel {
        int einzelXKor;
        int einzelYKor;
        BinaryTree<Position> kiBaum = new BinaryTree<>();

        /**
         * Constructor for objects of class Einzelspiel
         */
        Einzelspiel(int pSpieleranzahl) {

        }

        void einzelspieler(int pSpielzug) {
            spielrunde++;
            // switch case mit baum für die ersten 4 züge der Ki
            switch (pSpielzug) {
                case 1:
                    baueSpielbaum();
                    Position p = kiBaum.getContent();
                    einzelXKor = p.getX();
                    einzelYKor = p.getY();
                    break;
                case 3:
                    p = kiBaum.getContent();
                    boolean besetzt = true;
                    for (int xKor = 0; xKor < 5; xKor++) {
                        if (feld[xKor][2] == 2)
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
                    besetzt = feldFrei(xFrei, yFrei);
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
                            if (feldFrei(xKor, yKor)) {
                                setFeld(xKor, yKor, 1);
                                ki[xKor][yKor] = Auswertung.auswertung(1, getFeld());
                                setFeld(xKor, yKor, 2);
                                ki[xKor][yKor] = ki[xKor][yKor] + Auswertung.auswertung(2, getFeld());
                                setFeld(xKor, yKor, 0);
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

        void baueSpielbaum() {
            Position p0 = new Position(2, 2, 0, 0);
            kiBaum.setContent(p0);

            BinaryTree<Position> l  = new BinaryTree();
            Position             p1 = new Position(2, 3, 2, 1);
            l.setContent(p1);

            BinaryTree<Position> r  = new BinaryTree();
            Position             p2 = new Position(3, 2, 1, 2);
            r.setContent(p2);

            BinaryTree<Position> ll = new BinaryTree();
            Position             p3 = new Position(2, 4, 1, 3);
            ll.setContent(p3);

            BinaryTree<Position> lr = new BinaryTree();
            Position             p4 = new Position(2, 1, 2, 0);
            lr.setContent(p4);

            BinaryTree<Position> rl = new BinaryTree();
            Position             p5 = new Position(4, 2, 3, 1);
            rl.setContent(p5);

            BinaryTree<Position> rr = new BinaryTree();
            Position             p6 = new Position(1, 2, 0, 2);
            rr.setContent(p6);

            l.setLeftTree(ll);
            l.setRightTree(lr);
            r.setLeftTree(rl);
            r.setRightTree(rr);

            kiBaum.setLeftTree(l);
            kiBaum.setRightTree(r);
        }

        int getEinzelXKor() {
            return einzelXKor;
        }

        int getEinzelYKor() {
            return einzelYKor;
        }
    }

    public void computer(int pX, int pY) {
        if (spielrunde == 1) {
            einzelspiel.einzelspieler(1);
            setFeld(einzelspiel.getEinzelXKor(), einzelspiel.getEinzelYKor(), 1);
            spielrunde++;
        }
        if (feld[pX][pY] == 0) {
            setFeld(pX, pY, 2);
            einzelspiel.einzelspieler(spielrunde);
            setFeld(einzelspiel.getEinzelXKor(), einzelspiel.getEinzelYKor(), 1);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    System.out.print(feld[j][i] + "  ");
                }
                System.out.println();
            }
            spielrunde++;
        }
    }

    /*public void computerZug(){
            for(int i = 0;i<5;i++){
                for(int j = 0; j<5;j++){
                    if(feld[i][j] == 0){
                        set(i,j);
                        b[i][j].setBackground(Color.red);
                        int x = e.getEinzelXKor();
                        int y = e.getEinzelYKor();
                        b[x][y].setBackground(Color.blue);
                        tIndexI.setText(""+e.feld.auswertung(1));
                        tIndexJ.setText(""+e.feld.auswertung(2));
                    }
                }

    } */
}

/**
 * Rect ourRect = new Rect();
 * ourRect.set(0, 0 , canvas.getWidth(), canvas.getHeight()/2);
 * <p>
 * Paint blue = new Paint();
 * blue.setColor(Color.BLUE);
 * blue.setStyle(Paint.Style.FILL);
 * <p>
 * canvas.drawRect(ourRect, blue);
 * <p>
 * if(x < canvas.getWidth())
 * x += 10;
 * else
 * x = 0;
 * if (y < canvas.getHeight())
 * y += 10;
 * else
 * y=0;
 * <p>
 * canvas.drawBitmap(bmp, x, y, new Paint());
 */