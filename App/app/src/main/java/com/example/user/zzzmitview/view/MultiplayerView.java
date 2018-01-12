package com.example.user.zzzmitview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.activity.SpielfeldActivity;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;

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

    private SpielfeldActivity activity;

    private Spielfeld spielfeld;
    private Spieler[] spieler;
    private int       current;

    private Paint paint;

    private Bitmap bitmapBackground;
    private Bitmap bitmapFields;

    private boolean isInitialized;

    private int fieldWidth;
    private int stroke;

    public MultiplayerView(Context context) {
        super(context);

        this.isInitialized = false;
    }

    public MultiplayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.isInitialized = false;
    }

    public MultiplayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.isInitialized = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isInitialized) {
            initialize();
        }

        canvas.drawBitmap(bitmapBackground, 0, 0, paint);
        canvas.drawBitmap(bitmapFields, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX() / fieldWidth);
        if (x >= spielfeld.getBreite())
            x = spielfeld.getBreite() - 1;

        int y = (int) (event.getY() / fieldWidth);
        if (y >= spielfeld.getBreite())
            y = spielfeld.getBreite() - 1;

        if (spielfeld.leer(x, y)) {
            spielfeld.setze(spieler[current].getId(), x, y);

            activity.resetPunkte(current);

            current++;
            if (current == spieler.length) {
                current = 0;
            }
        }

        drawFields();
        invalidate();

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    private void initialize() {
        current = 0;

        stroke = 3;

        fieldWidth = getWidth() / spielfeld.getBreite();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        drawBackground();
        drawFields();

        isInitialized = true;
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

    public void setActivity(SpielfeldActivity activity) {
        this.activity = activity;
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
        isInitialized = false;
        invalidate();
    }

    public void setSpieler(Spieler[] spieler) {
        this.spieler = spieler;
    }
}