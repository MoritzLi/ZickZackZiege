package com.example.user.zzzmitview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class ChangeNameDialog extends AppCompatDialog {
    private final MainActivity activity;

    ChangeNameDialog(MainActivity context) {
        super(context);
        activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_name);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(preferences.getString("nickname", ""));

        findViewById(R.id.speichern).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    preferences
                            .edit()
                            .putString("nickname", text)
                            .apply();
                    dismiss();
                } else {
                    activity.toast("Bitte gib einen Namen ein.");
                }
            }
        });

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        editText.requestFocus();
    }
}