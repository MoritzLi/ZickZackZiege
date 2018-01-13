package com.example.user.zzzmitview.utility;

public class Position {
    private final int x;
    private final int y;
    private       int xFrei;
    private       int yFrei;

    Position(int pX, int pY, int pXf, int pYf) {
        x = pX;
        y = pY;
        xFrei = pXf;
        yFrei = pYf;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getXFrei() {
        return xFrei;
    }

    int getYFrei() {
        return yFrei;
    }
}

