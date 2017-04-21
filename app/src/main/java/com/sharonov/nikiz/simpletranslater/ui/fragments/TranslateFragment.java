package com.sharonov.nikiz.simpletranslater.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TranslateFragment extends Fragment implements com.sharonov.nikiz.simpletranslater.ui.View {

    @BindView(R.id.from_lang)
    Spinner spinnerFrom;

    @BindView(R.id.to_lang)
    Spinner spinnerTo;

    @BindView(R.id.swipe_img)
    ImageView swipeImg;

    @BindView(R.id.input_text)
    EditText inputText;

    @BindView(R.id.tvTranslatedText)
    TextView translatedText;

    @BindView(R.id.star)
    ImageView starImg;

    PresenterImpl presenter;

    private String langTo = "";


    public TranslateFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        ButterKnife.bind(this, view);

        presenter = new PresenterImpl(this);
        presenter.getLanguages();

        swipeImg.setOnClickListener(v -> swipeLanguages());


        inputText.addTextChangedListener(new TextWatcher() {

            Timer timer = new Timer();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        presenter.getTranslate(editable.toString(), spinnerTo.getSelectedItem().toString());
                    }
                }, 1000);
            }
        });

        starImg.setOnClickListener(v -> presenter.onStarred());

        return view;
    }

    @Override
    public void showLanguages(LanguagesList list) {

        Map<String, String> map = list.getLangs();
        ArrayList<String> langs = new ArrayList<>(map.values());
        List<String> langsKeys = new ArrayList<>(map.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, langsKeys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }


    @Override
    public void showTranslatedText(String text) {
        translatedText.setText(text);
        starImg.setVisibility(View.VISIBLE);
    }

    private void swipeLanguages() {
        int firstSpinnerIndex = spinnerFrom.getSelectedItemPosition();
        spinnerFrom.setSelection(spinnerTo.getSelectedItemPosition());
        spinnerTo.setSelection(firstSpinnerIndex);
    }
}
