package com.example.user.zzzmitview;

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

/**
 * @author Benedikt
 * Erstelldatum: 29.12.2017
 */

public class Drawer extends View {

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
    int spielrunde = 1;

    public Drawer(Context context) {
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

    public Drawer(Context context, @Nullable AttributeSet attrs) {
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

    public Drawer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        canvasBreite = getWidth();
        canvasHoehe = getHeight();
        kastenBreite = canvasBreite / 5;

        erstelleRaster(canvas); //Raster wird auf Canvas gezeichnet

        punkteAnzeiger(canvas); //Punkteanzeiger wird auf Canvas gezeichnet

        ziege(canvas); //Wenn Spiel vorbei, dann Ziege

        if (zug == true) {
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
                        if (spielrunde % 2 != 0)
                            setFeld(xArray, yArray, 1);
                        else if (spielrunde % 2 == 0)
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

        canvas.drawText("Spieler 1 (O): ", 0 + 50, begrenzung + begrenzung / 3, p);
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

        for (int i = 0; i < 6; i++) {
            int k = i * kastenBreite;
            canvas.drawLine(0, k, canvasBreite, k, black);
            begrenzung = k;
        }

        for (int i = 0; i < 6; i++) {
            int k = i * kastenBreite;
            canvas.drawLine(k, 0, k, begrenzung, black);
        }
    }

    public int[][] getFeld() {
        return feld;
    }
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