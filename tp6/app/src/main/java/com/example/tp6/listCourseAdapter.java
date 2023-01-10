package com.example.tp6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class listCourseAdapter extends BaseAdapter {

    private Context context;
    private List<listeCourse> listes;

    public listCourseAdapter(Context context, List<listeCourse> listes) {
        this.context = context;
        this.listes = listes;
    }

    @Override
    public int getCount() {
        return this.listes.size();
    }

    @Override
    public Object getItem(int i) {
        return this.listes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item, viewGroup, false);
        TextView textView = (TextView) itemView.findViewById(R.id.nom);
        textView.setText(this.listes.get(i).getNom());
        return itemView;
    }
}
