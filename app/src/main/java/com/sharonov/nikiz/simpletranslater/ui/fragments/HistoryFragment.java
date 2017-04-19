package com.sharonov.nikiz.simpletranslater.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;
import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.presenter.PresenterImpl;
import com.sharonov.nikiz.simpletranslater.ui.activities.MainActivity;
import com.sharonov.nikiz.simpletranslater.ui.other.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<HistoryElement> historyElements = new ArrayList<>();

    public HistoryFragment() {}

    private PresenterImpl presenter;
    private com.sharonov.nikiz.simpletranslater.ui.View view = new com.sharonov.nikiz.simpletranslater.ui.View() {
        @Override
        public void showLanguages(LanguagesList list) {}

        @Override
        public void showTranslatedText(String text) {}
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result =  inflater.inflate(R.layout.fragment_history, container, false);

        ButterKnife.bind(this, result);

        HistoryAdapter historyAdapter = new HistoryAdapter(historyElements);

        presenter = new PresenterImpl(view);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyAdapter);

        prepareElements();

        return result;
    }

    private void prepareElements() {
        presenter.onAddedToHistory(historyElements);
    }

}
