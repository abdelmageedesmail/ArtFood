package com.art4musilm.artfoodCustomer.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.session.SessionHelper;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class BaseFragment extends Fragment {
    @Inject
    public SessionHelper sessionHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    //Show success toast message
    public void showSuccessToast(String msg){
        Toasty.success(getContext(),msg,Toasty.LENGTH_LONG).show();
    }

    //Show error toast message
    public void showErrorToast(String msg){
        Toasty.error(getContext(),msg,Toasty.LENGTH_LONG).show();
    }
}