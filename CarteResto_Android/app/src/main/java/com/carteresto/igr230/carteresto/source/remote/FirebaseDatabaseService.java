package com.carteresto.igr230.carteresto.source.remote;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhufa on 20/03/2018.
 */

public class FirebaseDatabaseService {
    static final String TAG = FirebaseDatabaseService.class.getSimpleName();
    private static DatabaseReference PRODUCTS_INFO =
            FirebaseDatabase.getInstance().getReference("Products Info");

    private static DatabaseReference COMMAND_INFO =
            FirebaseDatabase.getInstance().getReference("Command Info");

    private static DatabaseReference TABLE_INFO =
            FirebaseDatabase.getInstance().getReference("Table Info");

    /**
     * get list of product
     **/
    static public LiveData<List<Product>> getListByType(@Product.Types String typeName) {
        Query ref = PRODUCTS_INFO.orderByChild("type").equalTo(typeName);
        GenericTypeIndicator<ArrayList<Product>> type = new GenericTypeIndicator<ArrayList<Product>>() {
        };
        FirebaseLiveData<List<Product>> aperoList = new FirebaseLiveData<>(ref, type);
        return aperoList;
    }


    static public LiveData<Product> getProductById(String id) {
        Query ref = PRODUCTS_INFO.orderByChild("id").equalTo(id);
        FirebaseLiveData<Product> productLiveData = new FirebaseLiveData<>(ref, Product.class);
        return productLiveData;
    }

    static public FirebaseLiveData<List<Product>> getAllProductsLiveData() {
        Query ref = PRODUCTS_INFO;
        GenericTypeIndicator<ArrayList<Product>> type = new GenericTypeIndicator<ArrayList<Product>>() {
        };
        FirebaseLiveData<List<Product>> productList = new FirebaseLiveData<>(ref, type);
        return productList;
    }

    @NonNull
    static public CommandLiveData getCmd(String cmdID) {
        return CommandLiveData.getInstance(cmdID);
    }


//
//    static public LiveData<Command> getTestCmd(String cmd){
//        Query ref = COMMAND_INFO.child(cmd);
//        MutableLiveData<Command> cmdLiveData = new MutableLiveData<>();
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    cmdLiveData.postValue(dataSnapshot.getValue(Command.class));
//                }else{
//                    Log.e("This is test", "onDataChange: cmd not exist:" + cmd);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        return cmdLiveData;
//    }

    static public void setCmd(@NonNull Command command) {
        Log.d(TAG, "setCmd: cmdID" + command.getId() + " table:" + command.getTableNb());
        COMMAND_INFO.child(command.getId()).setValue(command);
        TABLE_INFO.child(String.valueOf(command.getTableNb())).setValue(command.getId());
    }
//
//    static public FirebaseLiveData<Command> createCmd(String cmdID){
//        Command command = new Command(cmdID);
//        return
//    }


}
