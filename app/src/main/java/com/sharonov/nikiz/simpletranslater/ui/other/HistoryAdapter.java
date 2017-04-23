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
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.CustomViewHolder>
    implements RealmChangeListener {

    private final RealmResults<HistoryElement> elements;

    public HistoryAdapter(RealmResults<HistoryElement> elements) {
        this.elements = elements;
        elements.addChangeListener(this);
    }

    @Override
    public HistoryAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View result = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_row,
                parent, false);
        return new CustomViewHolder(result);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.CustomViewHolder holder, int position) {
        holder.originalText.setText(elements.get(position).getOriginalText());
        holder.translatedText.setText(elements.get(position).getTranslatedText());
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }


    @Override
    public void onChange(Object element) {
        notifyDataSetChanged();
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
