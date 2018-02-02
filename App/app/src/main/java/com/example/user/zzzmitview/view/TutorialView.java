package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.user.zzzmitview.utility.ArtificialIntelligence;
import com.example.user.zzzmitview.utility.Schwierigkeit;

/**
 * Created by User on 01.02.2018.
 */

public class TutorialView extends SpielfeldView {

    private Schwierigkeit schwierigkeit;

    private ArtificialIntelligence ki;

    public TutorialView(Context context) {
        super(context);
    }

    public TutorialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TutorialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                spielfeld.nextRound();

                ki.spielzug();
                activity.refreshPunkte(0, 1);
            }
        } else {
            spielfeld.clear();
            activity.refreshPunkte(0, 1);
            if (schwierigkeit == Schwierigkeit.SCHWIERIG) {
                ki.spielzug();
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    void initialize() {
        schwierigkeit = Schwierigkeit.EINFACH;

        spieler[0].setName("Computer");

        ki = new ArtificialIntelligence(spielfeld, schwierigkeit);

        super.initialize();
    }
}
