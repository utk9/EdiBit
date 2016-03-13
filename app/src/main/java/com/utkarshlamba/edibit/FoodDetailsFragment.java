package com.utkarshlamba.edibit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by utk on 16-03-12.
 */
public class FoodDetailsFragment extends Fragment {
    int pos;

    public FoodDetailsFragment(int pos) {
        this.pos = pos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_description, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView descriptionTitle = (TextView) getActivity().findViewById(R.id.description_title);
        TextView descriptionDetails = (TextView) getActivity().findViewById(R.id.description);
        TextView contactTextView = (TextView) getActivity().findViewById(R.id.description_contact);
        TextView timeTextView = (TextView) getActivity().findViewById(R.id.description_time);
        TextView priceTextView = (TextView) getActivity().findViewById(R.id.description_price);
        TextView tagsTextView = (TextView) getActivity().findViewById(R.id.description_tags);

        descriptionTitle.setText(Application.foodItemsList.get(pos).getFoodName());
        descriptionDetails.setText(Application.foodItemsList.get(pos).getDescription());
        contactTextView.setText("Contact: " + Application.foodItemsList.get(pos).getContactInfo());
        timeTextView.setText("Time created: " + Application.foodItemsList.get(pos).getTimeCooked());
        priceTextView.setText("Price: $" + Application.foodItemsList.get(pos).getPrice());
        tagsTextView.setText("Tags: " + Application.foodItemsList.get(pos).getTags());
    }
}
