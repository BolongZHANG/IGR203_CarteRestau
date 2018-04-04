package com.carteresto.igr230.carteresto.Panier;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.local.ProductDao;

import java.util.List;

/**
 * Created by zhufa on 28/03/2018.
 */

class PanierViewModel extends AndroidViewModel {
    @NonNull
    ProductsRepository mRepo;
    LiveData<List<CommandModel>> commandLiveData;
    LiveData<Product> productLiveData;
    private ProductDao dao;

    public PanierViewModel(@NonNull Application application) {
        super(application);
        mRepo = ProductsRepository.getInstance((MyApplication) application);
        mRepo.initDataBase();
        mRepo.setCmdId("049ee72a-42ba-4d19-adcc-644a4ed97a89");
        dao = mRepo.getProductDao();
    }

    public ProductDao getDao() {
        return dao;
    }


    @NonNull
    public LiveData<Product> getProduct() {
        if (productLiveData == null) {
            productLiveData = mRepo.getProductDao().getProductById("1");
        }

        return productLiveData;
    }


    @NonNull
    @Deprecated
    public String getCmdNumber() {
        return mRepo.getCmdId();
    }


}
