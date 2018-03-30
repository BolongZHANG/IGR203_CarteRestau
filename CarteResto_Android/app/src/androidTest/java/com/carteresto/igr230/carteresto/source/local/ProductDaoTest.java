package com.carteresto.igr230.carteresto.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.ProductModel;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.R;
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * Created by zhufa on 19/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {

    static final String TAG = ProductDaoTest.class.getSimpleName();
    private ProductDao mProductDao;
    private ProductDatabase mDb;

    List<ProductModel> productList;
    @Before
    public void createDB() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ProductDatabase.class).build();
        mProductDao = mDb.getProductDao();


        System.out.println("Begin to create data base");
        InputStream is = context.getResources().openRawResource(R.raw.products);
        Reader reader = new InputStreamReader(is);
        Gson gson = new Gson();
        productList = Arrays.asList(gson.fromJson(reader, ProductModel[].class));
        Log.i(TAG, "createDB: size of data" + productList.size());
    }

    @After
    public void closeDB() throws Exception {
        mDb.close();
    }


    @Test
    public void getProductById() throws Exception {
        mProductDao.insertProductList(productList);
        LiveData<Product> productLiveData = mProductDao.getProductById("27");
        LiveData<List<CommandModel>> commandLivedata = mProductDao.getCommandList();

        List<CommandModel> commandList = LiveDataTestUtil.getValue(commandLivedata);
        Product product = LiveDataTestUtil.getValue(productLiveData);

        Log.e(TAG, "getProductById: Before:" + product );
        Log.e(TAG, "getProductById: command list:" + commandList);
        assertEquals(product.getId(), "27");
        assertEquals(product.getName(), "Gimlet pickles");
        assertEquals(product.getType(), "apero");


        mProductDao.insertCommand(new CommandModel("27", 13));
        Log.d(TAG, "getProductById: command list:" + commandList);

        product = LiveDataTestUtil.getValue(productLiveData);
        commandList = LiveDataTestUtil.getValue(commandLivedata);

        Log.e(TAG, "getProductById: After:" + product );
        Log.e(TAG, "getProductById: command list:" + commandList);


        commandLivedata = mProductDao.getCommandList();
        commandList = LiveDataTestUtil.getValue(commandLivedata);
        Log.e(TAG, "getProductById: new command list:" + commandList);


        assertEquals(product.getId(), "27");
        assertEquals(product.getName(), "Gimlet pickles");
        assertEquals(product.getType(), "apero");
        assertEquals(product.getQuantity(), 13);

    }

    @Test
    public void insertAll() throws Exception {
//        Log.d(TAG, "insertAll: size of list:" + productList.size());
//
//        mProductDao.insertProductList(productList);
//        List<Product> products =  LiveDataTestUtil.getValue(mDb.getProductDao().getAllProductsLiveData());
//
//        assertNotNull(products);
//        assertThat(products.size(), is(productList.size()));
    }


    @Test
    public void insertProduct() throws Exception {
    }


    @Test
    public void getListByType() throws Exception {
        mProductDao.insertProductList(productList);
        LiveData<List<Product>> productsLivedata = mProductDao.getListByType(Product.PLAT);
        List<Product> products = LiveDataTestUtil.getValue(productsLivedata);
        assertNotNull(products);
        for(Product product: products){
            Log.e(TAG, "getListByType: " + product);
            assertEquals(product.getType(), Product.PLAT);
        }

        String id = products.get(0).getId();
        mProductDao.insertCommand(new CommandModel(id, 20));
        products = LiveDataTestUtil.getValue(productsLivedata);
        for(Product product: products){
            Log.e(TAG, "getListByType: " + product);
            assertEquals(product.getType(), Product.PLAT);
            if(product.getId().equals(id)){
                assertEquals(product.getQuantity(), 20);
            }
        }


    }
    @Test
    public void updateProduct() throws Exception {

    }

    @Test
    public  void getProductList(){
        mProductDao.insertProductList(productList);
        mProductDao.insertCommand(new CommandModel("1",23));
        List<Product> list = mProductDao.getProductList();
        Log.e(TAG, "getProductList: " + list);

    }


    private void assertSimpleProduct(SimpleProduct product, String id, String name,
                                     double price, String img, int quantity ) {
        assertNotNull(product);
        assertThat(product.getId(), is(id));
        assertThat(product.getName(), is(name));
        assertThat(product.getQuantity(), is(quantity));
        assertEquals(product.getPrice(), price, 0.001);
        assertEquals(product.getImage(), img);
    }


    private void assertProduct(Product product, String id, String name,String type, String commentaire,
                                     double price, String img, int quantity ) {
        assertNotNull(product);
        assertThat(product.getId(), is(id));
        assertThat(product.getName(), is(name));
        assertEquals(product.getImage(), img);
        assertEquals(product.getPrice(), price, 0.001);
        assertThat(product.getQuantity(), is(quantity));
        assertThat(product.getType(), is(type));
        assertThat(product.getCommentaire(), is(commentaire));
    }




}