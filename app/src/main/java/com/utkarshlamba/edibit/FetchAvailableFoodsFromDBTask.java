package com.utkarshlamba.edibit;

import android.app.*;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by utk on 16-03-12.
 */
public class FetchAvailableFoodsFromDBTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        try{
            String link = new String("http://charliezhang.xyz/foodapp/getAllData.php");

            URL url = new URL(link);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            StringBuilder stringBuilder = new StringBuilder();
            String inputString="";
            while ((inputString = reader.readLine())!=null){
                stringBuilder.append(inputString);
            }

            com.utkarshlamba.edibit.Application.foodItemsList.clear();

            JSONObject json = new JSONObject(stringBuilder.toString());
            JSONArray foodItems = json.getJSONArray("foodItems");

            for (int i = 0; i<foodItems.length(); i++){
                JSONObject obj = foodItems.getJSONObject(i);

                String foodName = obj.getString("name");
                String userName = obj.getString("userName");
                String description = obj.getString("description");
                String location = obj.getString("location");
                String contactInfo = obj.getString("contactInfo");
                String price = String.valueOf(obj.getDouble("price"));
                String timeCooked = obj.getString("timeCooked");
                String imagePath = obj.getString("imagePath");

                JSONArray list= obj.getJSONArray("tags");
                String tags="";
                for (int m=0; m<list.length();m++){
                    tags+=list.get(m)+", ";
                }

                com.utkarshlamba.edibit.Application.foodItemsList.add(
                        new FoodItem(foodName, userName, description, location, contactInfo, price,
                                timeCooked, imagePath, tags)
                );

            }

        } catch (Exception e){
            Log.e("FetchDataFromDBTask", "exception");
        }
        return null;
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //FAQFragment.adapter.notifyDataSetChanged();
        Log.e("FetchDataFromDBTask", "datasetnotified");
    }
}
