package com.utkarshlamba.edibit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.StringTokenizer;
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
        TextView priceTextView = (TextView) v.findViewById(R.id.price_textView);
        TextView timeTextView = (TextView) v.findViewById(R.id.time_remaining_textView);
        TextView tagsTextView = (TextView) v.findViewById(R.id.tags_textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.food_ImageView);

        titleTextView.setText(items.get(position).getFoodName());
        priceTextView.setText("$" + items.get(position).getPrice());
        timeTextView.setText("Time created: " + items.get(position).getTimeCooked());
        Uri myUri = Uri.parse("www.edibit.org/userImg/" + items.get(position).getImagePath());
        imageView.setImageURI(myUri);

        Log.e("Image path: ", items.get(1).getImagePath());


        // Display only the first few tags
        StringTokenizer allTags = new StringTokenizer(items.get(position).getTags());
        int counter = 0;
        String tags = "";

        while (allTags.hasMoreTokens() && counter < 4){
            tags += allTags.nextToken() + " ";
            counter++;
        }
        tags = tags.substring(0, tags.length()-2);

        tagsTextView.setText(tags);

        return v;
    }
}
