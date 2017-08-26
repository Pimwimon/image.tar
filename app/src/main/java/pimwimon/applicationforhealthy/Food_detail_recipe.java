package pimwimon.applicationforhealthy;

/**
 * Created by Pannawit on 26/7/2560.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Food_detail_recipe {
    public static final String TAG = pimwimon.applicationforhealthy.Food_detail_recipe.class.getSimpleName();

    public String title;
    public String description;
    public String imageUrl;
    public String instructionUrl;
    public String label;


    public static ArrayList<Food_detail_recipe> getRecipesFromJSON(String jsonString){
        final ArrayList<Food_detail_recipe> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");

            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                Food_detail_recipe Food_detail_recipe = new Food_detail_recipe();

                Food_detail_recipe.title = recipes.getJSONObject(i).getString("Food_id");
                Food_detail_recipe.description = recipes.getJSONObject(i).getString("Food_name");


                Food_detail_recipe.label = recipes.getJSONObject(i).getString("Food_cal")+" / ต่อจาน";
                Food_detail_recipe.imageUrl = "http://"+"10.5.50.77:8000"+"/image/iconfood.png";

                recipeList.add(Food_detail_recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
