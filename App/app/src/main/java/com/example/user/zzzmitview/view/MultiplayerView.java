package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MultiplayerView extends SpielfeldView {
    private int current;

    public MultiplayerView(Context context) {
        super(context);
    }

    public MultiplayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiplayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) (event.getX() / fieldWidth);
        if (x >= spielfeld.getFieldCount())
            x = spielfeld.getFieldCount() - 1;

        int y = (int) (event.getY() / fieldWidth);
        if (y >= spielfeld.getFieldCount())
            y = spielfeld.getFieldCount() - 1;

        if (spielfeld.isEmpty(x, y)) {
            spielfeld.setValue(spieler[current].getId(), x, y);

            activity.refreshPunkte(current);

            current++;
            if (current == spieler.length) {
                current = 0;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    void initialize() {
        current = 0;

        super.initialize();
    }
}