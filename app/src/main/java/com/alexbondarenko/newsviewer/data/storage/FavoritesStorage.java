package com.alexbondarenko.newsviewer.data.storage;


import android.content.Context;
import android.widget.Toast;

import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.pojo.news.ArticlesItem;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FavoritesStorage {
    private static FavoritesStorage instance;
    private static String FILE_NAME = "favorites.json";
    Context context;
    Gson gson;

    private FavoritesStorage(Context context) {
        this.context = context;
        gson = new Gson();
    }

    public static FavoritesStorage getInstance(Context context) {
        if (instance == null) {
            instance = new FavoritesStorage(context);
        }
        return instance;
    }

    public List<ArticlesItem> getFavorites() {

        try (FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
             InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {

            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return dataItems.getFavorites();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void setFavorites(List<ArticlesItem> articlesList) {
        DataItems dataItems = new DataItems();
        dataItems.setFavorites(articlesList);
        String jsonString = gson.toJson(dataItems);

        try (FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFavorites(ArticlesItem article) {
        List<ArticlesItem> favorites = getFavorites();
        favorites.add(article);
        setFavorites(favorites);
    }

    public void delFavorites(ArticlesItem article) {
        List<ArticlesItem> favorites = getFavorites();
        assert favorites != null;
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getUrl().equals(article.getUrl())) {
                favorites.remove(i);
                break;
            }
        }
        setFavorites(favorites);
    }

    public boolean hasIn(ArticlesItem article) {
        List<ArticlesItem> favorites = getFavorites();
        assert favorites != null;
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getUrl().equals(article.getUrl())) {
                return true;
            }
        }
        return false;
    }

    public void clearFavorites() {
        setFavorites(new ArrayList<>());
    }

    private static class DataItems {
        private List<ArticlesItem> favorites;

        List<ArticlesItem> getFavorites() {
            return favorites;
        }

        void setFavorites(List<ArticlesItem> favorites) {
            this.favorites = favorites;
        }
    }
}
