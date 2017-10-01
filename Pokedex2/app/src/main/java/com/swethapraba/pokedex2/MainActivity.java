package com.swethapraba.pokedex2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static boolean buttonEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick (View view)
    {
        switch(view.getId())
        {
            case R.id.button:
                if(buttonEnabled)
                {
                    EditText nameText = (EditText) findViewById(R.id.editText);
                    //TextView textView = (TextView) findViewById(R.id.textView);
                    Utils.JSONHandler handler = new Utils.JSONHandler(this);
                    handler.execute(nameText.getText().toString());
                }
        }

    }


}
