package com.example.joey.pokedex;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;


public class PokedexHome extends AppCompatActivity implements SearchView.OnQueryTextListener {

    static final int TYPE_REQUEST = 7;
    static final int POINT_REQUEST = 6;
    private RecyclerView pokemonList;
    private PokemonAdapter pokemonListAdapter;
    private RecyclerView.LayoutManager pokemonListLinearLayout, pokemonListGridLayout;

    private Pokedex pokedex = new Pokedex();
    final private ArrayList<Pokedex.Pokemon> pokemonsMaster = pokedex.getPokemon();
    private Button typeFilterButton;
    private Button pointFilterButton;
    private Button randomButton;
    final private ArrayList<String> allowedTypesMaster = new ArrayList<String>(Arrays.asList("normal", "fighting", "flying", "poison",
            "ground", "rock", "bug", "ghost", "steel", "fire", "water", "grass", "electric", "psychic", "ice",
            "dragon", "dark", "fairy"));
    public ArrayList<String> allowedTypes = new ArrayList<String>(allowedTypesMaster);
    private int apcutoff, hpcutoff, dpcutoff = 0;
    private boolean listLayout = true;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex_home);

        //init the Pokemon List View
        pokemonList = (RecyclerView) findViewById(R.id.pokemonList);
        pokemonListLinearLayout = new LinearLayoutManager(this);
        pokemonListGridLayout = new GridLayoutManager(this, 2);

        pokemonList.setLayoutManager(pokemonListLinearLayout);

        pokemonListAdapter = new PokemonAdapter(getApplicationContext(), pokemonsMaster);
        pokemonList.setAdapter(pokemonListAdapter);

        typeFilterButton = (Button) findViewById(R.id.typeFilterButton);
        pointFilterButton = (Button) findViewById(R.id.pointFilterButton);
        randomButton = (Button) findViewById(R.id.randomButton);

        typeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent typeOptions = new Intent(getApplicationContext(), TypeFilter.class);
                typeOptions.putStringArrayListExtra("allowedTypes", allowedTypes);
                startActivityForResult(typeOptions, TYPE_REQUEST);
            }
        });
        pointFilterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent pointOptions = new Intent(getApplicationContext(), PointFilter.class);
                pointOptions.putExtra("apcutoff", "" + apcutoff);
                pointOptions.putExtra("hpcutoff", "" + hpcutoff);
                pointOptions.putExtra("dpcutoff", "" + dpcutoff);
                startActivityForResult(pointOptions, POINT_REQUEST);
            }

        });

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushNewPokemonList(createRandomPokemons(20));
            }
        });
    }

    public void switchView(){
        if(listLayout){
            pokemonList.setLayoutManager(pokemonListLinearLayout);
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_dialer));

        }else{
            pokemonList.setLayoutManager(pokemonListGridLayout);
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, android.R.drawable.ic_menu_sort_by_size));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pushNewPokemonList(updatePokemonListByPoints(updatePokemonListByType(pokemonListAdapter.pokemons)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TYPE_REQUEST){
            if(resultCode == RESULT_OK){
                allowedTypes = data.getStringArrayListExtra("allowedTypes");
            }
        }
        if(requestCode == POINT_REQUEST){
            if(resultCode == RESULT_OK){
                apcutoff = Integer.parseInt(data.getStringExtra("apcutoff"));
                hpcutoff = Integer.parseInt(data.getStringExtra("hpcutoff"));
                dpcutoff = Integer.parseInt(data.getStringExtra("dpcutoff"));
            }
        }
    }

    public void pushNewPokemonList(ArrayList<Pokedex.Pokemon> newPokemons){
        pokemonListAdapter.pokemons.clear();
        pokemonListAdapter.pokemons.addAll(newPokemons);
        pokemonListAdapter.notifyDataSetChanged();

    }

    /*
    IMPLEMENTATION OF THE POKEMON SEARCH BY NAME FEATURES
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.swithLayout:
                if(listLayout){
                    listLayout = false;
                }else{
                    listLayout = true;
                }
                switchView();
                pokemonListAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        updatePokedemsByName(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void updatePokedemsByName(String nameQuery){
        ArrayList<Pokedex.Pokemon> newPokemons = new ArrayList<>();
        for(Pokedex.Pokemon pokemon : pokemonsMaster){
            if(pokemon.name.toLowerCase().startsWith(nameQuery.toLowerCase())){
                newPokemons.add(pokemon);
            }
            if(pokemon.number.replace("0", "").equals(nameQuery)){
                newPokemons.add(pokemon);
            }
        }
        newPokemons = updatePokemonListByType(updatePokemonListByPoints(newPokemons));
        if (newPokemons.size() == 1 && (newPokemons.get(0).name.equals(nameQuery) || newPokemons.get(0).number.replace("0","").equals(nameQuery))){
            String name = newPokemons.get(0).name;
            Intent openProfile = new Intent(getApplicationContext(), PokeProfile.class);
            openProfile.putExtra("Name", name);
            startActivity(openProfile);
        }
        pushNewPokemonList(newPokemons);
    }

    public ArrayList<Pokedex.Pokemon> updatePokemonListByType(ArrayList<Pokedex.Pokemon> pokemons){
        ArrayList<Pokedex.Pokemon> newPokemons = new ArrayList<Pokedex.Pokemon>();
        for(Pokedex.Pokemon pokemon : pokemons){
            Boolean add = false;
            for(String type : pokemon.type){
                if(allowedTypes.contains(type)){
                    add = true;
                    break;
                }
            }
            if(add){
                newPokemons.add(pokemon);
            }
        }
        return newPokemons;
    }

    public ArrayList<Pokedex.Pokemon> updatePokemonListByPoints(ArrayList<Pokedex.Pokemon> pokemons){
        ArrayList<Pokedex.Pokemon> newPokemons = new ArrayList<Pokedex.Pokemon>();
        for(Pokedex.Pokemon pokemon : pokemons){
            if(Integer.parseInt(pokemon.attack) > apcutoff &&
                    Integer.parseInt(pokemon.defense) > dpcutoff &&
                    Integer.parseInt(pokemon.hp) > hpcutoff){
                newPokemons.add(pokemon);
            }

        }
        return newPokemons;
    }

    public ArrayList<Pokedex.Pokemon> createRandomPokemons(int numMons){
        ArrayList<Pokedex.Pokemon> newPokemons = new ArrayList<Pokedex.Pokemon>();
        ArrayList<Pokedex.Pokemon> copyMaster = new ArrayList<>(pokemonsMaster);
        for(int i = 0; i < numMons; i++){
            int rand = (int) (Math.random()*copyMaster.size());
            newPokemons.add(copyMaster.get(rand));
            copyMaster.remove(rand);
        }
        return newPokemons;
    }
}