package com.example.user.zzzmitview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.zzzmitview.R;
import com.example.user.zzzmitview.utility.Spieler;

public class SpielerAdapter extends BaseAdapter {
    private final Spieler[] spieler;
    private final Context   context;

    public SpielerAdapter(@NonNull Context context, @NonNull Spieler[] objects) {
        super();
        this.context = context;
        spieler = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_spieler, parent, false);
        }

        final TextView name = (TextView) convertView.findViewById(R.id.spieler);
        name.setText(getItem(position).getName());

        final TextView punkte = (TextView) convertView.findViewById(R.id.punkte);
        punkte.setText(getItem(position).getPunkteString());

        return convertView;
    }

    @Nullable
    @Override
    public Spieler getItem(int position) {
        return spieler[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return spieler.length;
    }

    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
