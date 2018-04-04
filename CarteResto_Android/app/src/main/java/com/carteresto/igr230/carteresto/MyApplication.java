package com.carteresto.igr230.carteresto;

import android.app.Application;

import com.carteresto.igr230.carteresto.source.ProductsRepository;

/**
 * Created by zhufa on 21/03/2018.
 */

public class MyApplication extends Application {
    String cmdNumber = null;
    ProductsRepository mRepo;


    public void resetCmd() {
        cmdNumber = null;
    }

}
