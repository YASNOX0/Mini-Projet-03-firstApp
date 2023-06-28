package com.example.mini_projet_03.RoomDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mini_projet_03.Interfaces.QuoteDao;
import com.example.mini_projet_03.Models.Quote;

@Database(entities = {Quote.class} , version = 1 , exportSchema = true)
public abstract class QuotesDatabase extends RoomDatabase {

    public static QuotesDatabase instance;
    public abstract QuoteDao quoteDao();

    public static synchronized QuotesDatabase newInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context , QuotesDatabase.class , "quotes_database").build();
        }
        return instance;
    }

}
