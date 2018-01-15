package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.user.zzzmitview.network.GameClient;
import com.example.user.zzzmitview.network.GameServer;

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
            int x = (int) (event.getX() / fieldSize);
            if (x >= spielfeld.getFieldCount())
                x = spielfeld.getFieldCount() - 1;

            int y = (int) (event.getY() / fieldSize);
            if (y >= spielfeld.getFieldCount())
                y = spielfeld.getFieldCount() - 1;

            if (spielfeld.isEmpty(x, y)) {
                setze(x, y);
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    void initialize() {
        go = false;

        super.initialize();
    }

    private void setze(int x, int y) {
        spielfeld.setValue(myID, x, y);
        if (server == null) {
            client.send("SET " + x + ',' + y);
        } else {
            server.processMessage("localhost", GameServer.port, "SET " + x + ',' + y);
        }

        go = false;
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
