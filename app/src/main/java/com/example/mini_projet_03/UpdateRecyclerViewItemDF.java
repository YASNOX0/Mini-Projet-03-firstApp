package com.example.mini_projet_03;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.mini_projet_03.Adapters.RVAdapter;
import com.example.mini_projet_03.Models.Quote;
import com.example.mini_projet_03.RoomDb.QuotesDatabase;

public class UpdateRecyclerViewItemDF extends DialogFragment {

    private int quoteId;
    EditText et_updateQuote;
    EditText et_updateAuthor;
    Context context;
    RVAdapter rvAdapter;
    QuotesDatabase quotesDb;
    QuotesListActivity quotesListActivity;

    public UpdateRecyclerViewItemDF(Context context, int itemPosition, QuotesDatabase quotesDb, RVAdapter rvAdapter, QuotesListActivity quotesListActivity) {
        this.context = context;
        this.quoteId = itemPosition;
        this.rvAdapter = rvAdapter;
        this.quotesDb = quotesDb;
        this.quotesListActivity = quotesListActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return LayoutInflater.from(context).inflate(R.layout.manipulate_quote_author, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_updateQuote = view.findViewById(R.id.et_ManipulateQuote);
        et_updateAuthor = view.findViewById(R.id.et_ManipulateAuthor);
        Button btn_updateQuote = view.findViewById(R.id.btn_manipulateQuote);

        btn_updateQuote.setText(context.getString(R.string.update));

        btn_updateQuote.setOnClickListener(v -> {
            UpdateQuoteThread updateQuoteThread = new UpdateQuoteThread();
            updateQuoteThread.start();
        });

    }

    class UpdateQuoteThread extends Thread {

        @Override
        public void run() {
            super.run();
            Quote existingQuote = quotesDb.quoteDao().getQuotes().get(quoteId);
            existingQuote.setQuote(et_updateQuote.getText().toString());
            existingQuote.setAuthor(et_updateAuthor.getText().toString());
            quotesDb.quoteDao().update(existingQuote);

            ((Activity) context).runOnUiThread(() -> {
                quotesListActivity.new QuotesDbManipulator().getQuotes();
                dismiss();
            });
        }
    }
}
