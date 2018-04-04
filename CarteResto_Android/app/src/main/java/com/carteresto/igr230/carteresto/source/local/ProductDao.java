package com.carteresto.igr230.carteresto.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.MenuDuCarte;
import com.carteresto.igr230.carteresto.Model.MenuDishesModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.ProductModel;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;

import java.util.List;

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


    @Query("SELECT products.*, commands.* FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.type = 'menu' AND products.id =:id")
    LiveData<MenuDuCarte> getMenuById(String id);

    @Query("SELECT products.id, products.name, products.type, products.price," +
            " menu_dishes.quantity, menu_dishes.comment" +
            " FROM products " +
            "LEFT JOIN menu_dishes On products.id = menu_dishes.productID " +
            "WHERE menu_dishes.menuID = :id")
    LiveData<List<SimpleProduct>> getMenuDishesById(String id);

    @Query("SELECT * FROM products WHERE id = :id")
    ProductModel getFixedProductById(String id);


    @Query("SELECT * FROM products")
    List<ProductModel> getAllProducts();

    @Query("SELECT products.*, commands.* FROM products LEFT JOIN commands On products.id = commands.productId")
    List<Product> getProductList();

    @Query("SELECT COUNT(*) FROM products")
    int getProductsSize();

    @Query("SELECT COUNT(*) FROM menu_dishes")
    int getMenuSize();

    @Query("SELECT * FROM commands")
    LiveData<List<CommandModel>> getCommandList();


    @Query("SELECT * FROM commands WHERE commands.productId = :id")
    CommandModel getCommand(String id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertProduct(ProductModel product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertProductList(List<ProductModel> productModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCommand(CommandModel product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertCommandList(List<CommandModel> product);


    @Update
    int updateProduct(ProductModel productsModel);

    @Update
    int updateCommand(CommandModel products);

    @Update
    int updateMenuDish(MenuDishesModel menuDishesModel);

    @Insert
    Long insertMenuDish(MenuDishesModel menuDishesModel);

    @Insert
    Long[] insertMenuDishes(List<MenuDishesModel> menuDishesModel);


    @Query("SELECT products.id, products.name, products.type, " +
            " products.price, commands.quantity, commands.comment FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.id = :id")
    SimpleProduct getSimpleProductById(String id);
}
