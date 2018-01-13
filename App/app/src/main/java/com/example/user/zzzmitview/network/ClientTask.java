package com.example.user.zzzmitview.network;

import android.os.AsyncTask;

public class ClientTask extends AsyncTask<String, Void, GameClient> {
    @Override
    protected GameClient doInBackground(String... params) {
        return new GameClient(params[0], params[1]);
    }
}