package com.alexbondarenko.newsviewer.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.adapters.NewsAdapter;
import com.alexbondarenko.newsviewer.data.storage.FavoritesStorage;
import com.alexbondarenko.newsviewer.pojo.news.ArticlesItem;

import java.util.Collections;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView = findViewById(R.id.af_recyclerView);

        fillList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillList();
    }

    private void fillList() {
        List<ArticlesItem> favoritesArticles = FavoritesStorage.getInstance(this).getFavorites();
        Collections.reverse(favoritesArticles);
        NewsAdapter adapter = new NewsAdapter(getBaseContext(),favoritesArticles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}