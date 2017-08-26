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

public class NoteEditeActivity extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public String imageUrl;
    public static final String TAG = NoteEditeActivity.class.getSimpleName();
    private ListView mListView;
    String url = "http://"+this.ip()+"/getnote/"+ AccessToken.getCurrentAccessToken().getUserId();
    ProgressDialog dialog;
    private String delete_note,N_datetime,N_round,N_title,N_description,fid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edite);
        Intent _getDelUser = getIntent();
        delete_note = _getDelUser.getStringExtra("deleteNote");

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

        RequestQueue rQueue = Volley.newRequestQueue(NoteEditeActivity.this);
        rQueue.add(request);
    }

    void parseJsonData(final String jsonString) {
        try {
            final ArrayList<Note_recipe> recipeList = Note_recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            final Note_adapter adapter = new Note_adapter(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);


            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        N_datetime = ((TextView)view.findViewById(R.id.recipe_list_id)).getText().toString();
                        N_round = ((TextView)view.findViewById(R.id.recipe_list_detail)).getText().toString();
                        N_title = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();

                        N_description = ((TextView)view.findViewById(R.id.recipe_list_subtitle)).getText().toString();
//                    Toast.makeText(getApplicationContext(), "เลือก " + json2.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),NoteEdit2.class);
                        intent.putExtra("N_datetime",N_datetime);
                        intent.putExtra("N_round",N_round);
                        intent.putExtra("N_title",N_title);
                        intent.putExtra("N_description",N_description);
                        finish();
                        startActivity(intent);
                    }
                });

           if (delete_note.length()==11){
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        N_datetime = ((TextView)view.findViewById(R.id.recipe_list_id)).getText().toString();
                        N_round = ((TextView)view.findViewById(R.id.recipe_list_detail)).getText().toString();
                        N_title = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();
                        N_description = ((TextView)view.findViewById(R.id.recipe_list_subtitle)).getText().toString();
                        DelNote();
                    }

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }
    private void DelNote(){
        fid = AccessToken.getCurrentAccessToken().getUserId();
        String url = "http://"+this.ip()+"/deletenote/" + fid.toString() + "/";
        url += N_datetime + "/";
        url += N_round;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(NoteEditeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(NoteEditeActivity.this);
        rQueue.add(request);
    }
}
