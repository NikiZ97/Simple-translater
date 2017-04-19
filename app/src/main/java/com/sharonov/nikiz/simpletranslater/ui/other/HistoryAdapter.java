package com.sharonov.nikiz.simpletranslater.ui.other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CustomViewHolder> {

    private List<HistoryElement> historyElements;

    public HistoryAdapter(List<HistoryElement> historyElements) {
        this.historyElements = historyElements;
    }

    @Override
    public HistoryAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_row,
                parent, false);
        return new CustomViewHolder(result);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.CustomViewHolder holder, int position) {
        HistoryElement element = historyElements.get(position);
        holder.originalText.setText(element.getOriginalText());
        holder.translatedText.setText(element.getTranslatedText());
    }

    @Override
    public int getItemCount() {
        return historyElements.size();
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
