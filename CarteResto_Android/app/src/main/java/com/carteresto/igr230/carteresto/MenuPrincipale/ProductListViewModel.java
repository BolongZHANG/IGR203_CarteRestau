package com.carteresto.igr230.carteresto.MenuPrincipale;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.source.ProductsRepository;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhufa on 21/03/2018.
 */

public class ProductListViewModel extends AndroidViewModel{
    static String TAG = ProductListViewModel.class.getSimpleName();
    private LiveData<List<Product>> productsLivedata;
    private MutableLiveData<Command> cmdLiveData;

    @NonNull
    private ProductsRepository repo;
    @NonNull
    private String cmdId;
    @NonNull
    private Map<String, LiveData<List<Product>>> productMaps = new HashMap<>();

    public ProductListViewModel(@NonNull Application application) {
        super(application);
        this.repo = ProductsRepository.getInstance(application);
        this.cmdId = repo.getCmdId();
    }

    public LiveData<List<Product>> getPlatList(){
        if(productsLivedata == null){
            productsLivedata = repo.getListByType(Product.PLAT);
        }
        Log.e("ViewModel", "ProductViewModel: Livedata：" + productsLivedata.getValue() +  " cmdid" + cmdId);

        return this.productsLivedata;
    }

    public LiveData<List<Product>> getProductsListByType(@Product.Types String type){
        LiveData<List<Product>> products;
        if(!productMaps.containsKey(type)){
            products = repo.getListByType(type);
            productMaps.put(type, products);
            Log.d(TAG, "getProductsListByType: get new List live data" + products );
            return products;
        }

        Log.d(TAG, "getProductsListByType: get old List live data" + productMaps.get(type));
        return productMaps.get(type);

    }


    public LiveData<Command> getCMD(){
        if(cmdLiveData == null){
            cmdLiveData = FirebaseDatabaseService.getCmd(repo.getCmdId());
        }

        return cmdLiveData;
    }

    public void addProductQuantity(String id) {
        Command command = getCMD().getValue();
        if(command == null){
            Log.e(TAG, "addProductQuantity: Can not get Command object from live data!");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Product product = repo.getProductDao().getFixedProductById(id);
                product.add();
                repo.getProductDao().updateProduct(product);
                Log.d(TAG, "run: Update product" + id);
            }
        }).start();

        command.addProductQuantity(id);
        cmdLiveData.postValue(command);
    }

    public void minusProductQuantity(String id) {
        Command command = cmdLiveData.getValue();
        if(command == null){
            Log.e(TAG, "addProductQuantity: Can not get Command object from live data!");
        }
        command.minusProductQuantity(id);
        cmdLiveData.postValue(command);
    }


    //    public LiveData<List<Product>> getNormalPlatList(){
//        return this.repo.getNormalListByType(Product.PLAT);
//    }

}
