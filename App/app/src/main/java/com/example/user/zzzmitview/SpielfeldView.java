package com.example.user.zzzmitview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;

public class SpielfeldView extends View {
    private final Spielfeld spielfeld;

    private Paint paint;

    private Bitmap bitmapBackground;

    private boolean isInitialized;

    private int height, width;
    private int baselineY;
    private int fieldWidth;
    private int stroke;

    public SpielfeldView(Context context) {
        super(context);

        this.spielfeld = new Spielfeld(2);
        this.isInitialized = false;
    }

    public SpielfeldView(Context context, Spielfeld spielfeld) {
        super(context);

        this.spielfeld = spielfeld;
        this.isInitialized = false;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInitialized) {
            initialize();
        }

        canvas.drawBitmap(bitmapBackground, 0, baselineY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }

    private void initialize() {
        height = getHeight();
        width = getWidth();

        stroke = 5;

        baselineY = height / 128;

        fieldWidth = width / spielfeld.getBreite();

        paint = new Paint();

        drawBackground();
    }

    private void drawBackground() {
        paint.setStrokeWidth(stroke);
        paint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));

        bitmapBackground = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvasBackground = new Canvas(bitmapBackground);

        for (int i = 0; i <= spielfeld.getBreite(); i++) {
            canvasBackground.drawLine(fieldWidth * i, 0, fieldWidth * i, fieldWidth * spielfeld.getBreite(), paint);
            canvasBackground.drawLine(0, fieldWidth * i, fieldWidth * spielfeld.getBreite(), fieldWidth * i, paint);
        }
    }


}