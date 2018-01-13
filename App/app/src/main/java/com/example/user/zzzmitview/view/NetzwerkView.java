package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.user.zzzmitview.network.ClientListener;
import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;
import com.example.user.zzzmitview.network.ServerListener;
import com.example.user.zzzmitview.utility.Spieler;

public class NetzwerkView extends SpielfeldView {
    private boolean go;
    private int     myID;

    private GameServer server;
    private GameClient client;

    public NetzwerkView(Context context) {
        super(context);
    }

    public NetzwerkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NetzwerkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (go) {
            int x = (int) (event.getX() / fieldWidth);
            if (x >= spielfeld.getFieldCount())
                x = spielfeld.getFieldCount() - 1;

            int y = (int) (event.getY() / fieldWidth);
            if (y >= spielfeld.getFieldCount())
                y = spielfeld.getFieldCount() - 1;

            if (spielfeld.isEmpty(x, y)) {
                spielfeld.setValue(myID, x, y);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    void initialize() {
        go = server == null;

        super.initialize();
    }

    public void setClient(GameClient client) {
        this.client = client;
    }

    public void setServer(GameServer server) {
        this.server = server;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }

    public void setGo(boolean go) {
        this.go = go;
    }
}
