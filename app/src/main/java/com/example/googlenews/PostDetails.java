package com.example.googlenews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PostDetails extends AppCompatActivity {

    TextView title;
    TextView detail;
    ImageView thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        title = findViewById(R.id.txtPostTitle);
        detail = findViewById(R.id.txtDetail);
        thumb = findViewById(R.id.imgThumb);

        int index = getIntent().getIntExtra("index",0);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }catch(Exception e){
            e.printStackTrace();
        }

        Model article = MainActivity.data.get(index);

        title.setText(article.title);
        detail.setText(article.content);
        Glide.with(this).load(article.imageUrl).into(thumb);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
