package pimwimon.applicationforhealthy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class Food1Activity extends Activity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public static final String TAG = Food1Activity.class.getSimpleName();

    private ListView mListView;
    String url = "http://"+this.ip()+"/test3/";

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food1);
//        txt_food = (TextView)findViewById(R.id.txt_food);
        Intent _getfoodtype = getIntent();
//        txt_food.setText(_getfoodtype.getStringExtra("food_type"));
        url += _getfoodtype.getStringExtra("food_type");




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

        RequestQueue rQueue = Volley.newRequestQueue(Food1Activity.this);
        rQueue.add(request);
    }
    void parseJsonData(final String jsonString) {
        try {
            final ArrayList<Food_detail_recipe> recipeList = Food_detail_recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            final Food_detail_adapter adapter = new Food_detail_adapter(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                    String food_id = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();
                    String food_name = ((TextView)view.findViewById(R.id.recipe_list_subtitle)).getText().toString();

                    String food_cal = ((TextView)view.findViewById(R.id.recipe_list_detail)).getText().toString();
//                    Toast.makeText(getApplicationContext(), "เลือก " + json2.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),FoodDetail.class);

                    intent.putExtra("food_id",food_id);
                    intent.putExtra("food_name",food_name);
                    intent.putExtra("food_cal",food_cal);
                    finish();
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.dismiss();


    }

}
