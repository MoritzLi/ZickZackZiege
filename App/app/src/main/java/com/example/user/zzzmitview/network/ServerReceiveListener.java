package com.example.user.zzzmitview.network;

interface ServerReceiveListener {
    void received(String message, String ip, int port);

    void closed(String ip, int port);
}
