package hu.unideb.inf.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovielistActivity extends AppCompatActivity {

    public LinearLayout ll;
    public ScrollView sv;
    public JSONArray movielist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movielist);
        sv = findViewById(R.id.scrollMovieList);
        ll = findViewById(R.id.linearL);
        String movies = getIntent().getStringExtra("Movie List");
        try {
            movielist = new JSONArray(movies);
            for (int i = 0; i < movielist.length(); i++) {
                JSONObject movie = movielist.getJSONObject(i);
                String title = movie.getString("Title");
                String year = movie.getString("Year");
                String imdbID = movie.getString("imdbID");
                String type = movie.getString("Type");
                String posterUrl = movie.getString("Poster");

                createTitleTextView(title);
                createImageVIew(posterUrl, imdbID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void NewActivity(String imdbID)
    {
        Intent intent = new Intent(this, Movie.class);
        intent.putExtra("Movie ID", imdbID);
        startActivity(intent);
    }

    public void createImageVIew(String url, String imdbID)
    {
        ImageView imageView = new ImageView(this);
        Picasso.get().load(url).into(imageView);
        imageView.setPadding(0, 50, 0,0);
        LinearLayout.LayoutParams imageLayout = new LinearLayout.LayoutParams(500, 500);
        imageLayout.gravity = Gravity.CENTER;
        imageView.setLayoutParams(imageLayout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewActivity(imdbID);
            }
        });
        ll.addView(imageView);
    }

    public void createTitleTextView(String title){
        TextView textTitle = new TextView(this);
        textTitle.setText(title);
        textTitle.setTextSize(24);
        textTitle.setGravity(Gravity.CENTER);
        textTitle.setPadding(32,50,32,0);
        textTitle.setTextColor(Color.rgb(255,255,255));
        textTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(textTitle);
    }

    public void createYearTextView(String year){
        TextView textYear = new TextView(this);
        textYear.setText(year);
        textYear.setTextSize(24);
        textYear.setGravity(Gravity.CENTER);
        textYear.setPadding(0,20,0,0);
        textYear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(textYear);
    }

    public void createImdbIDTextView(String id){
        TextView textImdbID = new TextView(this);
        textImdbID.setText(id);
        textImdbID.setTextSize(24);
        textImdbID.setGravity(Gravity.CENTER);
        textImdbID.setPadding(0,20,0,0);
        textImdbID.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(textImdbID);
    }
}