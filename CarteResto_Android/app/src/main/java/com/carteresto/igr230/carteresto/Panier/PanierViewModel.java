package com.carteresto.igr230.carteresto.Panier;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.source.ProductsRepository;

/**
 * Created by zhufa on 28/03/2018.
 */

class PanierViewModel extends AndroidViewModel{
    @NonNull
    ProductsRepository mRepo;
    MutableLiveData<Command> commandMutableLiveData;

    public PanierViewModel(@NonNull Application application) {
        super(application);
        mRepo = ProductsRepository.getInstance((MyApplication)application);
        mRepo.initDataBase();
        //TODO Test
        mRepo.setCmdId("049ee72a-42ba-4d19-adcc-644a4ed97a89");
    }

    @NonNull
    public MutableLiveData<Command> getCmd(){
        if(commandMutableLiveData == null){
            commandMutableLiveData = mRepo.getCommand();
        }

        return commandMutableLiveData;
    }

    @NonNull
    @Deprecated
    public String getCmdNumber(){
        return mRepo.getCmdId();
    }

}
