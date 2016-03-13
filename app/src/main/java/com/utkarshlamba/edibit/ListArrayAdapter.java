package com.utkarshlamba.edibit;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Ryan on 2016-01-23.
 */
public class ListArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public ListArrayAdapter(Context context, String[] values) {
        super(context, R.layout.custom_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.list_item_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_logo);
        textView.setText(values[position]);


        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        if (s.equals("Available foods")) {
            imageView.setImageResource(R.drawable.available_icon);
        } else if (s.equals("Post Food")) {
            imageView.setImageResource(R.drawable.post_icon);
        } else if (s.equals("Payment Info")){
            imageView.setImageResource(R.drawable.card_icon);
        }


        return rowView;
    }
}