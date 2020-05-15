package com.assignment.maplehomeapp.model.database;

import android.app.admin.DeviceAdminInfo;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.assignment.maplehomeapp.interfaces.StimulationSessionsDao;
import com.assignment.maplehomeapp.interfaces.UserDao;
import com.assignment.maplehomeapp.model.DetailEntity;
import com.assignment.maplehomeapp.model.Prescriptions;
import com.assignment.maplehomeapp.model.RecordsEntity;
import com.assignment.maplehomeapp.model.StimulationHistory;
import com.assignment.maplehomeapp.model.UserDetails;
import com.assignment.maplehomeapp.utils.Constants;

@Database(version = 5, entities = {UserDetails.class, DetailEntity.class, StimulationHistory.class, Prescriptions.class})
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDetailsDao();

    public abstract StimulationSessionsDao stimulationSessionsDao();

    private static AppDataBase appDataBase;

    public static AppDataBase getInstance(Context context) {
        if (null == appDataBase) {
            appDataBase = buildDatabaseInstance(context);
        }
        return appDataBase;
    }

    private static AppDataBase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                AppDataBase.class,
                Constants.DB_NAME)
                .allowMainThreadQueries().build();
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
