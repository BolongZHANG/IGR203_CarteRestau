package com.carteresto.igr230.carteresto.source;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.R;
import com.carteresto.igr230.carteresto.source.local.LiveDataTestUtil;
import com.carteresto.igr230.carteresto.source.local.ProductDao;
import com.carteresto.igr230.carteresto.source.local.ProductDatabase;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by zhufa on 26/03/2018.
 */

@RunWith(AndroidJUnit4.class)
public class ProductsRepositoryTest {
    static final String TAG = ProductsRepository.class.getSimpleName();
    InstrumentationRegistry ir;

    private ProductDao mProductDao;
    private ProductDatabase mDb;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ProductDatabase.class).build();
        mProductDao = mDb.getProductDao();


        System.out.println("Begin to create data base");
        InputStream is = context.getResources().openRawResource(R.raw.products);
        Reader reader = new InputStreamReader(is);
        Gson gson = new Gson();
        List<Product> productList = Arrays.asList(gson.fromJson(reader, Product[].class));
//        mProductDao.insertAll(productList);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void initDataBase() throws Exception {

    }

    @Test
    public void getInstance() throws Exception {
    }

    @Test
    public void destroyInstance() throws Exception {
    }

    @Test
    public void updataProducts() throws Exception {
    }

    @Test
    public void getCommand() {
        String id = "032828b3-c4c8-4f52-a8a8-41538e361bb4";
        try {
            Command cmd = LiveDataTestUtil.getValue(FirebaseDatabaseService.getCmd(id));
            assertNotNull(cmd);
            Log.e(TAG, "getCommand: " + cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getListByType() throws Exception {
        String cmdId = "032828b3-c4c8-4f52-a8a8-41538e361bb4";
        LiveData<List<Product>> tmpLiveData = mProductDao.getListByType(Product.PLAT);
        LiveData<List<Product>> productsLiveData = Transformations.switchMap(tmpLiveData, inputList -> {
            LiveData<Command> cmdLiveData = FirebaseDatabaseService.getCmd(cmdId);

            LiveData<List<Product>> outputList = Transformations.map(cmdLiveData, inputCmd -> {
                Map<String, Integer> quantityMap = inputCmd.getProductList();
                for (Product product : inputList) {
                    if (quantityMap.containsKey(product.getId())) {
                        product.setQuantity(quantityMap.get(product.getId()));
                    }
                }
                return inputList;
            });

            return outputList;
        });


        List<Product> list = LiveDataTestUtil.getValue(productsLiveData);
        assertNotNull(list);
        Log.e(TAG, "getListByType: " + list);

        Command cmd = LiveDataTestUtil.getValue(FirebaseDatabaseService.getCmd(cmdId));
        cmd.addProductQuantity("67");
        FirebaseDatabaseService.setCmd(cmd);

        list = LiveDataTestUtil.getValue(productsLiveData);
        Log.e(TAG, "update getListByType: " + list);
        assertEquals(list.get(0).getQuantity(), 1);

    }

    @Test
    public void productsSize() throws Exception {
    }

}