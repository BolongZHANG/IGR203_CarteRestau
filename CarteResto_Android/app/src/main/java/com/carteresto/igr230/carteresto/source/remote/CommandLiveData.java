package com.carteresto.igr230.carteresto.source.remote;

import android.util.Log;

import com.carteresto.igr230.carteresto.Model.Command;
import com.carteresto.igr230.carteresto.Model.CommandModel;
import com.carteresto.igr230.carteresto.source.local.ProductDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class CommandLiveData extends FirebaseLiveData<Command>{
    private static final String TAG = "CommandLiveData";
    static CommandLiveData INSTANCE = null;
    private static DatabaseReference COMMAND_INFO =
            FirebaseDatabase.getInstance().getReference("Command Info");


    static public CommandLiveData getInstance(String cmdID){
        if(INSTANCE == null || !INSTANCE.query.getRef().getKey().equals(cmdID)){
            Query ref = COMMAND_INFO.child(cmdID);
            INSTANCE = new CommandLiveData(ref);
        }
        return INSTANCE;
    }

    private CommandLiveData(Query ref) {
        super(ref, Command.class);
    }

    public void addProduct(String id){
        Command command = getValue();
        if(command != null){
            command.addProductQuantity(id);
            postValue(command);
        }
    }


    public void minusProduct(String id){
        Command command = getValue();
        if(command != null){
            command.minusProductQuantity(id);
            postValue(command);
        }
    }

    public void updataProduct(ProductDao dao){
        query.getRef().child("productList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GenericTypeIndicator<Map<String, Integer>> type
                                = new GenericTypeIndicator<Map<String, Integer>>(){};
                        Map<String, Integer> list = dataSnapshot.getValue(type);
                        if (list == null) return;
                        for (Map.Entry<String, Integer> entry : list.entrySet()) {
                            dao.insertCommand(new CommandModel(entry.getKey().substring(3), entry.getValue()));
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

