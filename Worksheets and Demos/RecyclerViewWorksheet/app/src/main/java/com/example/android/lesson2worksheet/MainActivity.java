package com.example.android.lesson2worksheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO Question 1
        //Create a recyclerview with a linear layout
        RecyclerView recyclerViews = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViews.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Company> computerCompanies = new ArrayList<>();

        //TODO Question 2
        //add 4 companies: apples, asus, dell, and microsoft

        Company apple = new Company("Apple", 5, R.drawable.apple);
        computerCompanies.add(apple);
        Company asus = new Company("Asus",3,R.drawable.asus);
        computerCompanies.add(asus);
        Company dell = new Company("Dell",3 ,R.drawable.dell);
        computerCompanies.add(dell);
        Company microsoft = new Company("Microsoft",4,R.drawable.microsoft);
        computerCompanies.add(microsoft);

        //TODO Question 3
        //set the adapter using the constructor

        CompaniesAdapter companyAdapt = new CompaniesAdapter(getApplicationContext(),computerCompanies);
        recyclerViews.setAdapter(companyAdapt);

        //TODO Question 3.5??
        //guided row_view layout

    }
}
