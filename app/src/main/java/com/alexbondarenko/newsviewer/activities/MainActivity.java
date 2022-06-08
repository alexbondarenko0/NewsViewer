package com.alexbondarenko.newsviewer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.alexbondarenko.newsviewer.adapters.NewsAdapter;
import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.network.NetworkService;
import com.alexbondarenko.newsviewer.pojo.news.ArticlesItem;
import com.alexbondarenko.newsviewer.pojo.news.News;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView currentCategory;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.ma_recyclerView);
        currentCategory = findViewById(R.id.tv1);
        refreshLayout = findViewById(R.id.ma_swipeRefresh);
        sharedPreferences = getSharedPreferences("hasLogin", MODE_PRIVATE);

        boolean hasLogin = sharedPreferences.getBoolean("hasLogin", false);
        if (!hasLogin) {
            // Далее - срабатывает при первом запуке
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        } else {
            // Далее - срабатывает при последующих запуске
        }

        fillList();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillList();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        fillList();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    void fillList()
    {
        String category, sortBy, language;
        SharedPreferences sharedPreferences1 = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int categoryID = sharedPreferences1.getInt("category", 0);
        int sortByID = sharedPreferences1.getInt("sort", 0);
        int languageID = sharedPreferences1.getInt("language", 0);
        category = getResources().getStringArray(R.array.categories)[categoryID];
        sortBy = getResources().getStringArray(R.array.sortBy)[sortByID];
        language = getResources().getStringArray(R.array.languages)[languageID];

        try {
            NetworkService.getInstance()
                    .getNewsApi()
                    .getNews(category, sortBy, language)
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            News news = response.body();

                            setCurrentCategory(categoryID);
                            List<ArticlesItem> articles;
                            articles = news.getArticles();
                            NewsAdapter adapter = new NewsAdapter(getBaseContext(), articles);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                        @Override
                        public void onFailure(Call<News> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Catch", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setCurrentCategory (int categoryID) {
        String lang = Locale.getDefault().getCountry();
        switch (lang) {
            case "US":
                currentCategory.setText(getString(R.string.ma_chosen_category) +" "+ getResources().getStringArray(R.array.categories)[categoryID]);
                break;

            case "RU":
                currentCategory.setText(getString(R.string.ma_chosen_category) +" "+ getResources().getStringArray(R.array.categoriesRU)[categoryID]);
                break;

            default:
                currentCategory.setText(getString(R.string.ma_chosen_category) +" "+ getResources().getStringArray(R.array.categories)[categoryID]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.m_exit:
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putBoolean("hasLogin", false);
                e.apply();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
                return true;
            case R.id.m_settings:
                Intent intent2 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.m_favorites:
                Intent intent3 = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent3);
                return true;
        }
        return true;
    }
}