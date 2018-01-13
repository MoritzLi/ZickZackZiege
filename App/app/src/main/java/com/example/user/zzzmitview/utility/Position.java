package com.example.user.zzzmitview.utility;

public class Position {
    private final int x;
    private final int y;
    private       int xFrei;
    private       int yFrei;

    public Position(int pX, int pY, int pXf, int pYf) {
        x = pX;
        y = pY;
        xFrei = pXf;
        yFrei = pYf;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getXFrei() {
        return xFrei;
    }

    public int getYFrei() {
        return yFrei;
    }
}

