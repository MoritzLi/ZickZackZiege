package com.example.user.zzzmitview.network;

import android.os.AsyncTask;

public class ClientTask extends AsyncTask<String, Void, AndroidGameClient> {
    @Override
    protected AndroidGameClient doInBackground(String... params) {
        return new AndroidGameClient(params[0], params[1]);
    }
}