package pimwimon.applicationforhealthy;

/**
 * Created by Pannawit on 30/7/2560.
 */
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Note_recipe {

    public static final String TAG = pimwimon.applicationforhealthy.Note_recipe.class.getSimpleName();
    public String id;
    public String title;
    public String description;
    public String imageUrl;
    public String label;

    public static ArrayList<Note_recipe> getRecipesFromJSON(String jsonString){
        final ArrayList<Note_recipe> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");

            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                Note_recipe note_recipe = new Note_recipe();
                note_recipe.id = recipes.getJSONObject(i).getString("N_datetime");
                note_recipe.title = recipes.getJSONObject(i).getString("N_title");
                note_recipe.description = recipes.getJSONObject(i).getString("N_description");
                note_recipe.label = recipes.getJSONObject(i).getString("N_round");
                note_recipe.imageUrl = "http://"+"10.5.50.77:8000"+"/image/noteshow.jpg";

                recipeList.add(note_recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
