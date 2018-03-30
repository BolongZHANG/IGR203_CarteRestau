package com.carteresto.igr230.carteresto.source.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Product;
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

    List<Product> productList;
    @Before
    public void createDB() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, ProductDatabase.class).build();
        mProductDao = mDb.getProductDao();


        System.out.println("Begin to create data base");
        InputStream is = context.getResources().openRawResource(R.raw.products);
        Reader reader = new InputStreamReader(is);
        Gson gson = new Gson();
        productList = Arrays.asList(gson.fromJson(reader, Product[].class));

        Log.i(TAG, "createDB: size of data" + productList.size());
    }

    @After
    public void closeDB() throws Exception {
        mDb.close();
    }

    @Test
    public void getAperoList() throws Exception {
        mProductDao.insertProductList(productList);
        List<SimpleProduct> products = LiveDataTestUtil.getValue(mProductDao.getAperoList());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        SimpleProduct product = products.get(0);

        assertSimpleProduct(product, "1", "Samossas de crêpes jambon blanc-mascarpone",0, null,0);

    }

    @Test
    public void getEntreeList() throws Exception {
        mProductDao.insertProductList(productList);
        List<SimpleProduct> products = LiveDataTestUtil.getValue(mProductDao.getEntreeList());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        SimpleProduct product = products.get(0);
        assertSimpleProduct(product, "37", "Avocado toast",0, null,0);
    }

    @Test
    public void getPlatList() throws Exception {

        mProductDao.insertProductList(productList);
        List<SimpleProduct> products = LiveDataTestUtil.getValue(mProductDao.getPlatList());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        SimpleProduct product = products.get(0);
        assertSimpleProduct(product, "67", "Tourtes individuelles au boeuf et aux rognons",0, null,0);
    }

    @Test
    public void getDessertList() throws Exception {
        mProductDao.insertProductList(productList);
        List<SimpleProduct> products = LiveDataTestUtil.getValue(mProductDao.getDessertList());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        SimpleProduct product = products.get(0);
        assertSimpleProduct(product, "97", "Tarte à la mélasse",0, null,0);
    }

    @Test
    public void getVinList() throws Exception {
        mProductDao.insertAll(productList);
        List<SimpleProduct> products = LiveDataTestUtil.getValue(mProductDao.getVinList());
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void getProductById() throws Exception {
        mProductDao.insertProductList(productList);
        Product product = LiveDataTestUtil.getValue(mProductDao.getProductById("27"));
        assertEquals(product.getId(), "27");
        assertEquals(product.getName(), "Gimlet pickles");
        assertEquals(product.getType(), "apero");

    }

    @Test
    public void insertAll() throws Exception {
        Log.d(TAG, "insertAll: size of list:" + productList.size());

        mProductDao.insertProductList(productList);
        List<Product> products =  LiveDataTestUtil.getValue(mDb.getProductDao().getAllProductsLiveData());

        assertNotNull(products);
        assertThat(products.size(), is(productList.size()));
    }


    @Test
    public void insertProduct() throws Exception {
    }


    @Test
    public void getListByType() throws Exception {

        mProductDao.insertProductList(productList);
        List<Product> products = LiveDataTestUtil.getValue(mProductDao.getListByType(Product.PLAT));
        assertNotNull(products);
        for(Product product: products){
            Log.e(TAG, "getListByType: " + product);
        }

    }
    @Test
    public void updateProduct() throws Exception {

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