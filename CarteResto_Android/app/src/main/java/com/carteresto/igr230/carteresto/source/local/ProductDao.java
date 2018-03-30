package com.carteresto.igr230.carteresto.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by zhufa on 19/03/2018.
 */
@Dao
public interface ProductDao {
    @Query("SELECT id, name, price, image,type FROM products WHERE type = 'apero'")
    LiveData<List<SimpleProduct>> getAperoList();

    @Query("SELECT id, name, price, image,type FROM products WHERE type = 'entree'")
    LiveData<List<SimpleProduct>> getEntreeList();

    @Query("SELECT id, name, price, image,type FROM products WHERE type = 'plat'")
    LiveData<List<SimpleProduct>> getPlatList();


    @Query("SELECT id, name, price, image,type FROM products WHERE type = 'dessert'")
    LiveData<List<SimpleProduct>> getDessertList();


    @Query("SELECT id, name, price, image, type FROM products WHERE type = 'vin'")
    LiveData<List<SimpleProduct>> getVinList();

    @Query("SELECT * FROM products WHERE type =:type")
    LiveData<List<Product>> getListByType(@Product.Types String type);

    @Query("SELECT * FROM products WHERE type =:type")
    List<Product> getListByTypeFix(@Product.Types String type);


    @Query("SELECT * FROM products WHERE type=:type")
    LiveData<List<SimpleProduct>> getVinList(@Product.Types String type);

    @Query("SELECT * FROM products WHERE id = :id")
    LiveData<Product> getProductById(String id);


    @Query("SELECT * FROM products WHERE id = :id")
    Product getFixedProductById(String id);

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProductsLiveData();

    @Query("SELECT * FROM products WHERE image = null")
    LiveData<List<Product>> getAllNoImageProducts();

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();


    @Query("SELECT products.*, commands.* FROM products LEFT JOIN commands On products.id = commands.productId")
    List<Product> getProductList();

    @Query("SELECT COUNT(*) FROM products")
    int getSize();

    @Insert(onConflict = REPLACE )
    long insertProduct(Product product);

    @Insert(onConflict = REPLACE)
    List<Long> insertProductList(List<Product> products);

    @Insert(onConflict = REPLACE)
    List<Long> insertCommand(List<Product> products);


    @Update
    int updateProduct(Product products);

    @Update
    int updateCommand(CommandModel products);

}
