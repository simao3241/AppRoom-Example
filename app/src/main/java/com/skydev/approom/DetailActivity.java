package com.skydev.approom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.skydev.approom.DataBase.AppDatabase;
import com.skydev.approom.DataBase.Model.Recipe;
import com.skydev.approom.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    //Declare Variables
    private ActivityDetailBinding binding;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initiation Variables
        init();

        //Set Listeners
        setListeners();
    }


    //Initiation Variables
    private void init(){
        //Get Intent Variable
        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        binding.inputName.setText(recipe.name);
        binding.inputDescription.setText(recipe.description);
        binding.inputPreparation.setText(recipe.preparationTime);
    }

    private void setListeners(){
        binding.layoutToolBar.setOnClickListener(v -> finish());

        binding.btnConfirm.setOnClickListener(v -> updateRecipe());

        binding.btnDelete.setOnClickListener(v -> deleteRecipe());
    }

    //Update Recipe
    private void updateRecipe(){
        String inputName = binding.inputName.getText().toString().trim();
        String inputDescription = binding.inputDescription.getText().toString().trim();
        String inputPreparationTime = binding.inputPreparation.getText().toString().trim();
        if (inputName.isEmpty())
            makeToast("Insira o Nome!");
        else if (inputDescription.isEmpty())
            makeToast("Insira a Descrição!");
        else if (inputPreparationTime.isEmpty())
            makeToast("Insira o tempo de preparação!");
        else{
            recipe.name = inputName;
            recipe.description = inputDescription;
            recipe.preparationTime = inputPreparationTime;
            updateOnDataBase(recipe);
        }
    }
    private void updateOnDataBase(Recipe recipe){
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.recipeDao().update(recipe);
        makeToast("Receita alterada com sucesso!");
    }

    //Delete Recipe
    private void deleteRecipe(){
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.recipeDao().delete(recipe);
        makeToast("Receita eliminada com sucesso!");
        finish();
    }

    //Other Methods
    private void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}