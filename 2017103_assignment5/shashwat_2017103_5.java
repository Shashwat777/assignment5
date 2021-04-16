package com.example.assignment5;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "record")
public class record {
    @PrimaryKey
    public Integer uid;
    @ColumnInfo(name = "location")
    public String location;
    @ColumnInfo(name = "values")
    public ArrayList<Integer> val;
}