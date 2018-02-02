package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.os.Bundle;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.CallbackListener;

public class IdleDialog extends CallbackDialog {
    public IdleDialog(Activity context, CallbackListener listener) {
        super(context, listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_idle);
        setResult(true);
    }

    @Override
    public void cancel() {
        setResult(false);
        super.cancel();
    }
}