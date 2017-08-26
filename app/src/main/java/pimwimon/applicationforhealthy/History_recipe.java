package pimwimon.applicationforhealthy;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Pannawit on 29/7/2560.
 */

public class History_recipe {
    public static final String TAG = pimwimon.applicationforhealthy.History_recipe.class.getSimpleName();

    public String title;
    public String description;
    public String imageUrl;
    public String instructionUrl;
    public String label;


    public static ArrayList<History_recipe> getRecipesFromJSON(String jsonString){
        final ArrayList<History_recipe> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");


            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                History_recipe recipe = new History_recipe();

                recipe.title = recipes.getJSONObject(i).getString("Food_id");
                recipe.title += "-" + recipes.getJSONObject(i).getString("Fd_meal");

                recipe.description = recipes.getJSONObject(i).getString("Food_name");

                recipe.imageUrl = "http://"+"10.5.50.77:8000"+"/image/iconfood.png";
                recipe.label = "จำนวน ";
                recipe.label += recipes.getJSONObject(i).getString("Fd_unit");
                recipe.label += " : ";
                recipe.label += recipes.getJSONObject(i).getString("Fd_cal");
                recipe.label += " แคลลอรี่";

                recipeList.add(recipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
