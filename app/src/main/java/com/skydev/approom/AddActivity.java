
package com.skydev.approom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.skydev.approom.DataBase.AppDatabase;
import com.skydev.approom.DataBase.Model.Recipe;
import com.skydev.approom.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    //Declare Variables
    private ActivityAddBinding binding;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initializations
        init();

        //Set Listeners
        setListeners();
    }

    //Initializations
    private void init(){
        recipe = new Recipe();
    }

    //Set Listeners
    private void setListeners(){
        binding.btnConfirm.setOnClickListener(v -> addRecipe());

        binding.btnCancel.setOnClickListener(v -> finish());
    }


    //Add Recipe
    private void addRecipe() {
        String inputName = binding.inputName.getText().toString().trim();
        String inputDescription = binding.inputDesc.getText().toString().trim();
        String inputPreparationTime = binding.inputPreparationTIme.getText().toString().trim();
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
            addToDataBase(recipe);
        }
    }
     private void addToDataBase(Recipe recipe){
         AppDatabase db = AppDatabase.getInstance(this);
         db.recipeDao().insert(recipe);
         makeToast("Receita guardada! Aceda à listagem para ter acesso");
         cleanInputs();
         finish();
     }

    private void cleanInputs(){
        binding.inputName.setText("");
        binding.inputDesc.setText("");
        binding.inputPreparationTIme.setText("");
    }

    //Other Methods
    private void makeToast(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}