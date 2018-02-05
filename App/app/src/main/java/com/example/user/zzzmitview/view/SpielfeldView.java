package com.example.user.zzzmitview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Spieler;
import com.example.user.zzzmitview.utility.Spielfeld;

/**
 * View für das Spielfeld
 * Sub-Klassen müssen das setzen des Feldes in "onTouchEvent" selbst implementieren
 * Erwartet, dass spieler, spielfeld und listener gesetzt werden vor dem ersten Abbilden der View
 */
public abstract class SpielfeldView extends View {
    static final int[] colors = {
            android.R.color.transparent,
            R.color.materialRed,
            R.color.materialBlue,
            R.color.materialYellow,
            R.color.materialGreen,
            R.color.materialOrange,
            R.color.materialPurple,
            R.color.materialCyan,
            R.color.materialPink
    };

    private static final int[] drawables = {
            R.drawable.leer,
            R.drawable.x,
            R.drawable.o,
            R.drawable.raute,
            R.drawable.dreieck,
            R.drawable.hexagon,
            R.drawable.stern,
            R.drawable.herz,
            R.drawable.blitz
    };

    Spielfeld spielfeld;
    Spieler[] spieler;

    SpielListener listener;

    private Paint paint;

    private Bitmap bitmapBackground;
    private Bitmap bitmapFields;

    private final Bitmap[] icons;
    private final ColorFilter[] filters;

    private boolean isInitialized;

    int fieldSize;
    private int stroke;

    public SpielfeldView(Context context) {
        super(context);
        isInitialized = false;
        icons = new Bitmap[drawables.length];
        filters = new ColorFilter[drawables.length];
    }

    public SpielfeldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isInitialized = false;
        icons = new Bitmap[drawables.length];
        filters = new ColorFilter[drawables.length];
    }

    public SpielfeldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isInitialized = false;
        icons = new Bitmap[drawables.length];
        filters = new ColorFilter[drawables.length];
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

        int size = getMeasuredWidth();
        setMeasuredDimension(size, size);
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

    /**
     * Sollte in der Sub-Klasse überschrieben werden, falls weitere Initialisierungsschritte notwendig sind
     * Wird nur einmal aufgerufen.
     */
    @CallSuper
    void initialize() {
        stroke = 3;
        fieldSize = getWidth() / spielfeld.getFieldCount();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        for (int i = 0; i < drawables.length; i++) {
            icons[i] = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                            getResources(),
                            drawables[i]
                    ),
                    fieldSize,
                    fieldSize,
                    false
            );
            filters[i] = new PorterDuffColorFilter(
                    ContextCompat.getColor(
                            getContext(),
                            colors[i]
                    ),
                    PorterDuff.Mode.SRC_ATOP
            );
        }

        drawBackground();
        drawFields();

        isInitialized = true;
    }

    /**
     * Wird aufgerufen, um das Spielfeld auf ein neues Spiel vorzubreiten.
     */
    public abstract void reset();

    /**
     * Zeichnet das Raster in eine Bitmap.
     */
    private void drawBackground() {
        Paint paint = new Paint();
        paint.setStrokeWidth(stroke);
        paint.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        paint.setStyle(Paint.Style.STROKE);

        bitmapBackground = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                Bitmap.Config.ARGB_4444
        );
        Canvas canvas = new Canvas(bitmapBackground);

        for (int i = 0; i <= spielfeld.getFieldCount(); i++) {
            canvas.drawLine(fieldSize * i, 0, fieldSize * i, fieldSize * spielfeld.getFieldCount(), paint);
            canvas.drawLine(0, fieldSize * i, fieldSize * spielfeld.getFieldCount(), fieldSize * i, paint);
        }
    }

    /**
     * Zeichnet die Symbole der Spieler in eine transparente Bitmap, die über den Hintergrund gelegt wird.
     */
    private void drawFields() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        bitmapFields = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                Bitmap.Config.ARGB_4444
        );
        Canvas canvas = new Canvas(bitmapFields);

        for (int x = 0; x < spielfeld.getFieldCount(); x++) {
            for (int y = 0; y < spielfeld.getFieldCount(); y++) {
                int value = spielfeld.getValue(x, y);
                paint.setColorFilter(filters[value]);
                canvas.drawBitmap(icons[value], fieldSize * x + stroke, fieldSize * y + stroke, paint);
            }
        }
    }

    public void setSpielfeld(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
    }

    public void setSpieler(Spieler[] spieler) {
        this.spieler = spieler;
    }

    public void setListener(SpielListener listener) {
        this.listener = listener;
    }
}