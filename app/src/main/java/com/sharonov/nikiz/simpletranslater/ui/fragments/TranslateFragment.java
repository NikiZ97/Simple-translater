package com.sharonov.nikiz.simpletranslater.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TranslateFragment extends Fragment implements com.sharonov.nikiz.simpletranslater.ui.View {

    @BindView(R.id.from_lang)
    Spinner spinner;

    PresenterImpl presenter;


    public TranslateFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        ButterKnife.bind(this, view);

        presenter = new PresenterImpl(this);
        presenter.getLanguages();

        return view;
    }

    @Override
    public void showLanguages(LanguagesList list) {

        Map<String, String> map = list.getLangs();
        ArrayList<String> langs = new ArrayList<>(map.values());

        /*map.values().forEach(langs::add);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, langs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
