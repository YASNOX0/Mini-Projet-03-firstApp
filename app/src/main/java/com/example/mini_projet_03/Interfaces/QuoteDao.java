package com.example.mini_projet_03.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mini_projet_03.Models.Quote;

import java.util.List;

@Dao
public interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuote(Quote quote);

    @Query("select * from quote_table")
    List<Quote> getQuotes();

    @Delete
    void delete(Quote quote);

    @Update
    void update(Quote quote);

    @Query("SELECT * FROM quote_table WHERE quote = :quote AND author = :author")
    List<Quote> getQuotesByTextAndAuthor(String quote, String author);

}
