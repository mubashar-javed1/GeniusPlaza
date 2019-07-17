package com.mobi.geniusplaza.ui.createuseractivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;


import com.mobi.geniusplaza.MyApplication;
import com.mobi.geniusplaza.R;
import com.mobi.geniusplaza.data.User;
import com.mobi.geniusplaza.networkcall.ResponseCreateUser;
import com.mobi.geniusplaza.ui.BaseActivity;
import com.mobi.geniusplaza.viewmodel.AddUserViewModel;
import com.mobi.geniusplaza.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddUserActivity extends BaseActivity {
    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_first_name)
    EditText editFirstName;
    @BindView(R.id.edit_last_name)
    EditText editLastName;
    @BindView(R.id.edit_email)
    EditText editEmail;

    AddUserViewModel viewModel;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        setToolbar(toolbar);
        setUpHome();

        ((MyApplication) getApplication()).getAppComponent().injectUserActivity(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddUserViewModel.class);
        viewModel.getResponseLiveData().observe(this, this::consumeResponse);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating User");
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        saveUser();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveUser() {
        if (isInValid(editFirstName, editLastName, editEmail))
            return;

        if (!isValidEmail(editEmail)) {
            editEmail.setError(getString(R.string.invalid_email));
            return;
        }

        User user = new User();
        user.setEmail(getString(editEmail));
        user.setFirstName(getString(editFirstName));
        user.setLastName(getString(editLastName));

        viewModel.createUser(user);
    }

    private void consumeResponse(ResponseCreateUser response) {
        switch (response.status) {
            case LOADING:
                showProgressBar();
                break;

            case SUCCESS:
                if (response.user != null) {
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("USER", response.user);
                    setResult(RESULT_OK, intent);
                    showToast(getString(R.string.user_created));
                    finish();
                }
                break;

            case ERROR:
                dialog.dismiss();
                if (response.error != null)
                    showToast(response.error.getMessage());
                break;

            default:
        }
    }

    public void showProgressBar() {
        if (!dialog.isShowing())
            dialog.show();
    }
}