package com.example.wesh9.hw3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Screen2 extends AppCompatActivity {

    TextView screenTitle;
    TextView date;
    TextView overview;
    ImageView image1;
    CheckBox check;
    int pos;

    String dateString;
    String overviewString;
    String checkString = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        screenTitle = (TextView)findViewById(R.id.screenTitle);
        date = (TextView)findViewById(R.id.screenDate);
        overview = (TextView)findViewById(R.id.overview);
        image1 = (ImageView)findViewById(R.id.image1);
        check = (CheckBox)findViewById(R.id.favScreem2);

        Intent intent = getIntent();
        screenTitle.setText(intent.getExtras().getString("title"));
        date.setText(intent.getExtras().getString("date"));
        overview.setText(intent.getExtras().getString("overview"));
        pos = intent.getExtras().getInt("position");
        String img = intent.getExtras().getString("img");
        String fav = intent.getExtras().getString("fav");
        if(fav == "1"){
            check.toggle();
        }
        String url = "http://image.tmdb.org/t/p/w185" + img;

        loadImageFromUrl(url);

    }

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url).error(R.mipmap.ic_launcher)
                .into(image1, new com.squareup.picasso.Callback(){


                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("fav", checkString);
        intent.putExtra("position", pos);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onScreenBox(View view) {
        if(view.getId() == check.getId() ){
            if(check.isChecked()){
                checkString = "1";
            }
            else{
                checkString = "0";
            }
        }
    }
}
