package com.sharonov.nikiz.simpletranslater.ui.other;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;

import butterknife.BindView;
import butterknife.ButterKnife;

class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.CustomViewHolder> {

    private final HistoryElement element;

    public FavoritesAdapter(HistoryElement element) {
        this.element = element;
    }

    @Override
    public FavoritesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_row,
                            parent, false);
        return new CustomViewHolder(result);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.CustomViewHolder holder, int position) {
        holder.originalText.setText(element.getOriginalText());
        holder.translatedText.setText(element.getTranslatedText());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.original_text)
        TextView originalText;

        @BindView(R.id.translated_text)
        TextView translatedText;

        CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
