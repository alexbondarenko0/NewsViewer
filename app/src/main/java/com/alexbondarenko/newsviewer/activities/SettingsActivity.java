package com.alexbondarenko.newsviewer.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.alexbondarenko.newsviewer.R;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    Spinner categorySpinner, sortSpinner, langSpinner;
    Button saveButton;
    int category, sortBy, language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        categorySpinner = findViewById(R.id.as_spinnerCategoty);
        sortSpinner = findViewById(R.id.as_spinnerSort);
        langSpinner = findViewById(R.id.as_spinnerLang);
        saveButton = findViewById(R.id.as_btnSave);

        setSpinnersEntries();

        SharedPreferences sharedPreferences1 = getSharedPreferences("AppSettings", MODE_PRIVATE);
        int categoryID = sharedPreferences1.getInt("category", 0);
        int sortByID = sharedPreferences1.getInt("sort", 0);
        int languageID = sharedPreferences1.getInt("language", 0);

        categorySpinner.setSelection(categoryID);
        sortSpinner.setSelection(sortByID);
        langSpinner.setSelection(languageID);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setSpinnersEntries() {
        String lang = Locale.getDefault().getCountry();
        String[] categories, sortTypes, languages;
        switch (lang) {
            case "US":
                categories = getResources().getStringArray(R.array.categories);
                sortTypes = getResources().getStringArray(R.array.sortByUS);
                languages = getResources().getStringArray(R.array.languagesUS);
                break;

            case "RU":
                categories = getResources().getStringArray(R.array.categoriesRU);
                sortTypes = getResources().getStringArray(R.array.sortByRU);
                languages = getResources().getStringArray(R.array.languagesRU);
                break;

            default:
                categories = getResources().getStringArray(R.array.categories);
                sortTypes = getResources().getStringArray(R.array.sortBy);
                languages = getResources().getStringArray(R.array.languages);
        }

        ArrayAdapter<String> spinnerCategoriesAdapter = new ArrayAdapter<String>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories);
        ArrayAdapter<String> spinnerSortTypesAdapter = new ArrayAdapter<String>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sortTypes);
        ArrayAdapter<String> spinnerLanguagesAdapter = new ArrayAdapter<String>(
                this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, languages);

        categorySpinner.setAdapter(spinnerCategoriesAdapter);
        sortSpinner.setAdapter(spinnerSortTypesAdapter);
        langSpinner.setAdapter(spinnerLanguagesAdapter);
    }

    @Override
    public void onBackPressed() {
        saveChanges();
        super.onBackPressed();
    }

    void saveChanges() {
        category = categorySpinner.getSelectedItemPosition();
        sortBy = sortSpinner.getSelectedItemPosition();
        language = langSpinner.getSelectedItemPosition();

        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("category", category);
        editor.putInt("sort", sortBy);
        editor.putInt("language", language);
        editor.apply();
    }
}