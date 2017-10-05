package com.swethapraba.mdbsocials;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity
{
    //private ArrayList<Message> lists;
    //private RecyclerView eventsList;
    //private RecyclerView.LayoutManager listLayout;
    //private ListAdapter adaptList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerAdapter = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter adapter = new ListAdapter(getApplicationContext(), getList());
        recyclerAdapter.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessageActivity.class);
                startActivity(intent);
            }
        });
        //add in the code for displaying all of your events
    }

    private ArrayList<Message> getList()
    {
        final ArrayList<Message> messages = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren())
                {
                    Log.d(child.getKey(), child.getValue().toString());
                    Message m = child.getValue(Message.class);
                    System.out.println(m.getHost());
                    messages.add(m);

                    Log.d("list length", messages.size()+"");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        ref.addChildEventListener(new ChildEventListener()
//        {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s)
//            {
//                Log.d(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
//                //would normally getKey here
//                messages.add(dataSnapshot.getValue(Message.class));
//                /**messages.add(new Message(dataSnapshot.child("name").getValue(String.class),
//                        dataSnapshot.child("url").getValue(String.class),
//                        dataSnapshot.child("date").getValue(String.class),
//                        dataSnapshot.child("description").getValue(String.class),
//                        Integer.parseInt(dataSnapshot.child("interest").getValue(String.class)),
//                        dataSnapshot.child("email").getValue(String.class)));*/
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
        System.out.println(messages.size());
        return messages;
    }
}