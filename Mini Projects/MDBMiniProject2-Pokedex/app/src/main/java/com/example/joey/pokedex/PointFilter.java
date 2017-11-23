package com.example.joey.pokedex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class PointFilter extends AppCompatActivity {

    private EditText editAP;
    private EditText editDP;
    private EditText editHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_filter);

        editAP = (EditText) findViewById(R.id.attackPointsInput);
        editHP = (EditText) findViewById(R.id.healthPointsInput);
        editDP = (EditText) findViewById(R.id.defensePointsInput);



    }

    @Override
    protected void onResume() {
        super.onResume();
        editAP.setText(getIntent().getStringExtra("apcutoff"));
        editHP.setText(getIntent().getStringExtra("hpcutoff"));
        editDP.setText(getIntent().getStringExtra("dpcutoff"));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("apcutoff", checkNull(editAP.getText().toString()));
        intent.putExtra("dpcutoff", checkNull(editDP.getText().toString()));
        intent.putExtra("hpcutoff", checkNull(editHP.getText().toString()));
        Log.d("CREATION", checkNull(editHP.getText().toString()));
        setResult(RESULT_OK, intent);
        finish();
    }

    private static String checkNull(String s){
        if(isInteger(s)){
            return s;
        }else{
            return "0";
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
