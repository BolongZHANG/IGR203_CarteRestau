package com.carteresto.igr230.carteresto.source;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.source.local.ProductDao;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhufa on 20/03/2018.
 */

public class ProductsRepository{
    static MyApplication application;
    static final String TAG = ProductsRepository.class.getSimpleName();
    private static ProductsRepository INSTANCE = null;
    private final ProductDao productDao;


    private ProductsRepository( @NonNull  ProductDao productDao, @NonNull Application application) {
        this.productDao = checkNotNull(productDao);
        if(application instanceof MyApplication){
            application = (MyApplication) application;
            return;
        }

        Log.e(TAG, "ProductsRepository: The application type is not correct");

    }

    public static ProductsRepository getInstance(@NonNull  ProductDao productDao, @NonNull Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ProductsRepository(productDao, application);
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /** Update database**/
   public void updataProducts(){
           DatabaseReference PRODUCTS_INFO =
                   FirebaseDatabase.getInstance().getReference("Products Info");
           PRODUCTS_INFO.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists()){
                       GenericTypeIndicator<List<Product>> type =
                               new GenericTypeIndicator<List<Product>>(){};
                       final List<Product> products = dataSnapshot.getValue(type);
                       if(products != null){
                           new Runnable() {
                               @Override
                               public void run() {
                                   productDao.insertAll(products);
                               }
                           }.run();
                       }

                   }
               }
               @Override
               public void onCancelled(DatabaseError databaseError) {
                   Log.i(TAG, "onCancelled: Can not update the database!" );
               }
           });
       }


    /** Update database**/
    public void getListByType(String cmdID){
        String cmdId = application.getCmdNumber();
        LiveData<Command> cmdLiveData = FirebaseDatabaseService.getCmd(cmdId);



    }

    public void getCmd(String cmdInfo){

    }

}

