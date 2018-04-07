package com.carteresto.igr230.carteresto.Panier;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.remote.CommandLiveData;

/**
 * Created by zhufa on 28/03/2018.
 */

class PanierViewModel extends AndroidViewModel {
    static final String TAG = PanierActivity.class.getSimpleName();
    @NonNull
    ProductsRepository mRepo;
    CommandLiveData commandLiveData;


    public PanierViewModel(@NonNull Application application) {
        super(application);
        mRepo = ProductsRepository.getInstance((MyApplication) application);
    }



    @NonNull
    public CommandLiveData getCommandLiveData() {
        if (commandLiveData == null) {
            commandLiveData = mRepo.getCommand();
        }
        Log.d(TAG, "getCommandLiveData: " + commandLiveData + " value:" + commandLiveData.getValue());
        return commandLiveData;
    }

    public void addProductQuantity(String id) {
        mRepo.addProductQuantity(id);
    }

    public void minusProductQuantity(String id) {
        mRepo.minusProductQuantity(id);
    }

    public void updateNote(SimpleProduct product) {
        mRepo.updateNote(product);
    }
}
