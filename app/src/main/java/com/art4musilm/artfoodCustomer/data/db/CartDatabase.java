package com.art4musilm.artfoodCustomer.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.art4musilm.artfoodCustomer.models.CartModel;

@Database(entities = CartModel.class, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}