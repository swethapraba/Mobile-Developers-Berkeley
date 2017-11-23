package com.example.android.lesson2worksheet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by JustinKim on 2/8/17.
 */

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.CustomViewHolder> {
    //TODO Question 4
    //Create a constructor with two arguments. What would they be?
    //Hint: You need to know what the ____ is to be able to manipulate the UI. Also need data to display!
    Context context;
    ArrayList<Company> companies;

    public CompaniesAdapter(Context context, ArrayList<Company> companies)
    {
        this.context = context;
        this.companies = companies;
    }

    //TODO Question 5
    //Override the onCreateViewHolder method
    //Hint: we want a layout inflater from the context of its parent and inflate the row_view
    // layout from the parent, then pass that into our custom view holder
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, null, false);
        return new CustomViewHolder(view);
    }


    //TODO Question 6
    //Bind the data to the holder based on the position
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position)
    {
        Company company = companies.get(position);
        holder. imageView.setImageResource(company.imageResId);
        holder.textView.setText(company .companyName);
    }


    //TODO Question 7
    //Override the item size method
    @Override
    public int getItemCount()
    {
        return 0;
    }

    //TODO Question 8
    //Create a CustomViewHolder class that extends the base RecyclerView.ViewHolder
    //Override its constructor class with the following signature:
    //CustomViewHolder(View view) {
    //    super(view);
    //    ...
    //}
    //and create instance variables for the UI elements in the layout file
    //Hint: findViewById is a method of the View class

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;
        public CustomViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.layout.row_view);
            textView = (TextView) view.findViewById(R.id.textView2);
        }

    }
}


