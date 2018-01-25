package com.example.user.zzzmitview.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.activity.MainActivity;

public class SpielerzahlDialog extends AppCompatDialog {
    private final MainActivity activity;
    private final Intent       intent;

    public SpielerzahlDialog(MainActivity context, Intent intent) {
        super(context);
        activity = context;
        this.intent = intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_spieleranzahl);

        final EditText editText = findViewById(R.id.editText);

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    int n = Integer.parseInt(
                            text
                    );
                    if (n >= 2 && n <= 8) {
                        intent.putExtra(
                                MainActivity.INTENT_EXTRA_SPIELERZAHL,
                                n
                        );
                        dismiss();
                        activity.startActivity(intent);
                    } else {
                        activity.toast("Bitte gib Spielerzahl zwischen 2 und 8 ein.");
                    }
                } else {
                    activity.toast("Bitte gib Spielerzahl zwischen 2 und 8 ein.");
                }
            }
        });

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        editText.requestFocus();
    }
}