package com.saikiran.task.helpers;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saikiran.task.models.ListModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }



    public static void hideKeyboardIfOpened(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getWindow().getDecorView().getRootView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static ArrayList<ListModel> getListFromPreference(PSR_PrefsManager psr_prefsManager,String key) {

        Gson gson = new Gson();
        String json = psr_prefsManager.get(key, null);
        Type type = new TypeToken<ArrayList<ListModel>>() {
        }.getType();
        return gson.fromJson(json, type);
    }



    public  static  void saveArrayList(PSR_PrefsManager psr_prefsManager,List<ListModel> list, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        psr_prefsManager.save(key, json);

    }
}
