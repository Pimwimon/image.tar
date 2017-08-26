package pimwimon.applicationforhealthy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pannawit on 30/7/2560.
 */

public class History2_recipe {
    public static final String TAG = pimwimon.applicationforhealthy.History2_recipe.class.getSimpleName();

    public String title;
    public String description;
    public String imageUrl;

    public String label;


    public static ArrayList<History2_recipe> getRecipesFromJSON(String jsonString){
        final ArrayList<History2_recipe> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");


            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                History2_recipe recipe = new History2_recipe();

                recipe.title = recipes.getJSONObject(i).getString("Et_id");
                recipe.title += "-" + recipes.getJSONObject(i).getString("Ex_round");

                recipe.description = recipes.getJSONObject(i).getString("Et_name");

                recipe.imageUrl = "http://"+"10.5.50.77:8000"+"/image/icon_exercise/" + recipes.getJSONObject(i).getString("Et_pic");;

                recipe.label = recipes.getJSONObject(i).getString("Ex_time");
                recipe.label += " นาที : ";
                recipe.label += recipes.getJSONObject(i).getString("Ex_cal");
                recipe.label += " แคลลอรี่";

                recipeList.add(recipe);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
