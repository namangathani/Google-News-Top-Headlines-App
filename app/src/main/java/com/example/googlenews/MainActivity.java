package com.example.googlenews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ClickInterface{

//    ListView listView;
//    String[] array={"one","two","three","four","five"};

    RecyclerView recyclerView;
    ProgressBar progressBar;

    static ArrayList<Model>data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listView = findViewById(R.id._dynamic);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.test_list_item,array);
        //listView.setAdapter(adapter);

        recyclerView = findViewById(R.id.recyclerView);

        data = new ArrayList<>();

        progressBar = findViewById(R.id.progress_circular);

        getDataFromGoogleNews();
    }
    public void getDataFromGoogleNews(){

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=894b7c59a9f14c41abe7352ed166a607";
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    client.get(url, params, new JsonHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            try {
                                JSONArray articles = response.getJSONArray("articles");
                                Log.e("length", String.valueOf(articles.length()));

                                for(int i=0;i<articles.length();i++)
                                {
                                    JSONObject article = (JSONObject) articles.get(i);

                                    Model articlModel = new Model(article.getString("title"),article.getString("urlToImage"),article.getString("description"),article.getString("author"),article.getString("content"));
                                    Log.e("title",article.getString("title"));
                                    data.add(articlModel);
                                }

                                MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(data,MainActivity.this);

                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                                recyclerView.setAdapter(adapter);

                                adapter.setclickListener(MainActivity.this);

                                progressBar.setVisibility(View.GONE);


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("exception",e.toString());
                            }
                            Log.e("jsondata",response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.e("error",Integer.toString(statusCode));
                            Toast.makeText(MainActivity.this,"Failure"+Integer.toString(statusCode),Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exception",e.toString());
                }

            }
        };
        mainHandler.post(myRunnable);

    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this,PostDetails.class);
        intent.putExtra("index",position);
        startActivity(intent);

    }
}
