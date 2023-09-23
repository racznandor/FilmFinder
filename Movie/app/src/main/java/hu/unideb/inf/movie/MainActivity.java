package hu.unideb.inf.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public JSONArray array;

    public TextInputEditText movieName;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieName = findViewById(R.id.movieName);
        button = findViewById(R.id.searchButton);
    }

    public void searchClicked(View view) {
        new MoviesAsync(movieName, button).execute();
    }

    public void NewActivity()
    {
        Intent intent = new Intent(this, MovielistActivity.class);
        intent.putExtra("Movie List", array.toString());
        startActivity(intent);
    }

    public class MoviesAsync extends AsyncTask<Void, String, JSONArray> {

        private WeakReference<TextInputEditText> weakName;
        private WeakReference<Button> weakButton;

        public MoviesAsync(TextInputEditText tv, Button b){
            this.weakName = new WeakReference<>(tv);
            this.weakButton = new WeakReference<>(b);
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            String s = weakName.get().getText().toString();
            JSONArray search = new JSONArray();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://movie-database-alternative.p.rapidapi.com/?s=" + s + "&r=json&page=1")
                    .get()
                    .addHeader("X-RapidAPI-Key", "2d710340ebmsh239cd13c6a2ddb1p103a1djsn3de419bde264")
                    .addHeader("X-RapidAPI-Host", "movie-database-alternative.p.rapidapi.com")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                JSONObject object = new JSONObject(response.body().string());
                System.out.println(object);
                search = object.getJSONArray("Search");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return search;
        }

        protected void onPostExecute(JSONArray search) {
            super.onPostExecute(search);
            if (search.length() == 0 && weakName.get().getText().length() > 1){
                Toast.makeText(getApplicationContext() , "Nincs ilyen film", Toast.LENGTH_LONG).show();
            }
            else if (search.length() == 0)
            {
                Toast.makeText(getApplicationContext() , "Adja meg a fílm címét!", Toast.LENGTH_LONG).show();
            }
            else {
                array = search;
                NewActivity();
            }
        }
    }
}
