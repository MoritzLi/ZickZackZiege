package com.example.user.zzzmitview.view;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
    private       int       current;

    public SpielerAdapter(@NonNull Context context, @NonNull Spieler[] objects) {
        super();
        this.context = context;
        this.spieler = objects;
        this.current = -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_spieler, parent, false);
        }

        Spieler spieler = getItem(position);

        final TextView name = convertView.findViewById(R.id.spieler);
        name.setText(spieler.getName());

        final TextView punkte = convertView.findViewById(R.id.punkte);
        punkte.setText(spieler.getPunkteString());

        if (current == position) {
            convertView.setBackgroundColor(
                    ContextCompat.getColor(
                            context,
                            SpielfeldView.colors[spieler.getId()]
                    )
            );
        } else {
            convertView.setBackgroundColor(
                    ContextCompat.getColor(
                            context,
                            SpielfeldView.colors[0]
                    )
            );
        }

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

    public void setCurrent(int current) {
        this.current = current;
    }
}
