package com.example.user.zzzmitview.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Spielfeld;


public class StoryView extends View {

    private Spielfeld spielfeld;
    public int tapZaehler = 0;
    public Bitmap[] d1 = new Bitmap[7];
    private int[][] feld = new int[5][5];
    int canvasBreite, canvasHoehe, kastenBreite, xWert = -1, yWert = -1, begrenzung, liniendicke = 5, spielrunde = 1, Zx, Zy;
    final int groesse = 40;
    boolean zug = false, feldAnzeigen = false, switch1 = false;
    Bitmap ziegenfreund = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ziegenfreund), 238, 212, false);
    Bitmap ziegengesicht = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ziegengesicht), 238, 212, false);
    private SpielfeldView spielfeldView;

    public StoryView(Context context) {
        super(context);
        erstelleTexte();
        Zx = 0;
    }

    public StoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        erstelleTexte();
        Zx = 0;
    }

    public StoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        erstelleTexte();
        Zx = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //feldMalen(canvas);
        canvasBreite = canvas.getWidth();
        canvasHoehe = canvas.getHeight();
        kastenBreite = canvasBreite / 5;
        sq1(canvas);
        if (Zx < canvasBreite / 2) {
            invalidate();
        }
    }

    public void sq1(Canvas canvas) {
        Log.d("TAG", "HALLO");
        if (Zx < canvasBreite / 2) {
            Zy = getHeight() / 8 * 7;
            Zx += 5;
            canvas.drawBitmap(ziegenfreund, Zx, Zy, new Paint());
        } else {
            canvas.drawBitmap(ziegenfreund, Zx, Zy, new Paint());

            if (tapZaehler < d1.length) {
                Log.d("TAG", "dialog!!!");
                Log.d("TAG", "tapZaehler = " + tapZaehler);
                erstelleDialog(tapZaehler, canvas, d1);
            } else {
                Log.d("TAG", "rammbock!!!");
                rammbock1();
            }
        }

    }

    public void erstelleDialog(int pDialogID, Canvas canvas, Bitmap[] array) {
        Bitmap aktuell = Bitmap.createScaledBitmap(array[pDialogID], canvasBreite, canvasHoehe / 3, false);
        canvas.drawBitmap(aktuell, 0, canvasHoehe - ziegenfreund.getHeight() / 7 * 8 - aktuell.getHeight(), new Paint());
    }

    private void drawRectText(String text, Canvas canvas, Rect r) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(groesse);
        p.setTextAlign(Paint.Align.CENTER);
        int width = r.width();

        int numOfChars = p.breakText(text, true, width, null);
        int start = (text.length() - numOfChars) / 2;
        canvas.drawText(text, start, start + numOfChars, r.exactCenterX(), r.exactCenterY(), p);
    }

    public void erstelleDialog(String eingabe, Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.argb(100, 0, 0, 128));
        p.setStyle(Paint.Style.FILL);

        Paint p2 = new Paint();
        p2.setColor(Color.WHITE);

        final Rect r = new Rect();
        r.set(50, canvasHoehe - 200, canvasBreite - 50, canvasHoehe - 50);
        canvas.drawRect(r, p);
        String a[] = baueDialog(eingabe);
        drawRectText(a[tapZaehler], canvas, r);
    }

    public int dialogLaenge() {
        Paint p = new Paint();
        p.setTextSize(groesse);
        String s = "";
        while (canvasBreite > p.measureText(s) + 200) {
            s = s + "_";
        }
        return s.length();
    }

    public String[] baueDialog(String pDialog) { //baut aus einem 10 dimensionengroßen Eingabestring einen korrekt geparsten String-Array
        int laenge = pDialog.length();
        int letztesZeichen = 0;
        int z = 1;
        String[] parsedString = new String[10];
        while (letztesZeichen + dialogLaenge() < laenge) {
            parsedString[z] = pDialog.substring(letztesZeichen, dialogLaenge() * z);
            letztesZeichen = dialogLaenge() * z;
            z++;
        }
        parsedString[z] = pDialog.substring(letztesZeichen, laenge);

        for (int i = 0; i < 10; i++) {
            if (parsedString[i] == "") {
                parsedString[i] = " ";
            }
        }

        return parsedString;
    }

    public void erstelleTexte() {
        d1[0] = BitmapFactory.decodeResource(getResources(), R.drawable.s0);
        d1[1] = BitmapFactory.decodeResource(getResources(), R.drawable.s1);
        d1[2] = BitmapFactory.decodeResource(getResources(), R.drawable.s2);
        d1[3] = BitmapFactory.decodeResource(getResources(), R.drawable.s3);
        d1[4] = BitmapFactory.decodeResource(getResources(), R.drawable.s4);
        d1[5] = BitmapFactory.decodeResource(getResources(), R.drawable.s5);
        d1[6] = BitmapFactory.decodeResource(getResources(), R.drawable.s6);
    }

    public void rammbock1() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          try {
                                              spielfeld.setValue(1, 0, 0);
                                              spielfeldView.invalidate();
                                              Thread.sleep(500);
                                              spielfeld.setValue(1, 1, 0);
                                              spielfeldView.invalidate();
                                              Thread.sleep(500);
                                              spielfeld.setValue(1, 2, 1);
                                              spielfeldView.invalidate();
                                              Thread.sleep(500);
                                              spielfeld.setValue(1, 3, 1);
                                              spielfeldView.invalidate();
                                              Thread.sleep(1500);
                                              spielfeld.clear();
                                              switch1 = true;
                                              spielfeldView.invalidate();
                                          } catch (InterruptedException e) {
                                              e.printStackTrace();
                                          }
                                      }
                                  },
                500
        );
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

    public void setSpielfeldView(SpielfeldView spielfeldView) {
        this.spielfeldView = spielfeldView;
    }
}


