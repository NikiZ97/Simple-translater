package com.sharonov.nikiz.simpletranslater.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.sharonov.nikiz.simpletranslater.presenter.Presenter;
import com.sharonov.nikiz.simpletranslater.presenter.PresenterImpl;
import com.sharonov.nikiz.simpletranslater.ui.other.HistoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class FavoritesFragment extends Fragment implements com.sharonov.nikiz.simpletranslater.ui.View{

    @BindView(R.id.recycler_view_fav)
    RecyclerView recyclerView;

    private Presenter presenter;

    private Realm realm;

    public FavoritesFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_favorites, container, false);

        realm = Realm.getDefaultInstance();

        ButterKnife.bind(this, result);

        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new HistoryAdapter(realm.where(HistoryElement.class)
                .equalTo("isStarred", true).findAll()));
    }

    // refactor this!
    @Override
    public void showLanguages(LanguagesList list) {}

    @Override
    public void showTranslatedText(String text) {}
}
