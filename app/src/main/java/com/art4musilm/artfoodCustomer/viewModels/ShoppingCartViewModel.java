package com.art4musilm.artfoodCustomer.viewModels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import com.art4musilm.artfoodCustomer.base.BaseViewModel;
import com.art4musilm.artfoodCustomer.models.CartModel;
import com.art4musilm.artfoodCustomer.repositories.ShoppingCartRepository;

import java.util.List;

public class ShoppingCartViewModel extends BaseViewModel {
    private ShoppingCartRepository repository;

    @ViewModelInject
    public ShoppingCartViewModel(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<CartModel>> observeCartItems(){
        return repository.getCartItems();
    }

    public LiveData<Double> observeTotalCartPrice(){
        return repository.getTotalPrice();
    }
    public void insertCartItem(CartModel product){
        repository.insert(product);
    }

    public void updateCartItem(CartModel product){
        repository.update(product);
    }

    public void deleteCartItem(CartModel product){
        repository.delete(product);
    }

    public void clearCart(){
        repository.deleteAll();
    }
}
