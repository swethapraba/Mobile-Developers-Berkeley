package com.swethapraba.mdbsocials;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by swetha on 9/29/17.
 */

public class EventDetails extends AppCompatActivity
{
    //pass in all of the events
     String eventName,eventDate,eventDescription,eventImageURL;

    private DatabaseReference mDatabase;
    Button checked;
    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdetails);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        eventName = intent.getStringExtra("Name");
        eventDate = intent.getStringExtra("Date");
        eventDescription = intent.getStringExtra("Description");

        message = new Message(eventName,"https://google.com/testing",eventDate,eventDescription,0);
        //getting data from Firebase is a disaster
        //ImageView image = (ImageView) findViewById(R.id.eventPic);
        //String portraitString = "";
        //Glide.with(getApplicationContext()).load(portraitString).into(image);

        TextView nameEvents = (TextView) findViewById(R.id.addEventName);
        nameEvents.setText(eventName);

        TextView eventCreate = (TextView) findViewById(R.id.eventCreator);
        //TO DO: get the user data of who made the event , and set that username here
        eventCreate.setText("Test Human human@email.com");

        TextView numInterest = (TextView) findViewById(R.id.numInterested);
        //get the data from Firebase it's broken ;_;
        int data = 0;
        numInterest.setText(data + " interested individuals");

        TextView describe = (TextView) findViewById(R.id.addEventDescription);
        describe.setText(eventDescription);

        TextView eventDates = (TextView) findViewById(R.id.addEventDate);
        eventDates.setText(eventDate);

        checked = (Button) findViewById(R.id.interestedCheck);
        checked.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int x = message.getInterest();
                x+=1;
                message.setInterest(x);
            }
        });
    }
}