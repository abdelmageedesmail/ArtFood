package com.art4musilm.artfoodCustomer.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.art4musilm.artfoodCustomer.models.CartModel;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM CartModel")
    LiveData<List<CartModel>> getAll();
    @Query("SELECT SUM((price*count)+additionsTotal) FROM CartModel")
    LiveData<Double> getTotalPrice();
    @Update
    void update(CartModel cartModel);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartModel cartModel);
    @Delete
    void remove(CartModel cartModel);
    @Query("DELETE FROM CartModel")
    void removeAll();
}
