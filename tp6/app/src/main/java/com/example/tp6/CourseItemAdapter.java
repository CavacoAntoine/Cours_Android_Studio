package com.example.tp6;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseItemAdapter extends BaseAdapter {

    private Context context;
    private List<CourseItem> items;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item,viewGroup , false);
        TextView nom = (TextView) itemView.findViewById(R.id.nom);
        TextView quantite = (TextView) itemView.findViewById(R.id.nombre);
        nom.setText(this.items.get(i).getNom());
        quantite.setText(""+this.items.get(i).getQuantité());
        return itemView;
    }
}
