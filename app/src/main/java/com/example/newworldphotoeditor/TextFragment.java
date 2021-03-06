package com.example.newworldphotoeditor;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newworldphotoeditor.Adapter.ColorAdapter;
import com.example.newworldphotoeditor.Adapter.FontAdapter;
import com.example.newworldphotoeditor.Interface.ColorListener;
import com.example.newworldphotoeditor.Interface.FontListener;
import com.example.newworldphotoeditor.Interface.TextFragmentListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends BottomSheetDialogFragment implements ColorListener, FontListener {

    int colorDefault = Color.parseColor("#000000");

    TextFragmentListener listener;

    EditText edtText;
    RecyclerView rvColor;
    RecyclerView rvFont;
    ImageView imgClose;
    ImageView imgDone;

    Typeface typeface = Typeface.DEFAULT;

    static TextFragment instance;

    public static TextFragment getInstance(){
        if(instance == null)
            instance = new TextFragment();
        return instance;
    }
    public void setListener(TextFragmentListener listener) {
        this.listener = listener;
    }

    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        edtText = view.findViewById(R.id.edtText);
        rvColor = view.findViewById(R.id.rvColor);
        rvFont = view.findViewById(R.id.rvFont);
        imgClose = view.findViewById(R.id.imgClose);
        imgDone = view.findViewById(R.id.imgDone);

        rvColor.setHasFixedSize(true);
        rvColor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ColorAdapter colorAdapter = new ColorAdapter(getContext(), this);
        rvColor.setAdapter(colorAdapter);

        rvFont.setHasFixedSize(true);
        rvFont.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        FontAdapter fontAdapter = new FontAdapter(getContext(), this);
        rvFont.setAdapter(fontAdapter);

        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTextChanged(typeface, edtText.getText().toString(), colorDefault);
                getFragmentManager().beginTransaction().remove(TextFragment.this).commit();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(TextFragment.this).commit();
            }
        });
        return view;
    }

    @Override
    public void onColorPicked(int color) {
        colorDefault = color;
    }

    @Override
    public void onFontPicked(String fontName) {
        typeface = Typeface.createFromAsset(getContext().getAssets(), new StringBuilder("fonts/").append(fontName).toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
