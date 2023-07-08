package com.inheritex.demo;

import android.app.Application;
import android.content.Context;

import com.inheritex.demo.constant.ApiConstant;
import com.inheritex.demo.db.NewsDB;

/// [DemoApplication] This class is use for init object database
public class DemoApplication extends Application {
    private static NewsDB newsDB;

    @Override
    public void onCreate() {
        super.onCreate();
        newsDB = NewsDB.Companion.invoke(this, ApiConstant.DB_NAME);
    }

    //get singleTon object of database
    public static NewsDB getNewsDB(Context context, String dbName) {
        newsDB = NewsDB.Companion.invoke(context,dbName);
        return newsDB;
    }
}
