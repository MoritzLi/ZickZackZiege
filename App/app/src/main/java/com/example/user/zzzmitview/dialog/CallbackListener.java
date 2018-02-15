package com.example.user.zzzmitview.dialog;

public interface CallbackListener {
    void notify(Class source, boolean success, Object... results);
}