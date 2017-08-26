package pimwimon.applicationforhealthy;

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
import com.facebook.AccessToken;

import java.util.ArrayList;

public class Notification_Listview extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public String imageUrl;
    public static final String TAG = Notification_Listview.class.getSimpleName();
    private ListView mListView;
    String url = "http://"+this.ip()+"/getnotification/"+ AccessToken.getCurrentAccessToken().getUserId();
    ProgressDialog dialog;
    private String delete_notification,Nt_time,N_no,fid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__listview);

        Intent _getDelUser = getIntent();
        delete_notification = _getDelUser.getStringExtra("deleteNotification");


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

        RequestQueue rQueue = Volley.newRequestQueue(Notification_Listview.this);
        rQueue.add(request);
    }
    void parseJsonData(final String jsonString) {
        try {
            final ArrayList<Notification_recipe> recipeList = Notification_recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            final Notification_adapter adapter = new Notification_adapter(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);


            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    N_no = ((TextView)view.findViewById(R.id.recipe_list_id)).getText().toString();
                    Nt_time = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();
//                    Toast.makeText(getApplicationContext(), "เลือก " + json2.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Notification_Listview.this,Notification_Edit.class);
                    intent.putExtra("Nno",N_no);
                    startActivity(intent);
                    finish();
                }
            });

            if (delete_notification.length()==4){
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        N_no = ((TextView)view.findViewById(R.id.recipe_list_id)).getText().toString();
                        Nt_time = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();
                        DelNotification();
                    }

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }
    private void DelNotification(){

        fid = AccessToken.getCurrentAccessToken().getUserId();
        String url = "http://"+this.ip()+"/deletenotification/" + fid.toString() + "/";
        url += N_no ;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(Notification_Listview.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(Notification_Listview.this);
        rQueue.add(request);
    }



}
