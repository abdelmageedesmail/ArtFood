package com.art4musilm.artfoodCustomer.ui.activites.account;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.ImageUtil;
import com.art4musilm.artfoodCustomer.databinding.ActivityPersonalInfoBinding;
import com.art4musilm.artfoodCustomer.models.requests.UpdateProfileRequest;
import com.art4musilm.artfoodCustomer.models.response.Location;
import com.art4musilm.artfoodCustomer.models.response.UpdateProfileResponse;
import com.art4musilm.artfoodCustomer.ui.activites.LocationActivity;
import com.art4musilm.artfoodCustomer.ui.activites.MainActivity;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalInfoActivity extends BaseActivity {
    ActivityPersonalInfoBinding binding;
    AccountViewModel accountViewModel;
    public final int PICK_FROM_GALLERY = 1025;
    private String selectedFilePath = "";
    private double lat = 0;
    private double lng = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpObservers();
    }

    private void setUpObservers() {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        binding.setViewModel(accountViewModel);
        binding.setLifecycleOwner(this);
        accountViewModel.getUserProfile(Integer.parseInt(sessionHelper.userId()));
        accountViewModel.observeUpdatingUserProfile().observe(this, new Observer<UpdateProfileResponse>() {
            @Override
            public void onChanged(UpdateProfileResponse updateProfileResponse) {
                if (updateProfileResponse.getStatus()) {
                    showSuccessToast(updateProfileResponse.getMessage());
                    accountViewModel.getUserProfile(Integer.parseInt(sessionHelper.userId()));
                }
            }
        });
    }


    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info);
        binding.toolbar.tittle.setText(getString(R.string.personal_info));
        binding.toolbar.backBtn.setVisibility(View.VISIBLE);
        binding.toolbar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.tvSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalInfoActivity.this, LocationActivity.class)
                        .putExtra("target", "personalInfo"));
            }
        });
    }

    public void goChangePassword(View view) {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    public void editProfile(View view) {
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    public void changeUserImg(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        } else {
            openImagesPicker();
        }
    }

    void openImagesPicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.selectyourprofilepic)), PICK_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedFileUri = data.getData();
            selectedFilePath = ImageUtil.getPath(this, selectedFileUri);
            if (selectedFilePath != null) {
                File file = new File(selectedFilePath);
                MultipartBody.Part docImage = MultipartBody.Part.createFormData(
                        "image",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image*//*"), file)
                );
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
                updateProfileRequest.setEmail(sessionHelper.getUserInfo().getEmail());
                updateProfileRequest.setActive(sessionHelper.getUserInfo().getStatus() + "");
                updateProfileRequest.setId(sessionHelper.userId());
                updateProfileRequest.setLat(lat);
                updateProfileRequest.setLng(lng);
                updateProfileRequest.setMobile(sessionHelper.getUserInfo().getMobile());
                updateProfileRequest.setType(sessionHelper.getUserInfo().getType());
                updateProfileRequest.setName(sessionHelper.getUserInfo().getNameuser());
                updateProfileRequest.setEmail(sessionHelper.getUserInfo().getEmail());
                accountViewModel.updateUserProfile(Integer.parseInt(sessionHelper.userId()), sessionHelper.getUserLanguageCode(), docImage, updateProfileRequest);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        accountViewModel.getUserProfile(Integer.parseInt(sessionHelper.userId()));
        if (LocationActivity.latitude != 0.0) {
            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
            updateProfileRequest.setLat(LocationActivity.latitude);
            updateProfileRequest.setLng(LocationActivity.longitude);
            updateProfileRequest.setEmail(sessionHelper.getUserInfo().getEmail());
            updateProfileRequest.setActive(sessionHelper.getUserInfo().getStatus() + "");
            updateProfileRequest.setId(sessionHelper.userId());
            updateProfileRequest.setMobile(sessionHelper.getUserInfo().getMobile());
            updateProfileRequest.setType(sessionHelper.getUserInfo().getType());
            updateProfileRequest.setName(sessionHelper.getUserInfo().getNameuser());
            accountViewModel.updateUserProfile(Integer.parseInt(sessionHelper.userId()), sessionHelper.getUserLanguageCode(), null, updateProfileRequest);
        }
    }
}