package com.art4musilm.artfoodCustomer.commons.numberPicker.Listener;

import android.util.Log;

import com.art4musilm.artfoodCustomer.commons.numberPicker.Enums.ActionEnum;
import com.art4musilm.artfoodCustomer.commons.numberPicker.Interface.ValueChangedListener;


/**
 * Created by travijuu on 19/12/16.
 */

public class DefaultValueChangedListener implements ValueChangedListener {

    public void valueChanged(int value, ActionEnum action) {

        String actionText = action == ActionEnum.MANUAL ? "manually set" : (action == ActionEnum.INCREMENT ? "incremented" : "decremented");
        String message = String.format("NumberPicker is %s to %d", actionText, value);
        Log.v(this.getClass().getSimpleName(), message);
    }
}
