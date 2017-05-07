package com.sharonov.nikiz.simpletranslater.ui.fragments;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.view.View.GONE;


public class TranslateFragment extends Fragment implements com.sharonov.nikiz.simpletranslater.ui.View {

    /**
     * Spinner for choosing the language from which
     * the translation will be made
     */
    @BindView(R.id.from_lang)
    Spinner spinnerFrom;

    /**
     * Spinner for choosing the language to be translated into
     */
    @BindView(R.id.to_lang)
    Spinner spinnerTo;

    /**
     * The language swipe image
     */
    @BindView(R.id.swipe_img)
    ImageView swipeImg;

    /**
     * Field for input text
     */
    @BindView(R.id.input_text)
    EditText inputText;

    /**
     * Field for displaying the translated text
     */
    @BindView(R.id.tvTranslatedText)
    TextView translatedText;


    @BindView(R.id.star)
    ImageView starImg;

    @BindView(R.id.loading_progress_bar)
    ProgressBar progressBar;

    private PresenterImpl presenter;

    /**
     * List of languages keys
     */
    private List<String> langsKeys;


    public TranslateFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putString("text", translatedText.toString());
//        outState.putString("input", inputText.getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate, container, false);

        ButterKnife.bind(this, view);

        presenter = new PresenterImpl(this);

        // get languages using Retrofit
        presenter.getLanguages();

        /*if (savedInstanceState != null) {
            translatedText.setText(savedInstanceState.getString("text"));
            inputText.setText(savedInstanceState.getString("input"));
        }*/

        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#d2bd1d"),
                PorterDuff.Mode.MULTIPLY);


        swipeImg.setOnClickListener(v -> swapLanguages());


        // this will be triggered when user input text
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
                        if (langsKeys == null) return;
                        // set in spinner langs values, but using langs keys for query
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

    /**
     * This method shows languages in two spinners
     * @param list - model class containing map with langs
     */
    @Override
    public void showLanguages(LanguagesList list) {

        Map<String, String> map = list.getLangs();
        List<String> langsValues = new ArrayList<>(map.values());
        langsKeys = new ArrayList<>(map.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, langsValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        spinnerFrom.setSelection(69);
        spinnerTo.setSelection(16);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }

    /**
     * This method shows translated text
     * @param text - translated text to display in TextView
     */
    @Override
    public void showTranslatedText(String text) {
        translatedText.setText(text);
        starImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(GONE);
    }

    /**
     * This method swaps languages selected in spinners
     */
    private void swapLanguages() {
        int firstSpinnerIndex = spinnerFrom.getSelectedItemPosition();
        spinnerFrom.setSelection(spinnerTo.getSelectedItemPosition());
        spinnerTo.setSelection(firstSpinnerIndex);
    }
}
