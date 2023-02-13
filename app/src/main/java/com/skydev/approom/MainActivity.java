package com.skydev.approom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.skydev.approom.Adapters.RecipeAdapter;
import com.skydev.approom.DataBase.AppDatabase;
import com.skydev.approom.DataBase.Model.Recipe;
import com.skydev.approom.Listeners.RecipeListener;
import com.skydev.approom.databinding.ActivityDetailBinding;
import com.skydev.approom.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeListener {
    //Declare Variables
    private ActivityMainBinding binding;

    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initiation Variables
        init();

        //Set Listeners
        setListeners();
    }


    //Initiation Variables
    private void init(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        //Get Intent Variable
        loadRecipesList();
    }

    //Set Listeners
    private void setListeners(){
        binding.layoutToolBar.setOnClickListener(v -> openAddActivity());
    }

    private void setAdapter(){
        recipeAdapter = new RecipeAdapter(recipeList, this);
        binding.recyclerView.setAdapter(recipeAdapter);
        recipeAdapter.notifyDataSetChanged();
    }

    //Open Add Activity
    private void openAddActivity() {
        Intent recipesListIntent = new Intent(getApplicationContext(), AddActivity.class);
        startActivity(recipesListIntent);
    }

    //Interface Methods
    @Override
    public void onRecipeClicked(Recipe recipe){
        Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
        detailIntent.putExtra("recipe", recipe);
        startActivity(detailIntent);
    }

    //Reload list when back to activity
    @Override
    protected void onResume() {
        super.onResume();
        loadRecipesList();
    }

    //Other Methods
    private void setRecyclerGone(Boolean isGone){
        if (!isGone) {
            binding.recyclerView.setVisibility(View.GONE);
            binding.lbErrorMessage.setVisibility(View.VISIBLE);
            showErrorMessage();
        }
        else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.lbErrorMessage.setVisibility(View.GONE);
            setAdapter();
        }
    }
    private void showErrorMessage(){
        binding.lbErrorMessage.setText(String.format("%s", "Nenhuma receita encontrada!"));

    }
    private void loadRecipesList(){
        AppDatabase db = AppDatabase.getInstance(this);
        recipeList = db.recipeDao().getAll();

        setRecyclerGone(recipeList.size() > 0);
    }
}