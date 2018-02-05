package com.example.user.zzzmitview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.ArtificialIntelligence;

public class StoryView extends View {
    public static final int TUTORIAL_RAMMBOCK = 1;
    public static final int TUTORIAL_KORRUPTE = 2;
    public static final int TUTORIAL_PAPA     = 3;
    public static final int TUTORIAL_MAMA     = 4;
    private static int mode;

    private TutorialView spielfeldView;

    private boolean isInitialized;

    private Paint paint;

    public int dialogIndex;

    public Bitmap[] dialogBitmaps = new Bitmap[7];

    private int ziegeX;
    private int ziegeY;

    final int groesse = 40;

    Bitmap ziegenfreund  = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                    getResources(),
                    R.drawable.ziegenfreund
            ),
            238,
            212,
            false
    );
    Bitmap ziegengesicht = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                    getResources(),
                    R.drawable.ziegengesicht
            ),
            238,
            212,
            false
    );

    public StoryView(Context context) {
        super(context);
    }

    public StoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static int getCurrentMode() {
        return mode;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInitialized) {
            initialize();
        }

        if (ziegeX < getWidth() / 2) {
            ziegeX += 5;
            invalidate();
        } else {
            if (dialogIndex < dialogBitmaps.length) {
                canvas.drawBitmap(
                        dialogBitmaps[dialogIndex],
                        0,
                        getHeight() - ziegenfreund.getHeight() / 7 * 8 - dialogBitmaps[dialogIndex].getHeight(),
                        paint
                );
            } else {
                setzeRammbock();
            }
        }
        canvas.drawBitmap(
                ziegenfreund,
                ziegeX,
                ziegeY,
                paint
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ziegeX < getWidth() / 2 || event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        dialogIndex++;
        invalidate();
        return true;
    }

    private void initialize() {
        paint = new Paint();

        erstelleTexte();

        ziegeX = 0;
        ziegeY = getHeight() / 8 * 7;

        dialogIndex = 0;

        mode = TUTORIAL_RAMMBOCK;

        isInitialized = true;
    }

    public void setzeRammbock() {
        setVisibility(GONE);

        final Handler h = new Handler();

        final Runnable r5 = new Runnable() {
            @Override
            public void run() {
                spielfeldView.spielfeld.clear();
                spielfeldView.invalidate();
                spielfeldView.start();
            }
        };
        final Runnable r4 = new Runnable() {
            @Override
            public void run() {
                spielfeldView.spielfeld.setValue(ArtificialIntelligence.playerID, 3, 1);
                spielfeldView.invalidate();
                h.postDelayed(r5, 2500);
            }
        };
        final Runnable r3 = new Runnable() {
            @Override
            public void run() {
                spielfeldView.spielfeld.setValue(ArtificialIntelligence.playerID, 2, 1);
                spielfeldView.invalidate();
                h.postDelayed(r4, 500);
            }
        };
        final Runnable r2 = new Runnable() {
            @Override
            public void run() {
                spielfeldView.spielfeld.setValue(ArtificialIntelligence.playerID, 1, 0);
                spielfeldView.invalidate();
                h.postDelayed(r3, 500);
            }
        };
        final Runnable r1 = new Runnable() {
            @Override
            public void run() {
                spielfeldView.spielfeld.setValue(ArtificialIntelligence.playerID, 0, 0);
                spielfeldView.invalidate();
                h.postDelayed(r2, 500);
            }
        };

        h.postDelayed(r1, 250);
    }

    public void setSpielfeldView(TutorialView spielfeldView) {
        this.spielfeldView = spielfeldView;
    }

    public void erstelleTexte() {
        dialogBitmaps[0] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s0
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
        dialogBitmaps[1] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s1
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
        dialogBitmaps[2] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s2
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
        dialogBitmaps[3] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s3
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
        dialogBitmaps[4] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s4
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
        dialogBitmaps[5] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s5
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
        dialogBitmaps[6] = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(
                        getResources(),
                        R.drawable.s6
                ),
                getWidth(),
                getHeight() / 3,
                false
        );
    }
//    public void erstelleDialog(String eingabe, Canvas canvas) {
//        Paint p = new Paint();
//        p.setColor(Color.argb(100, 0, 0, 128));
//        p.setStyle(Paint.Style.FILL);
//
//        Paint p2 = new Paint();
//        p2.setColor(Color.WHITE);
//
//        final Rect r = new Rect();
//        r.set(50, getHeight() - 200, getWidth() - 50, getHeight() - 50);
//        canvas.drawRect(r, p);
//        String a[] = baueDialog(eingabe);
//        drawRectText(a[dialogIndex], canvas, r);
//    }
//
//    public int dialogLaenge() {
//        Paint p = new Paint();
//        p.setTextSize(groesse);
//        String s = "";
//        while (getWidth() > p.measureText(s) + 200) {
//            s = s + "_";
//        }
//        return s.length();
//    }
//
//    public String[] baueDialog(String pDialog) { //baut aus einem 10 dimensionengroßen Eingabestring einen korrekt geparsten String-Array
//        int      laenge         = pDialog.length();
//        int      letztesZeichen = 0;
//        int      z              = 1;
//        String[] parsedString   = new String[10];
//        while (letztesZeichen + dialogLaenge() < laenge) {
//            parsedString[z] = pDialog.substring(letztesZeichen, dialogLaenge() * z);
//            letztesZeichen = dialogLaenge() * z;
//            z++;
//        }
//        parsedString[z] = pDialog.substring(letztesZeichen, laenge);
//
//        for (int i = 0; i < 10; i++) {
//            if (parsedString[i].equals("")) {
//                parsedString[i] = " ";
//            }
//        }
//
//        return parsedString;
//    }
//
//    private void drawRectText(String text, Canvas canvas, Rect r) {
//        Paint p = new Paint();
//        p.setColor(Color.WHITE);
//        p.setTextSize(groesse);
//        p.setTextAlign(Paint.Align.CENTER);
//        int width = r.width();
//
//        int numOfChars = p.breakText(text, true, width, null);
//        int start      = (text.length() - numOfChars) / 2;
//        canvas.drawText(text, start, start + numOfChars, r.exactCenterX(), r.exactCenterY(), p);
//    }
}