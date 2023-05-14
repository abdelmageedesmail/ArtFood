package com.art4musilm.artfoodCustomer.repositories;

import androidx.lifecycle.LiveData;

import com.art4musilm.artfoodCustomer.data.db.CartDao;
import com.art4musilm.artfoodCustomer.models.CartModel;

import java.util.List;

import javax.inject.Inject;

public class ShoppingCartRepository {
    private CartDao cartDao;

    @Inject
    public ShoppingCartRepository(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public LiveData<List<CartModel>> getCartItems(){
        return cartDao.getAll();
    }

    public void insert(CartModel cartModel){
        cartDao.insert(cartModel);
    }

    public void delete(CartModel cartModel){
        cartDao.remove(cartModel);
    }

    public void update(CartModel cartModel){
        cartDao.update(cartModel);
    }

    public LiveData<Double> getTotalPrice(){
        return cartDao.getTotalPrice();
    }

    public void deleteAll(){
        cartDao.removeAll();
    }
}
