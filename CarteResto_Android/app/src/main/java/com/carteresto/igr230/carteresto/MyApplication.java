package com.carteresto.igr230.carteresto;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhufa on 21/03/2018.
 */

public class MyApplication extends Application {
    String cmdNumber = null;
    ProductsRepository mRepo;


    public void resetCmd(){
        cmdNumber = null;
    }

}
