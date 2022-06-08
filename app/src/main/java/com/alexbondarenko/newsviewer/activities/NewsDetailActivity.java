package com.alexbondarenko.newsviewer.activities;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.data.storage.FavoritesStorage;
import com.alexbondarenko.newsviewer.pojo.news.ArticlesItem;

public class NewsDetailActivity extends AppCompatActivity {

    TextView title, author, publishedAt, description;
    Button openView, closeView;
    WebView webView;
    ArticlesItem currArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = findViewById(R.id.nda_tvTitle);
        author = findViewById(R.id.nda_tvAuthor);
        publishedAt = findViewById(R.id.nda_tvPublishedAt);
        description = findViewById(R.id.nda_tvDesc);
        openView = findViewById(R.id.nda_btnOpenView);
        closeView = findViewById(R.id.nda_btnCloseView);
        webView = findViewById(R.id.nda_webView);
        currArticle = (ArticlesItem) getIntent().getExtras().getSerializable(ArticlesItem.class.getSimpleName());

        title.setText(currArticle.getTitle());
        author.setText(currArticle.getAuthor());
        publishedAt.setText(currArticle.getPublishedAt());
        description.setText(currArticle.getDescription());
        webView.loadUrl(currArticle.getUrl());

        webView.setVisibility(GONE);
        closeView.setVisibility(GONE);

        openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setVisibility(GONE);
                author.setVisibility(GONE);
                publishedAt.setVisibility(GONE);
                description.setVisibility(GONE);
                openView.setVisibility(GONE);
                webView.setVisibility(View.VISIBLE);
                closeView.setVisibility(View.VISIBLE);
            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setVisibility(View.VISIBLE);
                author.setVisibility(View.VISIBLE);
                publishedAt.setVisibility(View.VISIBLE);
                description.setVisibility(View.VISIBLE);
                openView.setVisibility(View.VISIBLE);
                webView.setVisibility(GONE);
                closeView.setVisibility(GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        MenuItem itemSwitch = menu.findItem(R.id.switch_action_bar);
        itemSwitch.setActionView(R.layout.use_switch);
        //FavoritesStorage.getInstance(getApplicationContext()).clearFavorites();
        final Switch sw = (Switch) menu.findItem(R.id.switch_action_bar).getActionView().findViewById(R.id.switch2);

        sw.setChecked(FavoritesStorage.getInstance(getApplicationContext()).hasIn(currArticle));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FavoritesStorage.getInstance(getApplicationContext()).addFavorites(currArticle);
                    Toast.makeText(getApplicationContext(), R.string.article_added_to_favorites, Toast.LENGTH_SHORT).show();
                }
                else {
                    FavoritesStorage.getInstance(getApplicationContext()).delFavorites(currArticle);
                    Toast.makeText(getApplicationContext(), R.string.article_removed_from_favorites, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return true;
    }
}