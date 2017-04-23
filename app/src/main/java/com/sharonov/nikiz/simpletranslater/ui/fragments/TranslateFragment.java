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
import android.widget.Toast;

import com.sharonov.nikiz.simpletranslater.R;
import com.sharonov.nikiz.simpletranslater.model.data.HistoryElement;
import com.sharonov.nikiz.simpletranslater.model.data.LanguagesList;
import com.sharonov.nikiz.simpletranslater.presenter.PresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;


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

    List<String> langsKeys;


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
            public void afterTextChanged(Editable editable) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Realm realm = Realm.getDefaultInstance();
                        if (realm.where(HistoryElement.class).findAll().contains(editable)) {
                            showTranslatedText(editable.toString());
                        }
                        int position = spinnerTo.getSelectedItemPosition();
                        presenter.getTranslate(editable.toString(), langsKeys.get(position));
                    }
                }, 1000);
            }
        });

        starImg.setOnClickListener(v -> {
            presenter.onStarred();
            starImg.setImageResource(R.drawable.pressed_star);
            Toast.makeText(getContext(), R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void showLanguages(LanguagesList list) {

        Map<String, String> map = list.getLangs();
        ArrayList<String> langs = new ArrayList<>(map.values());
        langsKeys = new ArrayList<>(map.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, langs);
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
