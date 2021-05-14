package com.mystihgreeh.go4lunch.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.mystihgreeh.go4lunch.R;
import com.mystihgreeh.go4lunch.repository.SaveDataRepository;

public class SettingsActivity extends PreferenceActivity {

    private SaveDataRepository saveDataRepository;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        //saveDataRepository.getPreferences();


    }


}