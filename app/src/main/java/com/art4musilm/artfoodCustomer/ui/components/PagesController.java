package com.art4musilm.artfoodCustomer.ui.components;

import androidx.fragment.app.FragmentTransaction;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.ui.fragments.HomeFragment;
import com.art4musilm.artfoodCustomer.ui.fragments.OffersFragment;
import com.art4musilm.artfoodCustomer.ui.fragments.OrdersFragment;

import static com.art4musilm.artfoodCustomer.commons.keys.HOME;
import static com.art4musilm.artfoodCustomer.commons.keys.OFFERS;
import static com.art4musilm.artfoodCustomer.commons.keys.ORDERS;
import static com.art4musilm.artfoodCustomer.commons.keys.PROFILE;

public class PagesController {
    public static int switchToPage(int page, FragmentTransaction fragmentTransaction) {
        FragmentTransaction transaction = fragmentTransaction;
        int pageIndex = 0;
        switch (page) {
            case HOME: {
                transaction.replace(R.id.content, new HomeFragment());
                pageIndex = HOME;
                break;
            }

            case ORDERS: {
                transaction.replace(R.id.content, new OrdersFragment());
                pageIndex = ORDERS;
                break;
            }

            case OFFERS: {
                transaction.replace(R.id.content, new OffersFragment());
                pageIndex = OFFERS;
                break;
            }

            case PROFILE: {
//                transaction.replace(R.id.content, new ProfileFragment());
                pageIndex = PROFILE;
                break;
            }


        }
        transaction.commit();
        return pageIndex;
    }

}
