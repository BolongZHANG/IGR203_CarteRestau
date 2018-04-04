package com.carteresto.igr230.carteresto.MenuDetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.MenuDuCarte;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.remote.CommandLiveData;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {
    static String TAG = MenuViewModel.class.getSimpleName();
    private CommandLiveData cmdLiveData;
    private LiveData<MenuDuCarte> menuLiveData;
    private LiveData<List<SimpleProduct>> menuDishesLivedata;
    private String menuID = "-1";
    @NonNull
    private ProductsRepository mRepo;
    @NonNull
    private String cmdId;
    public MenuViewModel(@NonNull Application application) {
        super(application);
        this.mRepo = ProductsRepository.getInstance(application);
        this.cmdId = mRepo.getCmdId();
        this.cmdLiveData = mRepo.getCommand();
    }

    @NonNull
    static public CommandLiveData getCmd(String cmdID) {
        return CommandLiveData.getInstance(cmdID);
    }

    public LiveData<List<SimpleProduct>> getMenuDishes(@NonNull String id) {
        if (!menuID.equals(id) || menuDishesLivedata == null) {
            Log.d(TAG, "getMenuDishes: Get new Menu dishlist :" + id);
            this.menuID = id;
            this.menuDishesLivedata = mRepo.getProductDao().getMenuDishesById(id);
        }

        return menuDishesLivedata;
    }


    public LiveData<MenuDuCarte> getMenu(@NonNull String id) {
        if (!menuID.equals(id) || menuLiveData == null) {
            Log.d(TAG, "getMenu: Get new Menu id:" + id);
            menuID = id;
            menuLiveData = mRepo.getProductDao().getMenuById(id);
        }

        return menuLiveData;
    }


}
