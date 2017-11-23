package com.example.joey.pokedex;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by joey on 9/17/17.
 */

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonListViewHolder> {

    Context context;
    public ArrayList<Pokedex.Pokemon> pokemons = new ArrayList<>();

    //need to update with information
    public PokemonAdapter(Context context, ArrayList<Pokedex.Pokemon> pokemons){
        this.context = context;
        this.pokemons = new ArrayList<Pokedex.Pokemon>(pokemons);
    }

    public PokemonAdapter.PokemonListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_row, parent, false);

        final PokemonListViewHolder holder = new PokemonAdapter.PokemonListViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = holder.pokemonName.getText().toString();
                Intent intentProfile = new Intent(v.getContext(),PokeProfile.class);
                //intentProfile.putParcelableArrayListExtra("PokemonList",pokemons);
                //intentProfile.putExtra("PokemonIndex",pokemons.indexOf(this));
                //pass to the intent the pokemons list
                //pass to the intent the pokemon's position
                intentProfile.putExtra("Name",name);
                //Context context = v.getContext();
                //int duration = Toast.LENGTH_SHORT;
                //CharSequence text = "hello there click pokemon";
                //Toast toast = Toast.makeText(context, name, duration);
                //toast.show();
                context.startActivity(intentProfile);
            }
        });
        //return

        return new PokemonListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PokemonListViewHolder holder, int position) {
        Pokedex.Pokemon pokemon = pokemons.get(position);
        holder.pokemonName.setText(pokemon.name);
        String portraitString = "http://assets.pokemon.com/assets/cms2/img/pokedex/full/" + pokemon.number +".png";
        Glide.with(context).load(portraitString).into(holder.portrait);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    class PokemonListViewHolder extends RecyclerView.ViewHolder{
        TextView pokemonName;
        ImageView portrait;

        public PokemonListViewHolder(View v){
            super(v);
            pokemonName = (TextView) v.findViewById(R.id.pokemonName);
            portrait = (ImageView) v.findViewById(R.id.profile);
        }

    }
}
