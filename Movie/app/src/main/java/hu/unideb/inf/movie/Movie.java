package hu.unideb.inf.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Movie extends AppCompatActivity {

    public TextView titletv;
    public TextView releasedDateTextView;
    public TextView runTimeTextView;
    public ImageView posterImage;
    public TextView genreTextView;
    public TextView directorTextView;
    public TextView writerTextView;
    public TextView actorsTextView;
    public TextView plotTextView;
    public TextView metascoreTextView;
    public TextView imdbRatinTextView;
    public TextView imdbVotesTextView;
    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        titletv = findViewById(R.id.titleTextView);
        releasedDateTextView = findViewById(R.id.releasedDateTextView);
        runTimeTextView = findViewById(R.id.runTimeTextView);
        posterImage = findViewById(R.id.posterImage);
        genreTextView = findViewById(R.id.genreTextView);
        directorTextView = findViewById(R.id.directorTextView);
        writerTextView = findViewById(R.id.writerTextView);
        actorsTextView = findViewById(R.id.actorsTextView);
        plotTextView = findViewById(R.id.plotTextView);
        metascoreTextView = findViewById(R.id.metascoreTextView);
        imdbRatinTextView = findViewById(R.id.imdbRatingTextView);
        imdbVotesTextView = findViewById(R.id.imdbVotesTextView);

        id = getIntent().getStringExtra("Movie ID");
        new MovieAsync().execute();

    }

    public void createImageVIew(String url){
        Picasso.get().load(url).into(posterImage);
    }

    public class MovieAsync extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://movie-database-alternative.p.rapidapi.com/?r=json&i=" + id)
                    .get()
                    .addHeader("X-RapidAPI-Key", "2d710340ebmsh239cd13c6a2ddb1p103a1djsn3de419bde264")
                    .addHeader("X-RapidAPI-Host", "movie-database-alternative.p.rapidapi.com")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());

                return object;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject object) {
            super.onPostExecute(object);
            if (object.length() == 0)
            {
                Toast.makeText(getApplicationContext() , "Hiba történt", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    System.out.println(object);
                    String title = object.getString("Title");
                    String year = object.getString("Year");
                    String rated = object.getString("Rated");
                    String released = object.getString("Released");
                    String runtime = object.getString("Runtime");
                    String genre = object.getString("Genre");
                    String director = object.getString("Director");
                    String writer = object.getString("Writer");
                    String actors = object.getString("Actors");
                    String plot = object.getString("Plot");
                    String language = object.getString("Language");
                    String country = object.getString("Country");
                    String awards = object.getString("Awards");
                    String poster = object.getString("Poster");
                    String metascore = object.getString("Metascore");
                    String imdbrating = object.getString("imdbRating");
                    String imdbvotes = object.getString("imdbVotes");
                    String imdbid = object.getString("imdbID");
                    String type = object.getString("Type");
                    //String dvd = object.getString("DVD");
                    String boxoffice = object.getString("BoxOffice");
                    //JSONArray ratingsArray = new JSONArray(object.getString("Ratings"));
                    /*for (int i = 0; i < ratingsArray.length(); i++) {
                        JSONObject rating = ratingsArray.getJSONObject(i);

                    }*/
                    titletv.setText(title);
                    String rd = "Released Date: " + released;
                    String rt = "Runtime: " + runtime;
                    releasedDateTextView.setText(rd);
                    runTimeTextView.setText(rt);
                    createImageVIew(poster);
                    genreTextView.setText(genre);
                    directorTextView.setText(director);
                    writerTextView.setText(writer);
                    actorsTextView.setText(actors);
                    plotTextView.setText(plot);
                    String ms = "Metascore: " + metascore;
                    metascoreTextView.setText(ms);
                    String ir = "imdbRating: " + imdbrating;
                    imdbRatinTextView.setText(ir);
                    String iv  = "imdbVotes: " + imdbvotes;
                    imdbVotesTextView.setText(iv);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}