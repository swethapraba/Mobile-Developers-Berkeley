package com.swethapraba.mdbsocials;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

/**
 * Created by swetha on 9/29/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder>
{
    private Context context;
    private ArrayList<Message> data;

    public ListAdapter(Context context, ArrayList<Message> data)
    {
        this.context = context;
        this.data = data;
        System.out.println(data.size());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);

        final CustomViewHolder holder = new ListAdapter.CustomViewHolder(view);

        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(final CustomViewHolder holder, int position)
    {
        final Message m = data.get(position);
        holder.msgView.setText(m.getName());//message);
        holder.emailfield.setText(m.getHost());
        System.out.print("hello message name is" + m.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            //bugs - clciking on the event does not expand the details page
            @Override
            public void onClick(View view)
            {
                //String name = holder.msgView.getText().toString();
                Intent intent = new Intent(view.getContext(),EventDetails.class);
                intent.putExtra("Name",m.message);
                intent.putExtra("Email",m.getHost());
                intent.putExtra("Date",m.getEventDate().toString()); //bugs here
                intent.putExtra("Description",m.getDescription().toString()); //bugs here
                intent.putExtra("Interest", m.getInterest()); //this is buggy
                context = view.getContext();
                context.startActivity(intent);
                ///Glide.with(context).load(/**/).asBitmap().into(100, 100).get();
            }
        });


        class DownloadFilesTask extends AsyncTask<String, Void, Bitmap> {
            protected Bitmap doInBackground(String... strings) {
                try {return Glide.
                        with(context).
                        load(strings[0]).
                        asBitmap().
                        into(100, 100). // Width and height
                        get();}
                catch (Exception e) {return null;}
            }
            protected void onProgressUpdate(Void... progress) {}

            protected void onPostExecute(Bitmap result) {
                holder.imageView.setImageBitmap(result);
            }
        }
        FirebaseStorage.getInstance().getReferenceFromUrl("gs://mdbsocials2017.appspot.com/").child(m.firebaseImageUrl + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("success", uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("failed", exception.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    /**
     * A card displayed in the RecyclerView
     */
    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView msgView;
        ImageView imageView;
        TextView emailfield;

        public CustomViewHolder (View view) {
            super(view);
            this.msgView = (TextView) view.findViewById(R.id.msgView);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.emailfield = (TextView) view.findViewById(R.id.email);
        }
    }
}
