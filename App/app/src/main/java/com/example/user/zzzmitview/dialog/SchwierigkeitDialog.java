package com.example.user.zzzmitview.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.activity.MainActivity;
import com.example.user.zzzmitview.utility.Schwierigkeit;

public class SchwierigkeitDialog extends AppCompatDialog {
    private final MainActivity activity;
    private final Intent       intent;

    public SchwierigkeitDialog(MainActivity context, Intent intent) {
        super(context);
        activity = context;
        this.intent = intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_schwierigkeit);

        findViewById(R.id.einfach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(MainActivity.INTENT_EXTRA_SCHWIERIGKEIT, Schwierigkeit.EINFACH.toString());
                dismiss();
                activity.startActivity(intent);
            }
        });

        findViewById(R.id.schwierig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("schwierigkeit", Schwierigkeit.SCHWIERIG.toString());
                dismiss();
                activity.startActivity(intent);
            }
        });
    }
}