package com.example.levon.jsonproject.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.levon.jsonproject.R;
import com.example.levon.jsonproject.adapters.UserItemsAdapter;
import com.example.levon.jsonproject.models.JsonModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<JsonModel> list = new ArrayList<>();
    private ProgressBar progressBar;
    private JsonModel jsonModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setAsyncTask();
    }

    @SuppressLint("CutPasteId")
    private void findViews() {
        recyclerView = findViewById(R.id.recyc);
        progressBar = findViewById(R.id.prog);
    }

    public void setJsonModel(JsonModel jsonModel){
        this.jsonModel = jsonModel;
    }

    public JsonModel getJsonModel(){
        return jsonModel;
    }

    private void setRecyclerView() {
        UserItemsAdapter userItemsAdapter = new UserItemsAdapter(MainActivity.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(userItemsAdapter);
    }

    private void setAsyncTask(){
        JsonAsynchTask jsonAsynchTask = new JsonAsynchTask();
        jsonAsynchTask.setContext(MainActivity.this);
        jsonAsynchTask.execute("https://jsonplaceholder.typicode.com/photos");
    }



    @SuppressLint("StaticFieldLeak")
    class JsonAsynchTask extends AsyncTask<String, Void, String> {

        private MainActivity mainActivity;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mainActivity.recyclerView.setVisibility(View.INVISIBLE);
            mainActivity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mainActivity.setRecyclerView();
            mainActivity.recyclerView.setVisibility(View.VISIBLE);
            mainActivity.progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            JsonArrayRequest request = new JsonArrayRequest(strings[0], new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for (int i = 0; i < response.length(); i++) {
                        JsonModel jsonModel = new JsonModel();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            jsonModel.setId(Integer.valueOf(jsonObject.getString("id")));
                            jsonModel.setAlbumId(Integer.valueOf(jsonObject.getString("albumId")));
                            jsonModel.setTitle(jsonObject.getString("title"));
                            jsonModel.setUrl(jsonObject.getString("url"));
                            jsonModel.setThumbnailUrl(jsonObject.getString("thumbnailUrl"));
                            list.add(jsonModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mainActivity, "Something will hapened with server", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(mainActivity);
            requestQueue.add(request);
            return null;
        }

         void setContext(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }
    }

}
