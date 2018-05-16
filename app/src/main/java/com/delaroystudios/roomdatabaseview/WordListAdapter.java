package com.delaroystudios.roomdatabaseview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delaroystudios.roomdatabaseview.database.Word;

import java.util.List;


public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name, designation, employeeDepartment;

        private WordViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            designation = itemView.findViewById(R.id.designation);
            employeeDepartment = itemView.findViewById(R.id.employeeDepartment);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mWords.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

    final private ItemClickListener mItemClickListener;
    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words

    WordListAdapter(Context context , ItemClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mItemClickListener = listener;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word current = mWords.get(position);
        holder.name.setText(current.getFirst() + " " + current.getLast());
        holder.designation.setText(current.getTitle());
        holder.employeeDepartment.setText(current.getDepartment());
    }

    void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    public List<Word> getTasks() {
        return mWords;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}


