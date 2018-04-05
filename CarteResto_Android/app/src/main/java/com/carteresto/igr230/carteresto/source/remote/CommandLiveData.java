package com.carteresto.igr230.carteresto.source.remote;

import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.Model.MenuDishesModel;
import com.carteresto.igr230.carteresto.Model.Product;
import com.carteresto.igr230.carteresto.Model.SimpleMenu;
import com.carteresto.igr230.carteresto.Model.SimpleProduct;
import com.carteresto.igr230.carteresto.source.local.ProductDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class CommandLiveData extends FirebaseLiveData<Command> {
    private static final String TAG = "CommandLiveData";
    static CommandLiveData INSTANCE = null;
    private static DatabaseReference COMMAND_INFO =
            FirebaseDatabase.getInstance().getReference("Command Info");


    private CommandLiveData(Query ref) {
        super(ref, Command.class);
    }

    static public CommandLiveData getInstance(String cmdID) {
        if (INSTANCE == null || !INSTANCE.query.getRef().getKey().equals(cmdID)) {
            Query ref = COMMAND_INFO.child(cmdID);
            INSTANCE = new CommandLiveData(ref);
            Log.d(TAG, "getInstance: Get Command LiveData cmdID:" + cmdID);
        }
        return INSTANCE;
    }

    public void updateProduct(SimpleProduct simpleProduct) {
        Command command = getValue();
        if (command != null) {
            command.updateProduct(simpleProduct);
            postValue(command);
        }
    }


    public void updateProducts(ProductDao dao) {
        query.getRef().child("productList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GenericTypeIndicator<Map<String, SimpleProduct>> type
                                = new GenericTypeIndicator<Map<String, SimpleProduct>>() {
                        };
                        Map<String, SimpleProduct> list = dataSnapshot.getValue(type);
                        if (list == null) return;
                        for (Map.Entry<String, SimpleProduct> entry : list.entrySet()) {
                            dao.insertCommand(new CommandModel(entry.getValue()));
                        }
                    }
                }).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addMenus(SimpleMenu simpleMenu){
        Command command = getValue();
        if (command != null) {
            command.updateMenu(simpleMenu);
            postValue(command);
        }
    }



    public void updateMenuDataBase(ProductDao dao) {
        query.getRef().child("menuList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GenericTypeIndicator<Map<String, SimpleMenu>> type
                                = new GenericTypeIndicator<Map<String, SimpleMenu>>() {
                        };
                        Map<String, SimpleMenu> list = dataSnapshot.getValue(type);
                        if (list == null) return;
                        for (Map.Entry<String, SimpleMenu> entry : list.entrySet()) {
                            Log.d(TAG, "run: Loading SimpleMenu:" + entry.getValue());
                            dao.insertCommand(new CommandModel(entry.getValue()));
                            dao.insertMenuDishes(MenuDishesModel.getListByMenu(entry.getValue()));
                        }
                    }
                }).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}

