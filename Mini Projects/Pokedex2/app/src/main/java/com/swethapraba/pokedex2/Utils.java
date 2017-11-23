package com.swethapraba.pokedex2;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by swetha on 9/30/17.
 */

//make sure evedrything is static
public class Utils
{
    final static String api = "https://pokeapi.co/api/v2/pokemon/";
    static String getJSONString(String name)
    {
        try{
            URL url = new URL(api + name);
            HttpURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = convertStreamToString(in);
            return response;
        } catch(MalformedURLException e){
            Log.e("error", e.getMessage());
        } catch (IOException e){
            Log.e("error",e.getMessage());
        }
        return "Error 404";
    }

    static String convertStreamToString(java.io.InputStream is){
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    static class JSONHandler extends AsyncTask<String, Void, String>
    {
        Context context;
        private JSONHandler(){}

        JSONHandler(Context context)
        {
            this.context = context;
        }
        @Override
        protected String doInBackground(String... strings)
        {
            MainActivity.buttonEnabled = false;
            if (strings.length == 1)
            {
                String pokemonName = strings[0];
                return getJSONString(pokemonName).toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            //the string it takes is the result of do in background
            TextView textView = (TextView)((Activity) context).findViewById(R.id.textView);
            textView.setText(s);
            MainActivity.buttonEnabled = true;
        }
    }
}
