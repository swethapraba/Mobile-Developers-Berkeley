package com.example.joey.pokedex;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.R.attr.button;

public class TypeFilter extends AppCompatActivity {

    private ArrayList<Button> typeButtons = new ArrayList<Button>();
    private View.OnClickListener typeButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_filter);

        ArrayList<View> allviews = new ArrayList<View>(((ConstraintLayout) findViewById(R.id.typeButtonBin)).getTouchables());
        for(View item : allviews){
            if(((Button) item) instanceof Button){
                typeButtons.add( (Button) item);
            }
        }

        typeButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button clickedButton = (Button) view;
                int currentColor = ((ColorDrawable) clickedButton.getBackground()).getColor();
                if(currentColor == Color.RED){
                    clickedButton.setBackgroundColor(Color.WHITE);
                    clickedButton.setTextColor(Color.RED);
                }else{
                    clickedButton.setBackgroundColor(Color.RED);
                    clickedButton.setTextColor(Color.WHITE);
                }
            }
        };

        //set listeners and configure buttons
        updateButtons();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra("allowedTypes", getAllowedTypes());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtons();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateButtons();
    }

    private void updateButtons(){
        ArrayList<String> prevAllowedTypes = getIntent().getStringArrayListExtra("allowedTypes");

        for(Button button : typeButtons){
            if(prevAllowedTypes.contains(button.getText().toString())){
                button.setBackgroundColor(Color.RED);
                button.setTextColor(Color.WHITE);
            }else{
                button.setBackgroundColor(Color.WHITE);
                button.setTextColor(Color.RED);
            }
            button.setOnClickListener(typeButtonListener);
        }
    }

    public ArrayList<String> getAllowedTypes(){
        ArrayList<String> types = new ArrayList<String>();
        for(Button button : typeButtons){
            if(((ColorDrawable) button.getBackground()).getColor() == Color.RED){
                types.add(button.getText().toString());
            }
        }
        return types;
    }
}
