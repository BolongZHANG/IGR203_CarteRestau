package com.carteresto.igr230.carteresto.Login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.carteresto.igr230.carteresto.source.ProductsRepository;

class LoginViewModel extends AndroidViewModel {

    ProductsRepository mRepo;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepo = ProductsRepository.getInstance(getApplication());
    }


    public String createCmd(String table) {
        return mRepo.createCmd(table);
    }

    public void setCmdId(String id){
        mRepo.setCmdId(id);
    }
}
