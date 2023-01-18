package com.example.tp6;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class CourseItemAdapter extends BaseAdapter {

    private final Context context;
    private final List<CourseItem> items;

    public CourseItemAdapter(Context context, List<CourseItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item, viewGroup, false);
        TextView nom = (TextView) itemView.findViewById(R.id.nom);
        TextView quantite = (TextView) itemView.findViewById(R.id.nombre);
        ImageView icon = itemView.findViewById(R.id.icon);
        nom.setText(this.items.get(i).getNom());
        quantite.setText("" + this.items.get(i).getQuantité());
        Resources res = context.getResources();
        String packageName = context.getPackageName();
        String s;
        if (this.items.get(i).getQuantité() > 10) {
            s = "deg" + 10;
        } else {
            s = "deg" + this.items.get(i).getQuantité();
        }
        int colorId = res.getIdentifier(s, "color", packageName);
        int desiredColor = ContextCompat.getColor(context, colorId);
        quantite.setBackgroundTintList(ColorStateList.valueOf(desiredColor));

        if(nom.getText().toString().trim().equals("tomate"))
            icon.setImageResource(R.drawable.tomato);
        if(nom.getText().toString().trim().equals("salade"))
            icon.setImageResource(R.drawable.lettuce);
        if(nom.getText().toString().trim().equals("carotte"))
            icon.setImageResource(R.drawable.carrot);
        if(nom.getText().toString().trim().equals("steak"))
            icon.setImageResource(R.drawable.meat);

        return itemView;
    }


}
