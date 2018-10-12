package com.example.wesh9.hw3;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

/**
 * Created by wesh9 on 10/17/2017.
 */

public class DownloadJSON extends IntentService {

    public static final String ACTION_DOWNLOAD = "com.example.wesh9.hw3.action.download";
    public static final String URL = "url";



    private DatabaseManager manager;

    public DownloadJSON() {
        super("DownloadJSON");
    }



    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("jsondata","onHandleIntent()");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {

                populateDB(
                        download(intent.getStringExtra(URL))

                );
            }
        }
    }


    private String download(String url){


        manager = new DatabaseManager(this.getApplicationContext());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);

        Response response = null;

        String jsonData = null;

        try {
            response = call.execute();

            if (response.isSuccessful()) {
                jsonData = response.body().string();

            } else {
                jsonData = null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData; //This is returned to onPostExecute()

    }


    private void populateDB(String jsonData) {

        Log.d("jsondata","can we see it:?"+jsonData);
        manager.open();
        JSONObject jsonResponse = null;
        try {

            jsonResponse = new JSONObject(jsonData);

            JSONArray movies = jsonResponse.getJSONArray("results");

            int movie_list_size = movies.length();



            for (int i = 0; i < movie_list_size; i++) {

                JSONObject jsonFilm = movies.getJSONObject(i);



                String dateString = jsonFilm.getString("popularity");
                String title = jsonFilm.getString("title");

                String rating = jsonFilm.getString("vote_average");

                String datee = jsonFilm.getString("release_date");
                String img = jsonFilm.getString("poster_path");
                String overview = jsonFilm.getString("overview");

                if (dateString != null && !dateString.equals("null")) {


                    manager.insertMovieInfo(title,dateString,rating, datee, img, overview);

                }
            }
            manager.close();

            //broadcasting that it worked

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcastIntent);
        }
        catch (JSONException e) {

            e.printStackTrace();
        }}


}
