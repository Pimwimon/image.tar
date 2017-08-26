package pimwimon.applicationforhealthy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import java.util.ArrayList;

public class History2Activity extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public static final String TAG = History2Activity.class.getSimpleName();
    String date;
    TextView txt_date,txt_exercise,txt_sum;
    ProgressDialog dialog;
    private ListView mListView,mListView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        Intent _getdate = getIntent();
        txt_date = (TextView)findViewById(R.id.textView15);
        date = _getdate.getStringExtra("date");
        txt_exercise = (TextView)findViewById(R.id.txt_exercise);
        txt_date.setText("รายการอาหารประจำวันที่"+date);
        txt_exercise.setText("รายการการออกกำลังกายประจำวันที่" + date);
        String url = "http://"+this.ip()+"/HistoryFood/" + AccessToken.getCurrentAccessToken().getUserId();
        String url2 = "http://"+this.ip()+"/HistoryExercise/" + AccessToken.getCurrentAccessToken().getUserId();
        String url3 = "http://"+this.ip()+"/summary/" + AccessToken.getCurrentAccessToken().getUserId() + "/" + date;
        url += "/"+date;
        url2 += "/"+date;

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

        StringRequest request2 = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                parseJsonData2(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        StringRequest request3 = new StringRequest(url3, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                txt_sum = (TextView)findViewById(R.id.textView19);
                txt_sum.setText(string);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(History2Activity.this);
        rQueue.add(request);
        rQueue.add(request2);
        rQueue.add(request3);

    }
    void parseJsonData(String jsonString) {

        try {
            final ArrayList<History_recipe> recipeList = History_recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            History_adapter adapter = new History_adapter(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.dismiss();

    }
    void parseJsonData2(String jsonString) {

        try {
            final ArrayList<History2_recipe> recipeList = History2_recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            History2_adapter adapter = new History2_adapter(this, recipeList);

            mListView2 = (ListView)findViewById(R.id.recipe_list_view2);
            mListView2.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.dismiss();

    }

}
