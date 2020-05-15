package com.assignment.maplehomeapp.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.assignment.maplehomeapp.model.UserDetails;


import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserDetails")
    List<UserDetails> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(UserDetails users);

    @Delete
    void delete(UserDetails user);
}
