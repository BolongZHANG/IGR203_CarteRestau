package com.carteresto.igr230.carteresto.source.remote;

/**
 * Created by zhufa on 20/03/2018.
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/** This class is used to store all the information from firebase realtime database.
 * If the infomation in the firebase realtime database is changed. The value stored in
 * this live data will also changed.
 * Created by zhufa on 09/03/2018.
 *
 */

public class FirebaseLiveData<T> extends LiveData<T> {
    private static final String LOG_TAG = "FirebaseQueryLiveData";
    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();
    Class<T> kind;
    boolean isGeneType = false;
    GenericTypeIndicator<T> geneKind;
    private ChildEventListener childEventListener;

    public void setChildEventListener(ChildEventListener listener){
        childEventListener = listener;
    }

    public FirebaseLiveData(Query query, Class<T> type) {
        this.query = query;
        kind = type;
        isGeneType = false;
    }

    public FirebaseLiveData(Query query, GenericTypeIndicator type) {
        this.query = query;
        geneKind = type;
        isGeneType = true;
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(listener);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
        query.getRef().setValue(value);
    }


    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                if(isGeneType){
                    postValue(dataSnapshot.getValue(geneKind));
                }else{
                    postValue(dataSnapshot.getValue(geneKind));
                }
                Log.i(LOG_TAG, "onDataChange: update " + getValue());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}