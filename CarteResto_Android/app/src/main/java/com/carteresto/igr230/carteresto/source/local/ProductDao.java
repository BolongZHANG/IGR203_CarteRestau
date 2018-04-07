package com.carteresto.igr230.carteresto.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.MenuDuCarte;
import com.carteresto.igr230.carteresto.Model.MenuDishesModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.ProductModel;
import com.carteresto.igr230.carteresto.Model.SimpleMenu;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;

import java.util.List;

/**
 * Created by zhufa on 19/03/2018.
 */
@Dao
public abstract class ProductDao {
    @Transaction
    @Query("SELECT products.*, commands.* FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.type =:type")
    public abstract LiveData<List<Product>> getListByType(@Product.Types String type);

    @Transaction
    @Query("SELECT products.*, commands.* FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.id = :id")
    public abstract LiveData<Product> getProductById(String id);

    @Transaction
    @Query("SELECT products.*, commands.* FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.type = 'menu' AND products.id =:id")
    public abstract LiveData<MenuDuCarte> getMenuById(String id);

    @Transaction
    @Query("SELECT products.id, products.name, products.type, products.price," +
            " menu_dishes.quantity, menu_dishes.comment" +
            " FROM products " +
            "LEFT JOIN menu_dishes On products.id = menu_dishes.productID " +
            "WHERE menu_dishes.menuID = :id")
    public abstract LiveData<List<SimpleProduct>> getMenuDishesById(String id);

    @Transaction
    @Query("SELECT * FROM products WHERE id = :id")
    public abstract ProductModel getFixedProductById(String id);

    @Transaction
    @Query("SELECT * FROM products")
    public abstract List<ProductModel> getAllProducts();
    @Transaction
    @Query("SELECT products.*, commands.* FROM products LEFT JOIN commands On products.id = commands.productId")
    public abstract List<Product> getProductList();
    @Transaction
    @Query("SELECT COUNT(*) FROM products")
    public abstract int getProductsSize();
    @Transaction
    @Query("SELECT COUNT(*) FROM menu_dishes")
    public abstract int getMenuSize();
    @Transaction
    @Query("SELECT * FROM commands")
    public abstract LiveData<List<CommandModel>> getCommandList();

    @Transaction
    @Query("SELECT * FROM commands WHERE commands.productId = :id")
    public abstract CommandModel getCommand(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertProduct(ProductModel product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertProductList(List<ProductModel> productModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insertCommand(CommandModel product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertCommandList(List<CommandModel> product);



    @Update
    public abstract int updateProduct(ProductModel productsModel);

    @Update
    public abstract int updateCommand(CommandModel products);

    @Update
    public abstract int updateMenuDish(MenuDishesModel menuDishesModel);

    @Update
    public abstract void updateMenuDishes(List<MenuDishesModel> menuDishesModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insertMenuDish(MenuDishesModel menuDishesModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long[] insertMenuDishes(List<MenuDishesModel> menuDishesModel);


    @Query("SELECT products.id, products.name, products.type, " +
            " products.price, commands.quantity, commands.comment FROM products " +
            "LEFT JOIN commands On products.id = commands.productId " +
            "WHERE products.id = :id")
    public abstract SimpleProduct getSimpleProductById(String id);


    @Query("DELETE FROM commands")
    public abstract void clearCommand();

    @Transaction
    public void updateMenu(SimpleMenu smenu, List<MenuDishesModel> relationList){
        updateMenuDishes(relationList);
        insertCommand(new CommandModel(smenu.getId()
                , smenu.getQuantity()
                , smenu.getComment()));
    }
}
