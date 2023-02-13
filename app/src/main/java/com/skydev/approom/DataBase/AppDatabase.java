package com.skydev.approom.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.skydev.approom.DataBase.Dao.RecipeDao;
import com.skydev.approom.DataBase.Model.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "RecipesDB";
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if( INSTANCE == null ) {
            synchronized( AppDatabase.class ) {

                INSTANCE = Room.databaseBuilder( context.getApplicationContext(),
                                AppDatabase.class, DB_NAME )
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }
    public abstract RecipeDao recipeDao();
}
