package com.utkarshlamba.edibit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by melissali on 16-03-12.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private static Context context;
    public CustomListAdapter(Activity context) {
        super(context, R.layout.custom_rowitem_layout);
        this.context = context;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.custom_rowitem_layout, null, true);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        TextView tags = (TextView) rowView.findViewById(R.id.tags);
        TextView time = (TextView) rowView.findViewById(R.id.time_remaining);
        TextView distance = (TextView) rowView.findViewById(R.id.distance);

        title.setText(Application.foodItemsList.get(position).getFoodName());
        //imageView.setImageResource(Application.imgid[position]); -- SET IMAGE LATER
        time.setText(Application.foodItemsList.get(position).getTimeCooked());
        tags.setText(Application.foodItemsList.get(position).getTags());
        distance.setText(Application.foodItemsList.get(position).getDistance());
        return rowView;

    };
}
