package com.example.mini_projet_03;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.Adapters.RVAdapter;
import com.example.mini_projet_03.Models.Quote;
import com.example.mini_projet_03.RoomDb.QuotesDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuotesListActivity extends AppCompatActivity {

    RecyclerView rv_QuotesListAct;
    RVAdapter rvAdapter;
    Button btn_addQuote;
    EditText et_AddQuote, et_AddAuthor;
    QuotesDatabase quotesDb;
    ArrayList<Quote> quotes;
    Button btn_quotesListActDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_list);

        quotesDb = QuotesDatabase.newInstance(this);

        rv_QuotesListAct = findViewById(R.id.rv_quotesListAct);
        btn_addQuote = findViewById(R.id.btn_manipulateQuote);
        et_AddQuote = findViewById(R.id.et_ManipulateQuote);
        et_AddAuthor = findViewById(R.id.et_ManipulateAuthor);
        btn_quotesListActDelete = findViewById(R.id.btn_quotesListActDelete);

        quotes = new ArrayList<>();

        QuotesDbManipulator quotesDbManipulator = new QuotesDbManipulator();

        rvAdapter = new RVAdapter(quotes, getSupportFragmentManager(), quotesDb, this, btn_quotesListActDelete);
        rv_QuotesListAct.setAdapter(rvAdapter);
        rv_QuotesListAct.setLayoutManager(new LinearLayoutManager(QuotesListActivity.this));

        btn_addQuote.setText("Add");
        btn_addQuote.setOnClickListener(view -> {
            quotesDbManipulator.addQuoteInRealTime(et_AddQuote.getText().toString(), et_AddAuthor.getText().toString());
            et_AddQuote.setText("");
            et_AddAuthor.setText("");
            et_AddQuote.requestFocus();
        });
    }

    public class QuotesDbManipulator {

        SharedPreferences sharedPreferences;

        private void insertQuotes() {
            sharedPreferences = getSharedPreferences("checkQuotesInserted", QuotesListActivity.MODE_PRIVATE);
            boolean isQuotesInserted = sharedPreferences.getBoolean("quotesInserted", false);

            if (isQuotesInserted) {
                return;
            }

            new Thread(() -> {
                String[] quotesAndAuthors;

                ArrayList<String> quotestxt = new ArrayList<>();
                ArrayList<String> authors = new ArrayList<>();

                try (InputStream inputStream = getResources().getAssets().open("Quotes.txt")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        quotesAndAuthors = line.split(" , ");
                        quotestxt.add(quotesAndAuthors[0].replaceAll("\"", "").trim());
                        authors.add(quotesAndAuthors[1].replaceAll("\"", "").trim());
                    }

                    for (int i = 0; i < quotestxt.size(); i++) {
                        quotesDb.quoteDao().insertQuote(new Quote(quotestxt.get(i), authors.get(i)));
                    }

                    // Update the concurrent list once all quotes are inserted
                    this.getQuotes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("quotesInserted", true);
                editor.apply();

            }).start();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void getQuotes() {
            new Thread(() -> {
                List<Quote> quoteList = quotesDb.quoteDao().getQuotes();
                quotes.clear();
                quotes.addAll(quoteList);

                runOnUiThread(() -> rvAdapter.notifyDataSetChanged());
            }).start();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void addQuoteInRealTime(String quote, String author) {
            new Thread(() -> {
                if (quotesDb.quoteDao().getQuotesByTextAndAuthor(quote, author).isEmpty()) {
                    if (!quote.isBlank() && !author.isBlank()) {
                        quotesDb.quoteDao().insertQuote(new Quote(quote, author));
                        this.getQuotes();
                    }
                }
            }).start();
        }

        public QuotesDbManipulator() {
            this.insertQuotes();
            this.getQuotes();
        }
    }
}
