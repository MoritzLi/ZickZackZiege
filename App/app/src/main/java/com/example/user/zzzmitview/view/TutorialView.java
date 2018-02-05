package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TutorialView extends SingleplayerView {
    private boolean started;

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
        return started && super.onTouchEvent(event);
    }

    @Override
    void initialize() {
        started = false;

        super.initialize();
    }

    public void start() {
        this.started = true;
    }
}