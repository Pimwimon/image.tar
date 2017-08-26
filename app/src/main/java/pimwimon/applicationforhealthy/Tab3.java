package pimwimon.applicationforhealthy;

/**
 * Created by Administrator on 28/6/2560.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import android.widget.*;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



public class Tab3 extends Activity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public static final String TAG = Tab3.class.getSimpleName();

    private ListView mListView;

    String url = "http://"+this.ip()+"/test2";

    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Tab3.this);
        rQueue.add(request);
    }
    void parseJsonData(String jsonString) {

        try {
            final ArrayList<Recipe> recipeList = Recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            RecipeAdapter adapter = new RecipeAdapter(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//                holder = new ViewHolder();
//                holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.recipe_list_thumbnail);
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    String exercice_id = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();
                    String exercice_name = ((TextView)view.findViewById(R.id.recipe_list_subtitle)).getText().toString();
                    String exercice_cal = ((TextView)view.findViewById(R.id.recipe_list_detail)).getText().toString();
                    Intent intent = new Intent(getApplicationContext(),Exercise1Activity.class);
                    intent.putExtra("exercice_id",exercice_id);
                    intent.putExtra("exercice_name",exercice_name);
                    intent.putExtra("exercice_cal",exercice_cal);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }




}

