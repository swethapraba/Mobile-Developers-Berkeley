package com.example.joey.pokedex;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by swetha on 9/21/17.
 */

public class PokeProfile extends AppCompatActivity
{
    Context context;
    private Pokedex pokedex = new Pokedex();
    final ArrayList<Pokedex.Pokemon> pokemonsMaster = pokedex.getPokemon();

    ImageView pokePics;
    TextView pokeNames,pokeNums,pokeSpeciess,pokeHPs,pokeTypes;
    TextView attacks,defenses,spAttacks,spDefenses,speeds,totals,flavortexts;
    Button websearchbuttons;

    String critterName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokeprofile);

        //using the pokemon position, make a pokemon object
        Intent intent = getIntent();
        critterName = intent.getStringExtra("Name");
        //use that pokemon's information and values to set everything for the profile
        int theIndex = 0;// = pokemonsMaster.indexOf(critterName);
        for(int i = 0; i < pokemonsMaster.size();i++)
        {
            String names = pokemonsMaster.get(i).name;
            if (critterName.equals(names)){
                theIndex = i;
                break;
            }
        }
        Pokedex.Pokemon ourPokemon = pokemonsMaster.get(theIndex);

        //critterName = "TJHSST";//the pokemon's name; used for web searching

        pokePics = (ImageView) findViewById(R.id.pokePic);
        String portraitString = "http://assets.pokemon.com/assets/cms2/img/pokedex/full/" + (ourPokemon).number +".png";
        Glide.with(getApplicationContext()).load(portraitString).into(pokePics);

        pokeNames = (TextView) findViewById(R.id.pokeName);
        pokeNames.setText(ourPokemon.name);

        pokeNums = (TextView) findViewById(R.id.pokeNum);
        pokeNums.setText("#" + ourPokemon.number);

        pokeSpeciess = (TextView)findViewById(R.id.pokeSpecies);
        pokeSpeciess.setText(ourPokemon.species.toString());

        pokeHPs = (TextView) findViewById(R.id.pokeHP);
        pokeHPs.setText("HP: " + ourPokemon.hp.toString());

        pokeTypes = (TextView) findViewById(R.id.pokeType);
        String[] thearray = ourPokemon.type;
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i <thearray.length; i++)
        {
            builder.append(thearray[i] + ", ");
        }
        String built = builder.substring(0,builder.length()-2);
        pokeTypes.setText("Type: " + built.toString());

        attacks = (TextView) findViewById(R.id.attack);
        attacks.setText("Attack: " + ourPokemon.attack.toString());

        defenses = (TextView) findViewById(R.id.defense);
        defenses.setText("Defense: " + ourPokemon.defense.toString());
        //else hide

        spAttacks = (TextView) findViewById(R.id.spAttack);
        spAttacks.setText("Special Attack: " + ourPokemon.spAttack.toString());
        //else hide

        spDefenses = (TextView) findViewById(R.id.spDefense);
        spDefenses.setText("Special Defense: " + ourPokemon.spDefense.toString());
        //else hide

        speeds = (TextView) findViewById(R.id.speed);
        speeds.setText("Speed: " + ourPokemon.speed.toString());
        //else hide

        totals = (TextView) findViewById(R.id.total);
        totals.setText("Totals: " + ourPokemon.total.toString());
        //else hide

        flavortexts = (TextView) findViewById(R.id.flavortext);
        flavortexts.setText("Flavor Text: " + ourPokemon.flavorText.toString());
        //else hide

        websearchbuttons = (Button) findViewById(R.id.websearchbutton);

        websearchbuttons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openWebPage("https://www.google.com/#q="+critterName);
            }
        });
    }


    public void openWebPage(String url)
    {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }
}

