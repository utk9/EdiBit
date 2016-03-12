package com.utkarshlamba.edibit;

import android.os.AsyncTask;

import java.util.ArrayList;

import io.triangle.Session;

/**
 * Created by Jeffrey on 2016-03-12.
 */
public class Application extends android.app.Application {


    private final String APPLICATION_ID = "LrEE9TPXH6rKcXL";
    private final String ACCESS_KEY = "SBG1XkubQT";
    private final String SECRET_KEY = "o3f538fEYQyNpdE0iAILJsc3fBRr03rnFaA2nV70L8ZOl5ctmW2Us4aVRcQrhNEU";

    public static ArrayList<FoodItem> foodItemsList;

    public Application(){

        super();
        foodItemsList = new ArrayList<>();
    }

    @Override
    public void onCreate(){
        super.onCreate();

        // Initialize the Triangle API if it has not been initialized yet
        final Session triangleSession = Session.getInstance();

        if (!triangleSession.isInitialized())
        {
            // Since the initialization performs network IO, we should execute it in a background thread
            AsyncTask<Void, Void, Void> triangleInitializationTask = new AsyncTask<Void, Void, Void>()
            {
                Exception exception;

                @Override
                protected Void doInBackground(Void... voids)
                {
                    // Please use the keys you obtain from the keys page below
                    try
                    {
                        triangleSession.initialize(
                                APPLICATION_ID, // Application ID
                                ACCESS_KEY,      // Access Key
                                SECRET_KEY, // Secret Key
                                Application.this);
                        System.out.println("initialized");
                    }
                    catch (Exception exception)
                    {
                        this.exception = exception;
                    }

                    return null;
                }
            };

            // Finally execute the task
            triangleInitializationTask.execute();
        }

    }
}
