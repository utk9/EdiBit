package com.utkarshlamba.edibit;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by utk on 16-03-12.
 */
public class AvailableFoodsFragment extends Fragment {


    public static ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_available_foods_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.available_foods_listView);

        ArrayList list = new ArrayList<String>();
        for (int i = 0; i<Application.foodItemsList.size(); i++){
            list.add(Application.foodItemsList.get(i).getFoodName());
            Log.e("dfd", Application.foodItemsList.get(i).getFoodName());
        }
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), Application.foodItemsList, list);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                FragmentManager fm = getActivity().getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,
                        new FoodDetailsFragment(i)).commit();
            }
        });

        new FetchAvailableFoodsFromDBTask(adapter).execute();

    }


}
