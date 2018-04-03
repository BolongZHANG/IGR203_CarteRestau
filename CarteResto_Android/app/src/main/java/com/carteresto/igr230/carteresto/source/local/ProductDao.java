package com.carteresto.igr230.carteresto.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.ProductModel;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by zhufa on 19/03/2018.
 */
@Dao
public interface ProductDao {
    @Query("SELECT products.*, commands.* FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.type =:type")
    LiveData<List<Product>> getListByType(@Product.Types String type);


    @Query("SELECT products.*, commands.* FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.id = :id")
    LiveData<Product> getProductById(String id);


    @Query("SELECT * FROM products WHERE id = :id")
    ProductModel getFixedProductById(String id);

    @Query("SELECT * FROM products WHERE image = null")
    LiveData<List<ProductModel>> getAllNoImageProducts();

    @Query("SELECT * FROM products")
    List<ProductModel> getAllProducts();

    @Query("SELECT products.*, commands.* FROM products LEFT JOIN commands On products.id = commands.productId")
    List<Product> getProductList();

    @Query("SELECT COUNT(*) FROM products")
    int getSize();

    @Query("SELECT * FROM commands")
    LiveData<List<CommandModel>> getCommandList();


    @Query("SELECT * FROM commands WHERE commands.productId = :id")
    CommandModel getCommand(String id);


    @Insert(onConflict =  OnConflictStrategy.REPLACE )
    long insertProduct(ProductModel product);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    List<Long> insertProductList(List<ProductModel> productModels);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    Long insertCommand(CommandModel product);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    List<Long> insertCommandList(List<CommandModel> product);


    @Update
    int updateProduct(ProductModel productsModel);

    @Update
    int updateCommand(CommandModel products);

}
