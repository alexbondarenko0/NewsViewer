package com.alexbondarenko.newsviewer.data.storage;

import android.content.Context;
import android.widget.Toast;

import com.alexbondarenko.newsviewer.data.models.User;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UsersStorage {

    private static UsersStorage instance;
    private static String FILE_NAME = "users.json";
    Context context;
    Gson gson;

    private UsersStorage(Context context) {
        this.context = context;
        gson = new Gson();
    }

    public static UsersStorage getInstance(Context context) {
        if (instance == null) {
            instance = new UsersStorage(context);
        }
        return instance;
    }

    public List<User> getUsers() {

        try (FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
             InputStreamReader streamReader = new InputStreamReader(fileInputStream)) {

            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return dataItems.getUsers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        DataItems dataItems = new DataItems();
        dataItems.setUsers(users);
        String jsonString = gson.toJson(dataItems);

        try (FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser(User newUser) {
        List<User> users = getUsers();
        users.add(newUser);
        setUsers(users);
    }

    public boolean hasIn(User user) {
        List<User> users = getUsers();
        assert users != null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).toString().equals(user.toString())) {
                return true;
            }
        }
        return false;
    }

    private static class DataItems {
        private List<User> favorites;

        List<User> getUsers() {
            return favorites;
        }

        void setUsers(List<User> favorites) {
            this.favorites = favorites;
        }
    }
}
