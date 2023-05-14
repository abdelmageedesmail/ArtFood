package com.art4musilm.artfoodCustomer.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseActivity;
import com.art4musilm.artfoodCustomer.commons.Utils;
import com.art4musilm.artfoodCustomer.databinding.ActivityMainBinding;
import com.art4musilm.artfoodCustomer.databinding.SideMenuLayoutBinding;
import com.art4musilm.artfoodCustomer.events.LocationEvent;
import com.art4musilm.artfoodCustomer.models.CartModel;
import com.art4musilm.artfoodCustomer.models.response.ProfileResponse;
import com.art4musilm.artfoodCustomer.session.SessionHelper;
import com.art4musilm.artfoodCustomer.ui.activites.account.PersonalInfoActivity;
import com.art4musilm.artfoodCustomer.ui.components.PagesController;
import com.art4musilm.artfoodCustomer.ui.dialogs.ConfirmDialog;
import com.art4musilm.artfoodCustomer.viewModels.AccountViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobeedev.library.SlidingMenuBuilder;
import com.mobeedev.library.SlidingNavigation;
import com.mobeedev.library.dragstate.DragStateListener;
import com.mobeedev.library.gravity.SlideGravity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.art4musilm.artfoodCustomer.commons.Utils.openUrl;
import static com.art4musilm.artfoodCustomer.commons.keys.FAMILY_APP_URL;
import static com.art4musilm.artfoodCustomer.commons.keys.HOME;
import static com.art4musilm.artfoodCustomer.commons.keys.OFFERS;
import static com.art4musilm.artfoodCustomer.commons.keys.ORDERS;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    int position = 0;
    SlidingNavigation slidingMenuBuilder;
    SideMenuLayoutBinding sideMenuLayoutBinding;
    AccountViewModel accountViewModel;
    boolean cartIsEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpUi();
        setUpSideMenu(savedInstanceState);
        setUpCartObserver();
        Utils.addPermissions(this);
    }

    private void setUpSideMenu(Bundle savedInstanceState) {
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        sideMenuLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.side_menu_layout, null, false);
        accountViewModel.observeUserProfile().observe(this, new Observer<ProfileResponse>() {
            @Override
            public void onChanged(ProfileResponse profileResponse) {
                sideMenuLayoutBinding.setProfile(profileResponse.getData());
            }
        });
        if (sessionHelper.isLogin()) {
            accountViewModel.getUserProfile(Integer.parseInt(sessionHelper.userId()));
        }

        slidingMenuBuilder = new SlidingMenuBuilder(MainActivity.this)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withGravity(sessionHelper.isArabic(this) ? SlideGravity.RIGHT : SlideGravity.LEFT)
                .addDragStateListener(new DragStateListener() {
                    @Override
                    public void onDragStart() {

                    }

                    @Override
                    public void onDragEnd(boolean b) {
                        unSelectAll();
                    }
                })
                .withMenuView(sideMenuLayoutBinding.getRoot())
                .inject();
    }


    private void setUpUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        PagesController.switchToPage(HOME, getTransaction());
        unSelectAll();
        binding.nav.curvedButtonBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.offers_nav: {
                        item.setCheckable(true);
                        if (sessionHelper.isLogin()) {
                            position = PagesController.switchToPage(OFFERS, getTransaction());
                        } else {
                            showErrorToast(getString(R.string.pleaseLoginFirst));
                        }
                        break;
                    }
                    case R.id.orders_nav: {
                        item.setCheckable(true);
                        if (sessionHelper.isLogin()) {
                            position = PagesController.switchToPage(ORDERS, getTransaction());
                        } else {
                            showErrorToast(getString(R.string.pleaseLoginFirst));
                        }
                        break;
                    }
                    case R.id.more_nav: {
                        unSelectAll();
                        if (slidingMenuBuilder.isMenuClosed()) {
                            slidingMenuBuilder.openMenu(true);
                        } else {
                            slidingMenuBuilder.closeMenu(true);
                        }
                        break;
                    }
                    case R.id.cart_nav: {
                        unSelectAll();
                        if (sessionHelper.isLogin()) {
                            if (cartIsEmpty) {
                                showErrorToast(getString(R.string.cartnotempty));
                            } else {
                                startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
                            }
                        } else {
                            showErrorToast(getString(R.string.pleaseLoginFirst));
                        }

                        break;
                    }
                }
                return true;
            }
        });
        binding.nav.homeBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = PagesController.switchToPage(HOME, getTransaction());
                unSelectAll();
            }
        });


    }

    public void unSelectAll() {
        binding.nav.curvedButtonBar.getMenu().findItem(R.id.nav_blank_item).setChecked(true);
    }

    public void onItemClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_payment_ways: {
                if (sessionHelper.isLogin()) {
                    startActivity(new Intent(MainActivity.this, PaymentWaysActivity.class)
                            .putExtra("from", "home"));
                } else {
                    showErrorToast(getString(R.string.pleaseLoginFirst));
                }
                break;
            }


            case R.id.personal_inf_action: {
                if (sessionHelper.isLogin()) {
                    startActivity(new Intent(MainActivity.this, PersonalInfoActivity.class));
                } else {
                    showErrorToast(getString(R.string.pleaseLoginFirst));
                }
                break;
            }
            case R.id.rules_nav: {
                startActivity(new Intent(MainActivity.this, RulesActivity.class)
                        .putExtra("target", "policy"));
                break;
            }
            case R.id.about_nav: {
                startActivity(new Intent(MainActivity.this, RulesActivity.class)
                        .putExtra("target", "about"));
                break;
            }
            case R.id.invoices_nav: {
                if (sessionHelper.isLogin()) {
                    startActivity(new Intent(MainActivity.this, InvoicesActivity.class));
                } else {
                    showErrorToast(getString(R.string.pleaseLoginFirst));
                }
                break;
            }
            case R.id.contact_nav: {
                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                break;
            }
            case R.id.nav_settings: {
                startActivity(new Intent(MainActivity.this, LocationActivity.class)
                        .putExtra("target", "Home"));
                break;
            }
            case R.id.nav_register_family: {
                openUrl(this, FAMILY_APP_URL);
                break;
            }
            case R.id.logout_nav: {
                new ConfirmDialog(this, getString(R.string.aeyousurelogout), new ConfirmDialog.ConfirmClickActions() {
                    @Override
                    public void onConfirmed() {
                        sessionHelper.logout(new SessionHelper.SessionCallBack() {
                            @Override
                            public void setOnLogout() {
                                logout(MainActivity.this);
                            }
                        });
                    }
                }).show();
            }
        }
        slidingMenuBuilder.closeMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionHelper.isLogin()) {
            accountViewModel.getUserProfile(Integer.parseInt(sessionHelper.userId()));
        }

    }

    private void setUpCartObserver() {
        shoppingCartViewModel.observeCartItems().observe(this, new Observer<List<CartModel>>() {
            @Override
            public void onChanged(List<CartModel> products) {
                cartIsEmpty = products.isEmpty();
                if (products.isEmpty()) {
                    binding.nav.curvedButtonBar.getOrCreateBadge(R.id.cart_nav).clearNumber();
                    binding.nav.curvedButtonBar.removeBadge(R.id.cart_nav);
                } else {
                    binding.nav.curvedButtonBar.getOrCreateBadge(R.id.cart_nav).setNumber(products.size());
                }
            }
        });

    }


}