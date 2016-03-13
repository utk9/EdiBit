package com.utkarshlamba.edibit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by melissali on 16-03-12.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<FoodItem> items;


    public CustomListAdapter(Context context, ArrayList<FoodItem> foods, ArrayList<String> names) {
        super(context,R.layout.custom_rowitem_layout, names);
        this.context = context;
        this.items = foods;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_rowitem_layout, parent, false);
        }

        TextView titleTextView = (TextView) v.findViewById(R.id.title_textView);

        TextView tagsTextView = (TextView) v.findViewById(R.id.tags_textView);


        titleTextView.setText(com.utkarshlamba.edibit.Application.foodItemsList.get(position).getFoodName());
        tagsTextView.setText(com.utkarshlamba.edibit.Application.foodItemsList.get(position).getTags());

        return v;
    }
}
