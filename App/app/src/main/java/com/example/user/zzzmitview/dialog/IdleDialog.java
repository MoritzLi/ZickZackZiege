package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;

import com.example.user.zzzmitview.R;

public class IdleDialog extends AppCompatDialog {
    public IdleDialog(Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_idle);
    }
}
