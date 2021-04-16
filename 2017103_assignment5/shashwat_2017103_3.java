package com.example.assignment5;


import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;

@Entity (tableName = "light")
public class light {
    @PrimaryKey
    public long ts;


//    @ColumnInfo(name = "value")
//    public Float val ;
@ColumnInfo(name = "location")
public String location;
@ColumnInfo(name = "values")
public ArrayList<Integer> val;

}