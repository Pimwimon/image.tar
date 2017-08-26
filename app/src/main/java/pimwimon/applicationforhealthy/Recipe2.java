package pimwimon.applicationforhealthy;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Pannawit on 21/7/2560.
 */

public class Recipe2 {

    public static final String TAG = pimwimon.applicationforhealthy.Recipe2.class.getSimpleName();

    public String title;
    public String description;
    public String imageUrl;


    public static ArrayList<Recipe2> getRecipesFromJSON(String jsonString) {
        final ArrayList<Recipe2> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");

            // Get Recipe objects from data
            for (int i = 0; i < recipes.length(); i++) {
                Recipe2 recipe2 = new Recipe2();

                recipe2.title = recipes.getJSONObject(i).getString("Food_type_id");
                recipe2.description = recipes.getJSONObject(i).getString("Food_type");
                recipe2.imageUrl = "http://"+"10.5.50.77:8000"+"/image/foodtype.jpg";

                recipeList.add(recipe2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;

    }


}
