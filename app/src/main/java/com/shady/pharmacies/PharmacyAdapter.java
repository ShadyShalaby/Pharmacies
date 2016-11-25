package com.shady.pharmacies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shady.pharmacies.model.Place;

import java.util.ArrayList;

/**
 * Created by SPC I on 11/25/2016.
 */

public class PharmacyAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Place> places;
    private LayoutInflater inflater;

    public PharmacyAdapter(Context context, ArrayList<Place> places) {
        this.context = context;
        this.places = places;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int i) {
        return places.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = inflater.inflate(R.layout.listitem_pharmacies, viewGroup, false);

        TextView name = (TextView) view.findViewById(R.id.listItem_pharmacies_textView);
        name.setText(places.get(i).getName());

        return view;
    }
}
