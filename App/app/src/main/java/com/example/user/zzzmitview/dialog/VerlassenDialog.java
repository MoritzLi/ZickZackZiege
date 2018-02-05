package com.example.user.zzzmitview.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.CallbackListener;

public class VerlassenDialog extends CallbackDialog {
    public VerlassenDialog(Activity context, CallbackListener listener) {
        super(context, listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_verlassen);

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(false);
                dismiss();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(true);
                dismiss();
            }
        });
    }
}
