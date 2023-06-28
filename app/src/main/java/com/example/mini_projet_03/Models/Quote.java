package com.example.mini_projet_03.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "quote_table")
public class Quote {

    //region Attributes
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "quote")
    private String quote;
    @ColumnInfo(name = "author")
    private String author;
    @Ignore
    private boolean isQuoteSelected;
    //endregion

    //region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isQuoteSelected() {
        return isQuoteSelected;
    }

    public void setQuoteSelected(boolean quoteSelected) {
        isQuoteSelected = quoteSelected;
    }
    //endregion

    //region Constructor
    public Quote(String quote, String author) {
        this.quote = quote;
        this.author = author;
        this.isQuoteSelected = false;
    }
    //endregion

}
