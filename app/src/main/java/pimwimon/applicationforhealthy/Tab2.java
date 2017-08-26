package pimwimon.applicationforhealthy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 28/6/2560.
 */

public class Tab2 extends Activity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public static final String TAG = Tab2.class.getSimpleName();

    private ListView mListView;
    String url = "http://"+this.ip()+"/test1";

    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2);

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

        RequestQueue rQueue = Volley.newRequestQueue(Tab2.this);
        rQueue.add(request);
    }
    void parseJsonData(final String jsonString) {
        try {
            final ArrayList<Recipe2> recipeList = Recipe2.getRecipesFromJSON(jsonString);

            // Create adapter
            final RecipeAdapter2 adapter = new RecipeAdapter2(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    String food_type = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();

//                    Toast.makeText(getApplicationContext(), "เลือก " + json2.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Food1Activity.class);
                    intent.putExtra("food_type",food_type);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.dismiss();


    }


}

