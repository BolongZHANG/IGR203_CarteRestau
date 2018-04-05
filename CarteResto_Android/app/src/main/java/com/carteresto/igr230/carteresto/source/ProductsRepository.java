package com.carteresto.igr230.carteresto.source;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.MenuDishesModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.ProductModel;
import com.carteresto.igr230.carteresto.Model.SimpleMenu;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.MyApplication;
import com.carteresto.igr230.carteresto.source.local.ProductDao;
import com.carteresto.igr230.carteresto.source.local.ProductDatabase;
import com.carteresto.igr230.carteresto.source.remote.CommandLiveData;
import com.carteresto.igr230.carteresto.source.remote.FirebaseDatabaseService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

;

/**
 * Created by zhufa on 20/03/2018.
 */

public class ProductsRepository {
    static final String TAG = ProductsRepository.class.getSimpleName();
    static MyApplication mApplication;
    private static ProductsRepository INSTANCE = null;
    ProductDao productDao;
    private LiveData<List<Product>> tempLiveData;
    private Executor diskIO;
    private CommandLiveData commandLiveData;

    private ProductsRepository(@NonNull Application mApplication) {

        ProductDatabase mDb = Room.inMemoryDatabaseBuilder(mApplication, ProductDatabase.class).build();
        this.productDao = checkNotNull(mDb.getProductDao());
        if (mApplication instanceof MyApplication) {
            this.mApplication = (MyApplication) mApplication;
        }
        diskIO = Executors.newFixedThreadPool(3);
//        Log.e(TAG, "ProductsRepository: The mApplication type is not correct");

    }

    public static ProductsRepository getInstance(@NonNull Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ProductsRepository(application);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public ProductDao getProductDao() {
        return productDao;
    }


    public void initDataBase() {
        initMenuDishes();
        initProducts();
    }


    public void initMenuDishes() {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://carterestoandroid.appspot.com");
        StorageReference storageRef = storage.getReference("MenuDishes.json");
        final File file = new File(mApplication.getFilesDir(), "MenuDishes.json");
        storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "onSuccess: File is successful created");

                try (FileReader fr = new FileReader(file)) {
                    Gson gson = new Gson();
                    List<MenuDishesModel> mData = Arrays.asList(gson.fromJson(fr, MenuDishesModel[].class));
                    Log.d(TAG, "onSuccess: Init database with list:" + mData);
                    new Thread(() -> {
                        Log.d(TAG, "onThread: Init database size:" + productDao.getMenuSize());
                        productDao.insertMenuDishes(mData);
                        Log.d(TAG, "onThread: Init database size:" + productDao.getMenuSize());
                    }).start();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "Can not download the products.json.");
            }
        });


    }


    public void initProducts() {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://carterestoandroid.appspot.com");
        StorageReference storageRef = storage.getReference("products.json");
        final File file = new File(mApplication.getFilesDir(), "products.json");
        storageRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "onSuccess: File is successful created");

                try (FileReader fr = new FileReader(file)) {
                    Gson gson = new Gson();
                    List<ProductModel> proList = Arrays.asList(gson.fromJson(fr, ProductModel[].class));
                    Log.d(TAG, "onSuccess: Init database with list size:" + proList.size());
                    new Thread(() -> {
                        Log.d(TAG, "onThread: Init database size:" + productDao.getProductsSize());
                        productDao.insertProductList(proList);
                        Log.d(TAG, "onThread: Init database size:" + productDao.getProductsSize());
                    }).start();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "Can not download the products.json.");
            }
        });

    }


    /**
     * Update database
     **/
    public void updataProducts() {
        DatabaseReference PRODUCTS_INFO =
                FirebaseDatabase.getInstance().getReference("Products Info");
        PRODUCTS_INFO.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<List<ProductModel>> type =
                            new GenericTypeIndicator<List<ProductModel>>() {
                            };
                    final List<ProductModel> products = dataSnapshot.getValue(type);
                    if (products != null) {
                        new Runnable() {
                            @Override
                            public void run() {
                                productDao.insertProductList(products);
                            }
                        }.run();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: Can not update the database!");
            }
        });
    }


//    /** Update database**/
//
//    public LiveData<List<Product>> getListByType(@Product.Types String type){
//        final String cmdId = getCmdId();
//        if(tempLiveData ==null) tempLiveData = productDao.getListByType(type);
//        Log.d(TAG, "getListByType: Type:" + type + " Data From Dao" + tempLiveData);
//        LiveData<List<Product>> productsLiveData = Transformations.switchMap(tempLiveData, inputList -> {
//            Log.d(TAG, "getListByType: Type:" + type + " Data From Dao" + tempLiveData + " input list:" + inputList);
//            /** 当获得的command序号不正确时， cmdLiveData无法正常更新，导致productsLiveData没有正常更新**/
//            LiveData<Command> cmdLiveData = FirebaseDatabaseService.getCmd(cmdId);
//
//            LiveData<List<Product>> outputList = Transformations.map(cmdLiveData, inputCmd -> {
//                List<Product> productsList = inputList;
//                Log.d(TAG, "getListByType: Type:" + type
//                        + "\nCommand from firebase:" + inputCmd
//                        + "\nLivedata：" + tempLiveData
//                        + "\ntempLiveData: " + tempLiveData.getValue().size()
//                        + "\ninputList:" + Integer.toHexString(inputList.hashCode())
//                        + "\ninputList:" + inputList.size());
//
//             //   if (productsList.size() == 0) productsList = productDao.getListByTypeFix(type);
//                for (Product product : productsList) {
//                    product.setQuantity(inputCmd.getProductQuantity(product.getId()));
//                }
//                Log.d(TAG, "getListByType: Type: inputList：" + productsList);
//                return productsList;
//            });
//            Log.d(TAG, "getListByType: Type: outputList：" + inputList);
//            return outputList;
//        });
//
//        return productsLiveData;
//    }

    /**
     * Update database
     **/

    public LiveData<List<Product>> getListByType(@Product.Types String type) {
        final String cmdId = getCmdId();
        Log.d(TAG, "getListByType: Type:" + type + " Data From Dao" + tempLiveData);
        return productDao.getListByType(type);
    }

    /**
     * Update database
     **/
    public LiveData<List<Product>> getNormalListByType(@Product.Types String type) {
        LiveData<List<Product>> tmpLiveData = productDao.getListByType(type);
        return tmpLiveData;
    }

    public CommandLiveData getComand(String cmdID) {
        return CommandLiveData.getInstance(cmdID);
    }

    public int productsSize() {
        return productDao.getProductsSize();
    }


    public LiveData<Product> getProductById(final String id) {
        final String cmdId = getCmdId();
        LiveData<Product> productLiveData = productDao.getProductById(id);
        Log.d(TAG, "getProductById: get product by sql-1:" + productLiveData.getValue());
        productLiveData = Transformations.switchMap(productLiveData, input -> {
            Log.d(TAG, "getProductById: get product by sql-2:" + input);
            /** 当获得的command序号不正确时， cmdLiveData无法正常更新，导致productsLiveData没有正常更新**/
            LiveData<Command> cmdLiveData = FirebaseDatabaseService.getCmd(cmdId);

            LiveData<Product> output = Transformations.map(cmdLiveData, inputCmd -> {
                Log.d(TAG, "getProductById: get cmd livedata, id = " + cmdId + " Command:" + cmdLiveData.getValue());
                Log.d(TAG, "getProductById: get product by sql-3:" + input + " product ID:" + id);
                input.setQuantity(inputCmd.getProductQuantity(input.getId()));
                return input;
            });

            return output;
        });

        return productLiveData;
    }

    public String createCmd(String table) {
        SharedPreferences settings = mApplication.getSharedPreferences("cmd", 0);
        String cmdNumber = UUID.randomUUID().toString();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("cmdID", cmdNumber);
        editor.commit();
        Log.d(TAG, "createCmd: table:" + table + " cmdID:" + cmdNumber);
        Command command = new Command(cmdNumber, table);
        FirebaseDatabaseService.setCmd(command);
        return cmdNumber;
    }

    @NonNull
    public CommandLiveData getCommand() {
        if (commandLiveData == null) {
            SharedPreferences settings = mApplication.getSharedPreferences("cmd", 0);
            String cmdNumber = settings.getString("cmdID", null);
            if (cmdNumber == null)
                throw new IllegalStateException("You have to create Command firstly");
            commandLiveData = getComand(getCmdId());
        }

        return commandLiveData;
    }

    public String getCmdId() {
        SharedPreferences settings = mApplication.getSharedPreferences("cmd", 0);
        String cmdNumber = settings.getString("cmdID", null);
        if (cmdNumber == null)
            throw new IllegalStateException("You have to create Command firstly");
        return cmdNumber;
    }


    public void setCmdId(String cmdId) {
        SharedPreferences settings = mApplication.getSharedPreferences("cmd", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("cmdID", cmdId);
        editor.commit();
        getCommand().updateProducts(productDao);
        getCommand().updateMenuDataBase(productDao);
        Log.d(TAG, "setCmdId: Set cmd id：" + cmdId);
    }

    public LiveData<Product> getProductTestById(String id) {

        return productDao.getProductById(id);
    }


    public void addProductQuantity(String id) {
        Runnable add = new Runnable() {

            @Override
            public void run() {

                SimpleProduct simpleProduct = productDao.getSimpleProductById(id);
                CommandModel commandModel = productDao.getCommand(id);
                simpleProduct.add();
                if (commandModel == null) commandModel = new CommandModel(id, 0);

                commandModel.setQuantity(simpleProduct.getQuantity());
                productDao.insertCommand(commandModel);
                getCommand().updateProduct(simpleProduct);

            }
        };

        this.diskIO.execute(add);
    }

    public void minusProductQuantity(String id) {
        Runnable minus = new Runnable() {

            @Override
            public void run() {
                // Update data in firebase
                SimpleProduct simpleProduct = productDao.getSimpleProductById(id);
                CommandModel commandModel = productDao.getCommand(id);
                simpleProduct.minus();
                if (commandModel == null) commandModel = new CommandModel(id, 0);
                commandModel.setQuantity(simpleProduct.getQuantity());
                productDao.insertCommand(commandModel);
                getCommand().updateProduct(simpleProduct);
            }
        };

        this.diskIO.execute(minus);

    }

    public void updataMenu(SimpleMenu smenu, List<MenuDishesModel> relationList) {
        Runnable updataMenu = new Runnable() {

            @Override
            public void run() {
                getCommand().addMenus(smenu);
                getProductDao().updateMenuDishes(relationList);
                getProductDao().insertCommand(new CommandModel(smenu.getId()
                        , smenu.getQuantity()
                        , smenu.getComment()));
            }
        };

        this.diskIO.execute(updataMenu);
    }
}
