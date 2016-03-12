package com.utkarshlamba.edibit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by utk on 16-03-12.
 */
public class AvailableFoodsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_available_foods_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) getActivity().findViewById(R.id.available_foods_listView);
        //CustomListAdapter adapter = new CustomListAdapter();
        //listView.setAdapter(adapter);

        new FetchAvailableFoodsFromDBTask().execute();

//        ListView listView = (ListView) getActivity().findViewById(R.id.faq_listView);
//        adapter = new QuestionListAdapter(getActivity(),
//                MainActivity.questionsList, MainActivity.answersList);
//        listView.setAdapter(adapter);

        //new FetchDataFromDBTask().execute();



    }


}
