package com.example.wesh9.hw3;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.BroadcastReceiver;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String url = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "e1357e27550f3f477a793f4b6e0fa7f9";

    private ResponseReceiver receiver;

    Button refresh;
    Spinner sortBy;
    CheckBox favBox;
    ListView movieList;
    Button info;

    List<Movie> movs;
    List<Movie> favMoves;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (Button)findViewById(R.id.refresh);
        sortBy = (Spinner)findViewById(R.id.sortBy);
        favBox = (CheckBox)findViewById(R.id.favBox);
        movieList = (ListView)findViewById(R.id.moviesList);
        favMoves = new ArrayList<Movie>();


        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(MainActivity.this, Screen2.class);
                String title = ((TextView) view.findViewById(R.id.title1)).getText().toString();
                Movie clickMov = movs.get(position);
                intent.putExtra("title", title);
                if(movs.get(position).getFav()){
                    intent.putExtra("fav", "1");
                }
                else{
                    intent.putExtra("fav", "0");
                }
                intent.putExtra("position", position);
                intent.putExtra("date", clickMov.getDatee());
                intent.putExtra("overview", clickMov.getOverview());
                intent.putExtra("img", clickMov.getImg());
                startActivityForResult(intent,1);
            }
        });

        //registering a local broadcast receiver that is activated when "movies_fetched"
        //action happens
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);


        // starting an intent service that will fetch the list of movies from the URL below
        String complete_url = url+ "movie/now_playing?api_key="+API_KEY+"&language=en-US&page=1";
        Intent msgIntent = new Intent(this, DownloadJSON.class);
        msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
        msgIntent.putExtra(DownloadJSON.URL, complete_url);
        startService(msgIntent);

        // after IntentService is done we will receive a broadcast telling us that it is time to fetch the list of movies from the db
    }


    public void displayMovies(){
        DatabaseManager manager = new DatabaseManager(this);

        manager.open();

        String sort = sortBy.getSelectedItem().toString();

        if (sort.equals("Title")) {
            sort = DBOpenHelper.COLUMN_NAME_TITLE;
        }
        if (sort.equals("Votes")) {
            sort = DBOpenHelper.COLUMN_NAME_VOTE;
        }
        if(sort.equals("Popularity")){
            sort = DBOpenHelper.COLUMN_NAME_POPULARITY;
        }

        List<Movie> arrayOfMovies = manager.getAllRecords(sort);
        movs = arrayOfMovies;


//        for(int i = 0; i < movs.size(); i++){
//            if(movs.get(i).getFav()){
//                Toast.makeText(this,"fav", Toast.LENGTH_LONG);
//            }
//        }



// Create the adapter to convert the array to views
        if(favBox.isChecked()){

            MovieAdapter adapter = new MovieAdapter(this, (ArrayList<Movie>)favMoves);
            ListView listView = (ListView) findViewById(R.id.moviesList);
            listView.setAdapter(adapter);
            manager.deleteAll();
        }
        else {
            MovieAdapter adapter = new MovieAdapter(this, (ArrayList<Movie>) arrayOfMovies);
            ListView listView = (ListView) findViewById(R.id.moviesList);
            listView.setAdapter(adapter);
            manager.deleteAll();

        }

// Attach the adapter to a ListView




    }



    private void loadImageFromUrl(String url, ImageView img) {
        Picasso.with(this).load(url).error(R.mipmap.ic_launcher)
                .into(img, new com.squareup.picasso.Callback(){


                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == refresh.getId()){
            unregisterReceiver(receiver);
            IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            receiver = new ResponseReceiver();
            registerReceiver(receiver, filter);


            // starting an intent service that will fetch the list of movies from the URL below
            String complete_url = url+ "movie/now_playing?api_key="+API_KEY+"&language=en-US&page=1";
            Intent msgIntent = new Intent(this, DownloadJSON.class);
            msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
            msgIntent.putExtra(DownloadJSON.URL, complete_url);
            startService(msgIntent);
        }
    }


    public class MovieAdapter extends ArrayAdapter<Movie> {

        public MovieAdapter(Context context, ArrayList<Movie> movies) {

            super(context, 0, movies);

        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            // Get the data item for this position

            Movie movie = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view

            if (convertView == null) {

                //the like button would need to be in single_movie.xml
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_movie, parent, false);


            }

            // Lookup view for data population
            info = (Button) convertView.findViewById(R.id.info);

            TextView title = (TextView) convertView.findViewById(R.id.title1);

            TextView date = (TextView) convertView.findViewById(R.id.date);

            TextView rating = (TextView) convertView.findViewById(R.id.rating);

            ImageView image2 = (ImageView) convertView.findViewById(R.id.lvImage);

            String imgageString = movie.getImg();
            String url = "http://image.tmdb.org/t/p/w185" + imgageString;
            loadImageFromUrl(url, image2);

            //CheckBox favorite = (CheckBox) convertView.findViewById(R.id.favorite);

            // Per each view we fetch info from the corresponding movie and set it

            title.setText(movie.getTitle());

            date.setText(movie.getDate());

            rating.setText(movie.getRating()+"");


            // Return the completed view to render on screen

            return convertView;

        }

    }



    public class ResponseReceiver extends BroadcastReceiver{
        public static final String ACTION_RESP =
                "movies_fetched";

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("demoapp","movies are fetched");

            displayMovies();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){

            if(resultCode == RESULT_OK){
                String check = data.getExtras().getString("fav");
                if(check.equals("1")){
                    Movie favorite = movs.get(data.getExtras().getInt("position"));
                    favMoves.add(favorite);
                }
                
                unregisterReceiver(receiver);
                IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
                filter.addCategory(Intent.CATEGORY_DEFAULT);
                receiver = new ResponseReceiver();
                registerReceiver(receiver, filter);


                // starting an intent service that will fetch the list of movies from the URL below
                String complete_url = url+ "movie/now_playing?api_key="+API_KEY+"&language=en-US&page=1";
                Intent msgIntent = new Intent(this, DownloadJSON.class);
                msgIntent.setAction(DownloadJSON.ACTION_DOWNLOAD);
                msgIntent.putExtra(DownloadJSON.URL, complete_url);
                startService(msgIntent);


            }
        }

    }
}


