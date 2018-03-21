package com.carteresto.igr230.carteresto.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.Product;

/**
 * Created by zhufa on 19/03/2018.
 */

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    /**
     * The Room Database that contains the Task table.
     */

        private static ProductDatabase INSTANCE;
        public abstract ProductDao getProductDao();
        private static final Object sLock = new Object();


        public static ProductDatabase getInstance(Context context) {
            synchronized (sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductDatabase.class, "products.db")
                            .build();
                }
                return INSTANCE;
            }
        }
}
