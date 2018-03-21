package com.carteresto.igr230.carteresto;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhufa on 21/03/2018.
 */

public class MyApplication extends Application {
    String cmdNumber = null;

    public void setCmdNumber(String id){
        SharedPreferences settings = getSharedPreferences("cmd", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name",id);
        editor.commit();
        cmdNumber = id;

    }

    public String getCmdNumber(){
        SharedPreferences settings = getSharedPreferences("cmd", 0);

        cmdNumber = settings.getString("cmdID",null);

        if(cmdNumber != null) return cmdNumber;
        cmdNumber = UUID.randomUUID().toString();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name",cmdNumber);
        editor.commit();

        return cmdNumber;
    }

    public void resetCmd(){
        cmdNumber = null;
    }

}
