package com.example.user.zzzmitview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.activity.SpielfeldActivity;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;

public class SpielfeldView extends View {
    static final int[] colors = {
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

    SpielfeldActivity activity;

    Spielfeld spielfeld;
    Spieler[] spieler;

    private Paint paint;

    private Bitmap bitmapBackground;
    private Bitmap bitmapFields;

    private boolean isInitialized;

    int fieldWidth;
    private int stroke;

    public SpielfeldView(Context context) {
        super(context);
        isInitialized = false;
    }

    public SpielfeldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isInitialized = false;
    }

    public SpielfeldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isInitialized = false;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @Override
    public void invalidate() {
        drawFields();
        super.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();

        return super.onTouchEvent(event);
    }

    @CallSuper
    void initialize() {
        fieldWidth = getWidth() / 5;
        stroke = 3;

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
                fieldWidth * spielfeld.getFieldCount(),
                fieldWidth * spielfeld.getFieldCount(),
                Bitmap.Config.ARGB_4444
        );
        Canvas canvas = new Canvas(bitmapBackground);

        for (int i = 0; i <= spielfeld.getFieldCount(); i++) {
            canvas.drawLine(fieldWidth * i, 0, fieldWidth * i, fieldWidth * spielfeld.getFieldCount(), paint);
            canvas.drawLine(0, fieldWidth * i, fieldWidth * spielfeld.getFieldCount(), fieldWidth * i, paint);
        }
    }

    private void drawFields() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        bitmapFields = Bitmap.createBitmap(
                fieldWidth * spielfeld.getFieldCount(),
                fieldWidth * spielfeld.getFieldCount(),
                Bitmap.Config.ARGB_4444
        );
        Canvas canvas = new Canvas(bitmapFields);

        for (int i = 0; i < spielfeld.getFieldCount(); i++) {
            for (int j = 0; j < spielfeld.getFieldCount(); j++) {
                paint.setColor(ContextCompat.getColor(getContext(), SpielfeldView.colors[spielfeld.getValue(i, j)]));
                canvas.drawRect(fieldWidth * i + stroke, fieldWidth * j + stroke, fieldWidth * (i + 1) - stroke, fieldWidth * (j + 1) - stroke, paint);
            }
        }
    }

    public void setActivity(SpielfeldActivity activity) {
        this.activity = activity;
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

    public void setSpieler(Spieler[] spieler) {
        this.spieler = spieler;
    }
}
