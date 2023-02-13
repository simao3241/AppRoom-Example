package com.skydev.approom.Adapters;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.skydev.approom.DataBase.Model.Recipe;
import com.skydev.approom.Listeners.RecipeListener;
import com.skydev.approom.databinding.RecipeListItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecentSearchViewHolder> {

    private List<Recipe> recipeList;
    private final RecipeListener recipeListener;

    public RecipeAdapter(List<Recipe> recipeList, RecipeListener recipeListener) {
        this.recipeList = recipeList;
        this.recipeListener = recipeListener;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecipeListItemBinding searchListItemBinding = RecipeListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new RecipeAdapter.RecentSearchViewHolder(searchListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecentSearchViewHolder holder, int position) {

        holder.setRecentSearchData(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecentSearchViewHolder extends RecyclerView.ViewHolder{

        private RecipeListItemBinding binding;

        public RecentSearchViewHolder(RecipeListItemBinding searchListItemBinding) {
            super(searchListItemBinding.getRoot());
            binding = searchListItemBinding;
        }

        void setRecentSearchData(Recipe recipe){
            binding.lbName.setText("Nome: " + recipe.name);
            binding.lbDate.setText("Data: " + getDateTime(new Date()));
            binding.getRoot().setOnClickListener(v -> recipeListener.onRecipeClicked(recipe));
        }

        private String getDateTime(Date date) {
            return new SimpleDateFormat("dd, MMMM yyyy - hh:mm a", Locale.getDefault()).format(date);
        }
    }
}