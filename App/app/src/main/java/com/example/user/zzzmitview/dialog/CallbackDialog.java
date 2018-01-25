package com.example.user.zzzmitview.dialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.example.user.zzzmitview.utility.CallbackListener;

public abstract class CallbackDialog extends AppCompatDialog {
    private final CallbackListener listener;
    private       boolean          success;
    private       Object[]         result;

    CallbackDialog(Context context, CallbackListener listener) {
        super(context);

        this.listener = listener;
        setResult(false);
    }

    void setResult(boolean success, Object... result) {
        this.success = success;
        this.result = result;
    }

    CallbackListener getListener() {
        return listener;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        listener.notify(getClass(), success, result);
    }
}