package pimwimon.applicationforhealthy;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Notification_recipe {
    public static final String TAG = pimwimon.applicationforhealthy.Notification_recipe.class.getSimpleName();
    public String id;
    public String title;
    public String imageUrl;

    public static ArrayList<Notification_recipe> getRecipesFromJSON(String jsonString){
        final ArrayList<Notification_recipe> recipeList = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("data");

            // Get Recipe objects from data
            for(int i = 0; i < recipes.length(); i++){
                Notification_recipe notification_recipe = new Notification_recipe();
                notification_recipe.id = recipes.getJSONObject(i).getString("Nt_no");
                notification_recipe.title = recipes.getJSONObject(i).getString("Nt_time");
                notification_recipe.imageUrl = "http://"+"10.5.50.77:8000"+"/image/notification.jpg";

                recipeList.add(notification_recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }
}
