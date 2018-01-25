package com.example.user.zzzmitview.utility;

public interface CallbackListener {
    void notify(Class source, boolean success, Object... results);
}