package com.utkarshlamba.edibit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by utk on 16-03-12.
 */
public class PostDetails extends Activity {

    String ba1;
    //public static final String URL = "http://www.edibit.org/php/addFood.php";

    public static final String URL = "http://www.charliezhang.xyz/foodapp/php/addFood.php";
    String mCurrentPhotoPath;

    File imageFile;


    String foodName;
    String userName;
    String description;
    String location;
    String contactInfo;
    String price;
    String timeCooked;
    String imagePath;
    String tags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_post_details);

        mCurrentPhotoPath = getIntent().getStringExtra("path");

        final EditText titleEditText = (EditText) findViewById(R.id.description_title);

        titleEditText.setHint("title");

        final TextView userTextView = (TextView) findViewById(R.id.description_user);

        final EditText descriptionEditText = (EditText) findViewById(R.id.description);
        descriptionEditText.setHint("Description");

        final EditText timeEditText = (EditText) findViewById(R.id.description_time);
        timeEditText.setHint("hh:mm AM/PM");

        final EditText priceEditText = (EditText) findViewById(R.id.description_price);
        priceEditText.setHint("0.00");

        final EditText contactEditText = (EditText) findViewById(R.id.description_contact);
        contactEditText.setHint("Phone or email address");

        final EditText tagsEditText = (EditText) findViewById(R.id.description_tags);

        ImageView imgView = (ImageView) findViewById(R.id.image_view);

        imageFile = new  File(mCurrentPhotoPath);
        if(imageFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

            imgView.setImageBitmap(myBitmap);

        }





        Button postButton = (Button) findViewById(R.id.post_button);
        postButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                foodName = titleEditText.getText().toString();
                userName = userTextView.getText().toString();
                description = descriptionEditText.getText().toString();
                contactInfo = contactEditText.getText().toString();
                price  = priceEditText.getText().toString();
                timeCooked = timeEditText.getText().toString();
                tags = tagsEditText.getText().toString();

                if (!foodName.equals("") && !userName.equals("") && !description.equals("") && !contactInfo.equals("")
                        && !price.equals("") && !timeCooked.equals("") && !tags.equals("")){

                    SingleShotLocationProvider.requestSingleUpdate(getApplicationContext(),
                            new SingleShotLocationProvider.LocationCallback() {
                                @Override
                                public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates locationL) {
                                    //Log.d("Location", "my location is " + location.longitude + " " + location.latitude);

                                    String locationCoord = String.valueOf(locationL.latitude) + " " + String.valueOf(locationL.longitude);
                                    Log.e("dfddfdfd", locationCoord);
                                    location = locationCoord;

                                    //FoodItem foodItem = new FoodItem(foodName, userName, description, locationCoord, contactInfo, price, timeCooked, mCurrentPhotoPath, tags);

                                    //new SendPostDetailsToDBTask(foodItem).execute();

                                    upload();
                                }
                            });
                } else{
                    Toast.makeText(getApplicationContext(), "Please fill out the required fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void upload() {
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        //ba1 = Base64.encodeBytes(ba);
        ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        // Upload image to server
        new uploadToServer().execute();

    }

    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(PostDetails.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Uploading");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

//            String foodName = obj.getString("name");
//            String userName = obj.getString("username");
//            String description = obj.getString("description");
//            String location = obj.getString("location");
//            String contactInfo = obj.getString("contactInfo");
//
//            String price = String.valueOf(obj.getDouble("price"));
//            String timeCooked = obj.getString("time");
//            String imagePath = obj.getString("image");
//
//            JSONArray list= obj.getJSONArray("tags");

            try {
                HttpClient httpclient = new DefaultHttpClient();

                HttpPost httppost = new HttpPost(URL);


                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                builder.addTextBody("name", foodName);
                builder.addTextBody("description", description);
                builder.addTextBody("location", location);
                builder.addTextBody("price", price);
                builder.addTextBody("contact", contactInfo);
                builder.addTextBody("timeCooked", timeCooked);
                builder.addTextBody("tags", tags);
                if(imageFile != null)
                {
                    builder.addBinaryBody("file", imageFile);
                }

                //builder.addPart("name", new StringBody(foodName));

                HttpEntity entity = builder.build();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                Log.e("dfdfd", EntityUtils.toString(response.getEntity()));


            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }

    }

    private class PhotoUploadResponseHandler implements ResponseHandler<Object> {

        @Override
        public Object handleResponse(HttpResponse response)
                throws ClientProtocolException, IOException {

            HttpEntity r_entity = response.getEntity();
            String responseString = EntityUtils.toString(r_entity);
            Log.d("UPLOAD", responseString);

            return null;
        }

    }
}



