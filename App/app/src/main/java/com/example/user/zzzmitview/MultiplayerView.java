package com.example.user.zzzmitview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MultiplayerView extends View {
    private static final int[] colors = {
            android.R.color.transparent,
            R.color.materialYellow,
            R.color.materialGreen,
            R.color.materialRed,
            R.color.materialBlue,
            R.color.materialOrange,
            R.color.materialPurple,
            R.color.materialCyan,
            R.color.materialPink
    };

    private final Spielfeld spielfeld;

    private Paint paint;

    private Bitmap bitmapBackground;
    private Bitmap bitmapFields;

    private boolean isInitialized;

    private int height, width;
    private int baselineY;
    private int fieldWidth;
    private int stroke;

    public MultiplayerView(Context context) {
        super(context);

        this.spielfeld = new Spielfeld(2);
        this.isInitialized = false;

        for (int i = 0; i < 10; i++) {
            spielfeld.setze(((int) (1 + Math.random() * 7)), ((int) (Math.random() * spielfeld.getBreite())), ((int) (Math.random() * spielfeld.getBreite())));
        }
    }

    public MultiplayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.spielfeld = new Spielfeld(2);
        this.isInitialized = false;

        for (int i = 0; i < 10; i++) {
            spielfeld.setze(((int) (1 + Math.random() * 7)), ((int) (Math.random() * spielfeld.getBreite())), ((int) (Math.random() * spielfeld.getBreite())));
        }
    }

    public MultiplayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.spielfeld = new Spielfeld(2);
        this.isInitialized = false;

        for (int i = 0; i < 10; i++) {
            spielfeld.setze(((int) (1 + Math.random() * 7)), ((int) (Math.random() * spielfeld.getBreite())), ((int) (Math.random() * spielfeld.getBreite())));
        }
    }

    public MultiplayerView(Context context, Spielfeld spielfeld) {
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
        canvas.drawBitmap(bitmapFields, 0, baselineY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        int x = (int) (eventX / fieldWidth);
        int y = (int) (eventY / fieldWidth);

        spielfeld.setze(1, x, y);

        invalidate();

        return super.onTouchEvent(event);
    }

    private void initialize() {
        height = getHeight();
        width = getWidth();

        stroke = 3;

        baselineY = height / 128;

        fieldWidth = width / spielfeld.getBreite();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        drawBackground();
        drawFields();
    }

    private void drawBackground() {
        Paint paint = new Paint();
        paint.setStrokeWidth(stroke);
        paint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        paint.setStyle(Paint.Style.STROKE);

        bitmapBackground = Bitmap.createBitmap(
                fieldWidth * spielfeld.getBreite(),
                fieldWidth * spielfeld.getBreite(),
                Bitmap.Config.ARGB_4444
        );
        Canvas canvas = new Canvas(bitmapBackground);

        for (int i = 0; i <= spielfeld.getBreite(); i++) {
            canvas.drawLine(fieldWidth * i, 0, fieldWidth * i, fieldWidth * spielfeld.getBreite(), paint);
            canvas.drawLine(0, fieldWidth * i, fieldWidth * spielfeld.getBreite(), fieldWidth * i, paint);
        }
    }

    private void drawFields() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        bitmapFields = Bitmap.createBitmap(
                fieldWidth * spielfeld.getBreite(),
                fieldWidth * spielfeld.getBreite(),
                Bitmap.Config.ARGB_4444
        );
        Canvas canvas = new Canvas(bitmapFields);

        for (int i = 0; i < spielfeld.getBreite(); i++) {
            for (int j = 0; j < spielfeld.getBreite(); j++) {
                paint.setColor(ContextCompat.getColor(getContext(), colors[spielfeld.gib(i, j)]));
                canvas.drawRect(fieldWidth * i + stroke, fieldWidth * j + stroke, fieldWidth * (i + 1) - stroke, fieldWidth * (j + 1) - stroke, paint);
            }
        }
    }
}