package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.user.zzzmitview.utility.ArtificialIntelligence;
import com.example.user.zzzmitview.utility.Schwierigkeit;

public class SingleplayerView extends SpielfeldView {
    private Schwierigkeit schwierigkeit;

    private ArtificialIntelligence ki;

    public SingleplayerView(Context context) {
        super(context);
    }

    public SingleplayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleplayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (spielfeld.isPlaying()) {
            int x = (int) (event.getX() / fieldSize);
            if (x >= spielfeld.getFieldCount())
                x = spielfeld.getFieldCount() - 1;

            int y = (int) (event.getY() / fieldSize);
            if (y >= spielfeld.getFieldCount())
                y = spielfeld.getFieldCount() - 1;

            if (spielfeld.isEmpty(x, y)) {
                spielfeld.setValue(ArtificialIntelligence.playerID, x, y);
                listener.round();

                if (spielfeld.isPlaying()) {
                    ki.spielzug();
                    listener.round();
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    void initialize() {
        spieler[0].setName("Computer");

        ki = new ArtificialIntelligence(spielfeld, schwierigkeit);
        if (schwierigkeit == Schwierigkeit.SCHWIERIG) {
            ki.spielzug();
            listener.round();
        }

        super.initialize();
    }

    public void setSchwierigkeit(Schwierigkeit schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }

    @Override
    public void reset() {
        if (schwierigkeit == Schwierigkeit.SCHWIERIG) {
            ki.spielzug();
        }
    }
}