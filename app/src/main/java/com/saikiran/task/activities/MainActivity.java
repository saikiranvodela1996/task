package com.saikiran.task.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saikiran.task.R;
import com.saikiran.task.adapters.Adapter;
import com.saikiran.task.helpers.Keys;
import com.saikiran.task.helpers.PSR_PrefsManager;
import com.saikiran.task.helpers.Utils;
import com.saikiran.task.models.ListModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.email_Et)
    EditText emailEt;
    @BindView(R.id.number_Et)
    EditText numberEt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.noList_tv)
    TextView noListTv;

    List<ListModel> modelList;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        modelList = new ArrayList<>();

        List<ListModel> listFromPreference = Utils.getListFromPreference(psr_prefsManager, Keys.TOTALLIST);
        if (listFromPreference != null)
            modelList.addAll(listFromPreference);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(modelList);
        recyclerView.setAdapter(adapter);
        if (modelList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noListTv.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noListTv.setVisibility(View.GONE);
        }
        Utils.hideKeyboardIfOpened(this);
    }


    @OnClick(R.id.submit_btn)
    void onClickSubmit(View view) {
        Utils.hideKeyboardIfOpened(this);
        String email = emailEt.getText().toString();
        String number = numberEt.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utils.showToast(this, getResources().getString(R.string.enter_validemail_msg));
            return;
        }
        if (modelList.size() != 0) {
            for (ListModel model : modelList) {
                if (model.getEmail().equalsIgnoreCase(email)) {
                    Utils.showToast(this, getResources().getString(R.string.already_enteredemail_msg));
                    return;
                }
            }
        }
        if (number.isEmpty()) {
            Utils.showToast(this, getResources().getString(R.string.enter_numb_msg));
            return;
        }
        if (modelList.size() != 0) {
            for (ListModel model : modelList) {
                if (model.getNumber().equalsIgnoreCase(number)) {
                    Utils.showToast(this, getResources().getString(R.string.enter_numbregistered_msg));
                    return;
                }
            }
        }

        ListModel listModel = new ListModel();
        listModel.setEmail(email);
        listModel.setNumber(number);
        modelList.add(listModel);
        adapter.notifyDataSetChanged();
        emailEt.setText("");
        numberEt.setText("");
        Utils.saveArrayList(psr_prefsManager, modelList, Keys.TOTALLIST);
        Utils.showToast(this, getResources().getString(R.string.added_successfully_msg));
        if (recyclerView.getVisibility()==View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
            noListTv.setVisibility(View.GONE);
        }
    }


}