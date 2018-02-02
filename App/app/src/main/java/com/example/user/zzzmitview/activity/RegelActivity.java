package com.example.user.zzzmitview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.user.zzzmitview.R;

public class RegelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeln);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                int width;
                while ((width = findViewById(R.id.titel1).getWidth()) == 0)
                    ;

                findViewById(R.id.titel2).setMinimumWidth(width);
                findViewById(R.id.titel3).setMinimumWidth(width);
                findViewById(R.id.titel4).setMinimumWidth(width);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}