package com.carteresto.igr230.carteresto.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;

import java.util.List;

/**
 * Created by zhufa on 19/03/2018.
 */
@Dao
public interface ProductDao {
    @Query("SELECT id, name, price, image FROM products WHERE type = 'apero'")
    LiveData<List<SimpleProduct>> getAperoList();

    @Query("SELECT id, name, price, image FROM products WHERE type = 'entree'")
    LiveData<List<SimpleProduct>> getEntreeList();

    @Query("SELECT id, name, price, image FROM products WHERE type = 'plat'")
    LiveData<List<SimpleProduct>> getPlatList();


    @Query("SELECT id, name, price, image FROM products WHERE type = 'dessert'")
    LiveData<List<SimpleProduct>> getDessertList();


    @Query("SELECT id, name, price, image FROM products WHERE type = 'vin'")
    LiveData<List<SimpleProduct>> getVinList();


    @Query("SELECT id, name, price, image FROM products WHERE type=:type")
    LiveData<List<SimpleProduct>> getVinList(@Product.Types String type);

    @Query("SELECT * FROM products WHERE id = :id")
    Product getProductById(int id);

    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT COUNT(*) FROM products")
    int getSize();

    @Insert
    void insertAll(List<Product> products);

    @Update
    int updateProduct(Product products);


}
