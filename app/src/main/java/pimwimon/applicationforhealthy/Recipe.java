package pimwimon.applicationforhealthy;

/**
 * Created by Pannawit on 21/7/2560.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Recipe {

    public static final String TAG = pimwimon.applicationforhealthy.Recipe.class.getSimpleName();

    public String title;
    public String description;
    public String imageUrl;
    public String instructionUrl;
    public String label;

    public static ArrayList<Recipe> getRecipesFromJSON(String jsonString){
        final ArrayList<Recipe> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");

            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();

                recipe.title = recipes.getJSONObject(i).getString("Et_id");
                recipe.description = recipes.getJSONObject(i).getString("Et_name");
                recipe.imageUrl = "http://"+"10.5.50.77:8000"+"/image/icon_exercise/"+recipes.getJSONObject(i).getString("Et_pic");

                recipe.label = recipes.getJSONObject(i).getString("Et_cal")+" / ต่อนาที";

                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
