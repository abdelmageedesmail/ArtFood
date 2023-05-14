package com.art4musilm.artfoodCustomer.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.art4musilm.artfoodCustomer.R;
import com.art4musilm.artfoodCustomer.base.BaseFragment;
import com.art4musilm.artfoodCustomer.databinding.FragmentHomeBinding;
import com.art4musilm.artfoodCustomer.events.ErrorMessageEvent;
import com.art4musilm.artfoodCustomer.events.LocationEvent;
import com.art4musilm.artfoodCustomer.gps.GPSTracker;
import com.art4musilm.artfoodCustomer.models.response.CategoriesResponse;
import com.art4musilm.artfoodCustomer.models.response.Category;
import com.art4musilm.artfoodCustomer.models.response.FamiliesResponse;
import com.art4musilm.artfoodCustomer.models.response.Family;
import com.art4musilm.artfoodCustomer.models.response.FamilySearchResponse;
import com.art4musilm.artfoodCustomer.models.response.Slider;
import com.art4musilm.artfoodCustomer.models.response.SliderResponse;
import com.art4musilm.artfoodCustomer.ui.activites.FamilyDetailsActivity;
import com.art4musilm.artfoodCustomer.ui.activites.LocationActivity;
import com.art4musilm.artfoodCustomer.ui.activites.StoreActivity;
import com.art4musilm.artfoodCustomer.ui.adapters.CategoriesAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.ProductivityFamiliesAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.SearchProductivityFamiliesAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.SliderAdapter;
import com.art4musilm.artfoodCustomer.ui.adapters.StoresAdapter;
import com.art4musilm.artfoodCustomer.viewModels.StoresViewModel;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.labo.kaji.fragmentanimations.FlipAnimation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout;

import static android.app.Activity.RESULT_OK;
import static com.art4musilm.artfoodCustomer.commons.Utils.autoScrollRecycler;


public class HomeFragment extends BaseFragment implements
        CategoriesAdapter.CategoryClickAction,
        StoresAdapter.StoresClickAction,
        ProductivityFamiliesAdapter.StoresClickAction,
        SearchProductivityFamiliesAdapter.StoresSerchClickAction {
    FragmentHomeBinding binding;
    SliderAdapter sliderAdapter;
    CategoriesAdapter categoriesAdapter;
    StoresAdapter storesAdapter;
    ProductivityFamiliesAdapter productivityFamiliesAdapter;
    SearchProductivityFamiliesAdapter searchProductivityFamiliesAdapter;
    StoresViewModel storesViewModel;
    List<Category> categoryList = new ArrayList<>();
    List<Slider> sliders = new ArrayList<>();
    List<Family> families = new ArrayList<>();
    List<Family> familySearchList = new ArrayList<>();
    String selectedCategory = "0";
    GPSTracker mGps;

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return FlipAnimation.create(FlipAnimation.LEFT, enter, 1000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpAdsSlider();
        setUpCategoriesList();
//        setUpProductivityFamiliesList();
        setUpStoresList();
        setUpSearching();
        binding.locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LocationActivity.class)
                        .putExtra("target", "Home"));
            }
        });
        setUpObservers();
    }

    private void setUpSearching() {
        binding.searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    storesViewModel.getFamilySearch("", mGps.getLatitude(), mGps.getLongitude());
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

//        binding.searchInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                productivityFamiliesAdapter.getFilter().filter(charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
    }

    private void setUpObservers() {
        //Get Categories
        storesViewModel = new ViewModelProvider(this).get(StoresViewModel.class);
        binding.setViewModel(storesViewModel);
        mGps = new GPSTracker(getActivity());
        binding.setLifecycleOwner(this);
        storesViewModel.observeCategories().observe(getViewLifecycleOwner(), new Observer<CategoriesResponse>() {
            @Override
            public void onChanged(CategoriesResponse categoriesResponse) {
                if (categoriesResponse.getStatus()) {
                    if (categoriesResponse.getData() != null) {
                        categoryList.clear();
                        categoryList.addAll(categoriesResponse.getData());
                        categoriesAdapter.notifyDataSetChanged();
                        if (!categoriesResponse.getData().isEmpty()) {
                            selectedCategory = categoriesResponse.getData().get(0).getId();
                            getFamilies(categoriesResponse.getData().get(0).getId());
                        }
                    }
                }
            }
        });
        storesViewModel.getCategories();

        //Get top slider
        storesViewModel.observeSlider().observe(getViewLifecycleOwner(), new Observer<SliderResponse>() {
            @Override
            public void onChanged(SliderResponse sliderResponse) {
                if (sliderResponse.getStatus()) {
                    if (sliderResponse.getData() != null) {
                        sliders.clear();
                        sliders.addAll(sliderResponse.getData());
                        sliderAdapter.notifyDataSetChanged();
                        binding.pageIndicator.setCount(sliderResponse.getData().size());
                    }
                }
            }
        });
        storesViewModel.getSlider();

        //Get families
        storesViewModel.observeFamilies().observe(getViewLifecycleOwner(), new Observer<FamiliesResponse>() {
            @Override
            public void onChanged(FamiliesResponse familiesResponse) {
                if (familiesResponse.getStatus()) {
                    setUpProductivityFamiliesList();
                    families.clear();
                    families.addAll(familiesResponse.getData());
                    productivityFamiliesAdapter.notifyDataSetChanged();
                }

            }
        });

        storesViewModel.observeFamilySearchResponse().observe(this, new Observer<FamiliesResponse>() {
            @Override
            public void onChanged(FamiliesResponse familiesResponse) {
                if (familiesResponse.getStatus()) {
                    familySearchList.clear();
                    families.clear();
                    if (productivityFamiliesAdapter != null) {
                        productivityFamiliesAdapter.notifyDataSetChanged();
                    }
                    setUpProductivityFamiliesList();
                    for (int i = 0; i < familiesResponse.getData().size(); i++) {
                        Family family;
                        if (familiesResponse.getData().get(i).getName().contains(binding.searchInput.getText().toString())) {
                            family = familiesResponse.getData().get(i);
                            familySearchList.add(family);
                        }
                    }
                    List<Family> familyList = removeDuplicates(familySearchList);
                    families.addAll(familyList);
                    Log.e("familySearchSize", "" + families.size());
                    productivityFamiliesAdapter.notifyDataSetChanged();
                    binding.searchInput.setText("");
                }
            }
        });
    }


    public List<Family> removeDuplicates(List<Family> list) {
        // Set set1 = new LinkedHashSet(list);
        Set set = new TreeSet(new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                if (((Family) o1).getId().equalsIgnoreCase(((Family) o2).getId()) /*&&
                    ((Blog)o1).getName().equalsIgnoreCase(((Blog)o2).getName())*/) {
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final List newList = new ArrayList(set);
        return newList;
    }

    private void setUpStoresList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        storesAdapter = new StoresAdapter(getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.storesList.setLayoutManager(layoutManager);
        binding.storesList.setAdapter(storesAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.storesList);
        binding.storesList.setOnFlingListener(gravitySnapHelperSlider);
    }

    private void setUpProductivityFamiliesList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        productivityFamiliesAdapter = new ProductivityFamiliesAdapter(getContext(), this, families, sessionHelper.getUserLanguageCode());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.productiveFamiliesList.setLayoutManager(layoutManager);
        binding.productiveFamiliesList.setAdapter(productivityFamiliesAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.productiveFamiliesList);
        binding.productiveFamiliesList.setOnFlingListener(gravitySnapHelperSlider);
    }

    private void setUpSearchProductivityFamiliesList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        productivityFamiliesAdapter = new ProductivityFamiliesAdapter(getContext(), this, familySearchList, sessionHelper.getUserLanguageCode());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.productiveFamiliesList.setLayoutManager(layoutManager);
        binding.productiveFamiliesList.setAdapter(productivityFamiliesAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.productiveFamiliesList);
        binding.productiveFamiliesList.setOnFlingListener(gravitySnapHelperSlider);
    }

    private void setUpCategoriesList() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.START);
        categoriesAdapter = new CategoriesAdapter(getContext(), this, sessionHelper.getUserLanguageCode(), categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.categoriesList.setLayoutManager(layoutManager);
        binding.categoriesList.setAdapter(categoriesAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.categoriesList);
        binding.categoriesList.setOnFlingListener(gravitySnapHelperSlider);
    }

    private void setUpAdsSlider() {
        GravitySnapHelper gravitySnapHelperSlider = new GravitySnapHelper(Gravity.CENTER);
        sliderAdapter = new SliderAdapter(getContext(), sliders);
        LinearLayoutManager layoutManager = new ZoomRecyclerLayout(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.adsSliderLayout.setLayoutManager(layoutManager);
        binding.adsSliderLayout.setAdapter(sliderAdapter);
        gravitySnapHelperSlider.attachToRecyclerView(binding.adsSliderLayout);
        binding.adsSliderLayout.setOnFlingListener(gravitySnapHelperSlider);
        binding.pageIndicator.setCount(sliderAdapter.getItemCount());
        autoScrollRecycler(binding.adsSliderLayout, binding.pageIndicator, sliderAdapter.getItemCount());
    }

    @Override
    public void onCategorySelected(Category category) {
        selectedCategory = category.getId();
        getFamilies(category.getId());
    }

    private void getFamilies(String id) {
//        if (sessionHelper.getLocation() != null) {
//            storesViewModel.getFamilies(id, sessionHelper.getLocation().getLat(), sessionHelper.getLocation().getLng());
//        } else {
        Log.e("Locatonsss", "" + mGps.getLatitude() + "..." + mGps.getLongitude());
        storesViewModel.getFamilies(id, mGps.getLatitude(), mGps.getLongitude());
//        }
    }

    @Override
    public void onFamilySelected(Family family) {
        Intent intent = new Intent(getContext(), FamilyDetailsActivity.class);
        intent.putExtra("id", family.getId() + "");
        intent.putExtra("isDelivery", family.getIsdelivery());
        intent.putExtra("status", family.getFamilystatusNameen());
        if (family.getIsdelivery().equals("Y")) {
            intent.putExtra("price", "" + family.getDeliveryprice());
        } else {
            intent.putExtra("price", "" + family.getPriceequation());
        }
        startActivity(intent);
    }

    @Override
    public void onStoreSelected() {
        startActivity(new Intent(getContext(), StoreActivity.class));
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void setLocationEvent(LocationEvent locationEvent) {
        storesViewModel.getFamilies(selectedCategory, locationEvent.getCurrentLocation().getLat(), locationEvent.getCurrentLocation().getLng());
    }

    @Override
    public void onFamilySearchSelected(FamilySearchResponse.DataEntity family) {
        Intent intent = new Intent(getContext(), FamilyDetailsActivity.class);
        intent.putExtra("id", family.getId() + "");
        intent.putExtra("isDelivery", family.getIsdelivery());
        intent.putExtra("status", family.getFamilystatus_nameen());
        if (family.getIsdelivery().equals("Y")) {
            intent.putExtra("price", "" + family.getDeliveryprice());
        } else {
            intent.putExtra("price", "" + family.getDeliveryprice());
        }
        startActivity(intent);
    }
}