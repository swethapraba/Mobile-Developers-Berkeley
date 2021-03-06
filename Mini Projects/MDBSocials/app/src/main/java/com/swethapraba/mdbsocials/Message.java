package com.swethapraba.mdbsocials;

import java.util.Date;

/**
 * Created by swetha on 9/29/17.
 */

public class Message {
    String message; //this is the event's name
    String firebaseImageUrl;
    private String eventDate;
    private String description;
    private int interested;
    private String creators;

    public Message()
    {

    }

    public Message(String message, String firebaseImageUrl,String date, String describe,int interest,String creator)
    {
        this.message = message;
        this.firebaseImageUrl = firebaseImageUrl;
        this.eventDate = date;
        this.description = describe;
        setInterest(interest);
        this.creators = creator;
    }

    public String getName(){
        return message;
    }
    public String getEventDate()
    {
        return eventDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setInterest(int x)
    {
        interested = x;
    }

    public int getInterest()
    {
        return interested;
    }
    public String getHost() {
        return creators;
    }
}
