package com.mobi.geniusplaza.ui.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


import com.mobi.geniusplaza.MyApplication;
import com.mobi.geniusplaza.R;
import com.mobi.geniusplaza.adapter.UserAdapter;
import com.mobi.geniusplaza.data.UserResponse;
import com.mobi.geniusplaza.networkcall.ResponseGetUser;
import com.mobi.geniusplaza.ui.BaseActivity;
import com.mobi.geniusplaza.ui.createuseractivity.AddUserActivity;
import com.mobi.geniusplaza.viewmodel.MainViewModel;
import com.mobi.geniusplaza.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE = 30;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rv_users)
    RecyclerView rvUsers;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    MainViewModel viewModel;

    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar(toolbar);

        ((MyApplication) getApplication()).getAppComponent().injectMainActivity(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.getResponseLiveData().observe(this, this::consumeResponse);

        if (savedInstanceState == null) {
            viewModel.getUsers();
        }
    }

    @OnClick(R.id.fb_add_user)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, AddUserActivity.class), REQUEST_CODE);
    }

    private void consumeResponse(ResponseGetUser response) {
        switch (response.status) {
            case LOADING:
                showProgressBar();
                break;

            case SUCCESS:
                assert response.data != null;
                updateRecyclerView(response.data);
                break;

            case ERROR:
                if (response.error != null)
                    showStatusText(response.error.getMessage());
                break;

            default:

        }
    }

    private void updateRecyclerView(UserResponse data) {
        if (data != null && !data.getUser().isEmpty()) {
            showRecyclerView();
            adapter = new UserAdapter(this, data.getUser());
            rvUsers.setAdapter(adapter);


            if (!checkNetworkAvailability()) {
                tvStatus.setText(getString(R.string.cache));
                tvStatus.setVisibility(View.VISIBLE);
            }
        } else {
            showStatusText(getString(R.string.no_result_found));
        }
    }

    protected void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        tvStatus.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
    }

    protected void showStatusText(String text) {
        progressBar.setVisibility(View.GONE);
        rvUsers.setVisibility(View.GONE);
        tvStatus.setVisibility(View.VISIBLE);
        tvStatus.setText(text);
    }

    protected void showRecyclerView() {
        progressBar.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);
        rvUsers.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            adapter.addNewUser(data.getParcelableExtra("USER"));
        }
    }
}