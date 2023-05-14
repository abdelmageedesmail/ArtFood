package com.art4musilm.artfoodCustomer.di;
import android.app.Application;
import androidx.room.Room;
import com.art4musilm.artfoodCustomer.data.db.CartDao;
import com.art4musilm.artfoodCustomer.data.db.CartDatabase;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static CartDatabase provideDB(Application application){
        return Room.databaseBuilder(application, CartDatabase.class, "fav_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static CartDao provideDao(CartDatabase cartDatabase){
        return cartDatabase.cartDao();
    }

}
