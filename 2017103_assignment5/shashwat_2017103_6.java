package com.example.assignment5;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao

public interface userLight {
    @Insert
     void insert(light val) ;

    ;
    @Query("SELECT * FROM light")
    List<light> getAll();
}
