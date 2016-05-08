package com.sharesmile.share.core;

import android.database.sqlite.SQLiteDatabase;

import com.sharesmile.share.DaoMaster;
import com.sharesmile.share.DaoSession;
import com.sharesmile.share.MainApplication;
import com.sharesmile.share.WorkoutDao;
import com.sharesmile.share.gps.models.WorkoutData;

/**
 * Created by Shine on 07/05/16.
 */
public class DbWrapper {

    private final static String DB_NAME = "impact-db";

    MainApplication application;

    private SQLiteDatabase db;
    private DaoMaster.DevOpenHelper mDbHelper;
    private DaoSession mSaoSession;
    private DaoSession mDaoSession;
    private WorkoutDao mWorkoutDao;

    public DbWrapper(MainApplication app) {
        application = app;
        generateMembers();
    }

    private void generateMembers() {

        mDbHelper = new DaoMaster.DevOpenHelper(application, DB_NAME, null);

        if (db != null) {
            db.close();
        }

        db = mDbHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        mWorkoutDao = mDaoSession.getWorkoutDao();

    }

    public DaoMaster.DevOpenHelper getDbHelper() {
        return mDbHelper;
    }

    public DaoSession getSaoSession() {
        return mSaoSession;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public WorkoutDao getWorkoutDao() {
        return mWorkoutDao;
    }
}