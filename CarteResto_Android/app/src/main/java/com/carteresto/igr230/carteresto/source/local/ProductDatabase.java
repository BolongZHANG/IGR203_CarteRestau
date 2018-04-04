package com.carteresto.igr230.carteresto.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.MenuDishesModel;
import com.carteresto.igr230.carteresto.Model.ProductModel;

/**
 * Created by zhufa on 19/03/2018.
 */

@Database(entities = {ProductModel.class, CommandModel.class, MenuDishesModel.class}, version = 1, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    private static final Object sLock = new Object();
    /**
     * The Room Database that contains the Task table.
     */

    private static ProductDatabase INSTANCE;

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

    public abstract ProductDao getProductDao();
}
