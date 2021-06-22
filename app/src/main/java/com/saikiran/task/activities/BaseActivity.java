package com.saikiran.task.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.saikiran.task.helpers.PSR_PrefsManager;

public class BaseActivity extends AppCompatActivity {
    PSR_PrefsManager psr_prefsManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        psr_prefsManager = new PSR_PrefsManager(this);
    }
}
