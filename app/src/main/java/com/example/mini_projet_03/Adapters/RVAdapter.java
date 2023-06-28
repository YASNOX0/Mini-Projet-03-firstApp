package com.example.mini_projet_03.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_projet_03.Models.Quote;
import com.example.mini_projet_03.QuotesListActivity;
import com.example.mini_projet_03.R;
import com.example.mini_projet_03.RoomDb.QuotesDatabase;
import com.example.mini_projet_03.UpdateRecyclerViewItemDF;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private ArrayList<Quote> quotes;
    private final FragmentManager fragmentManager;

    private final QuotesDatabase quotesDb;

    private final QuotesListActivity quotesListActivity;
    private ArrayList<Quote> deleteQuotesList = new ArrayList<>();
    Context context;
    Button btn_quotesListActDelete;

    public RVAdapter(ArrayList<Quote> quotes, FragmentManager fragmentManager, QuotesDatabase quotesDb, QuotesListActivity quotesListActivity, Button btn_quotesListActDelete) {
        this.quotes = quotes;
        this.fragmentManager = fragmentManager;
        this.quotesDb = quotesDb;
        this.quotesListActivity = quotesListActivity;
        this.btn_quotesListActDelete = btn_quotesListActDelete;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_itemRvQuotesListQuote, tv_itemRvQuotesListAuthor;
        CheckBox cb_itemRvQuotesListDelete;
        Button btn_itemRvQuotesListUpdate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemRvQuotesListQuote = itemView.findViewById(R.id.tv_itemRvQuotesListQuote);
            tv_itemRvQuotesListAuthor = itemView.findViewById(R.id.tv_itemRvQuotesListAuthor);
            cb_itemRvQuotesListDelete = itemView.findViewById(R.id.cb_itemRvQuotesListDelete);
            btn_itemRvQuotesListUpdate = itemView.findViewById(R.id.btn_itemRvQuotesListUpdate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_rv_quotes_list, parent, false));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quote quote = quotes.get(position);

        holder.tv_itemRvQuotesListQuote.setText(quote.getQuote());
        holder.tv_itemRvQuotesListAuthor.setText(quote.getAuthor());

        holder.cb_itemRvQuotesListDelete.setOnCheckedChangeListener(null); // Remove previous listener to avoid conflicts

        holder.cb_itemRvQuotesListDelete.setChecked(quote.isQuoteSelected());

        holder.cb_itemRvQuotesListDelete.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            quote.setQuoteSelected(isChecked);
            if (isChecked) {
                deleteQuotesList.add(quote);
            } else {
                deleteQuotesList.remove(quote);
            }
            btn_quotesListActDelete.setText(String.format("Delete (%d)", deleteQuotesList.size()));
        });

        btn_quotesListActDelete.setOnClickListener(view -> {
            for (Quote item : deleteQuotesList) {
                new Thread(() -> {
                    quotesDb.quoteDao().delete(item);
                    deleteQuotesList.remove(item);
                }).start();
            }
            btn_quotesListActDelete.setText(context.getString(R.string.delete_0));

            new Handler().postDelayed(() -> {
                quotesListActivity.new QuotesDbManipulator().getQuotes();
            }, 200);
        });


        holder.btn_itemRvQuotesListUpdate.setOnClickListener(view -> {
            UpdateRecyclerViewItemDF updateRecyclerViewItemDF = new UpdateRecyclerViewItemDF(context, holder.getAdapterPosition(), quotesDb, this, quotesListActivity);
            updateRecyclerViewItemDF.show(fragmentManager, null);
        });

    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }


}
