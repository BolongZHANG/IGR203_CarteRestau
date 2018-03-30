package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;

/**
 * Created by zhufa on 28/03/2018.
 */

class ProductViewModel extends AndroidViewModel {
    private static final String TAG = ProductViewModel.class.getSimpleName();
    private MutableLiveData<Command> mCommand;
    private LiveData<Product> mProduct;
    private ProductsRepository mRepo;
    private String mId;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepo = ProductsRepository.getInstance(application);
    }


    public MutableLiveData<Command> getCmd(){
        if(mCommand == null){
            mCommand = FirebaseDatabaseService.getCmd(mRepo.getCmdId());
        }

        return mCommand;
    }

    @Deprecated
    public String getCmdNumber(){
        return mRepo.getCmdId();
    }


    public LiveData<Product> getProductById(String id){
        if(mProduct == null || !mId.equals(id)){
            Log.d(TAG, "getProductById: Get new product, id:" + id);
            mId = id;
            mProduct = mRepo.getProductById(id);
        }
        return mProduct;
    }

    public LiveData<Product> getTestProduct(String id){
        if(mProduct == null || !mId.equals(id)){
            Log.d(TAG, "getProductById: Get new product, id:" + id);
            mId = id;
            mProduct = mRepo.getProductTestById(id);
        }
        return mProduct;
    }


}
