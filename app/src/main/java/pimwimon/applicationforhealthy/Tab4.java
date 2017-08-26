package pimwimon.applicationforhealthy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.model.ShareLinkContent;

import android.net.Uri;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by Administrator on 2/7/2560.
 */

public class Tab4 extends Activity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    public static final String TAG = Tab4.class.getSimpleName();

    private ListView mListView,mListView2;
    private Button shareButton;
    private String Ex_data,Fd_data;
    String url = "http://"+this.ip()+"/summaryFood/" + AccessToken.getCurrentAccessToken().getUserId();
    String url2 = "http://"+this.ip()+"/summaryExercise/" + AccessToken.getCurrentAccessToken().getUserId();
    ProgressDialog dialog;
    private ShareDialog shareDialog;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab4);

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
        RequestQueue rQueue = Volley.newRequestQueue(Tab4.this);
        rQueue.add(request);
        rQueue.add(request2);



        shareDialog = new ShareDialog(this);
        shareButton = (Button) findViewById(R.id.share_btn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                String cdate = df.format(c.getTime());

                Toast.makeText(getApplicationContext(), "Date : "+cdate, Toast.LENGTH_SHORT).show();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()

                            .setContentUrl(Uri.parse("http://"+this.ip()+"/share/"+AccessToken.getCurrentAccessToken().getUserId()+"/"+cdate))
                            .build();

                    shareDialog.show(linkContent);
                }

            }

            private String ip() {
                return "10.5.50.77:8000";
            }
        });
    }
    void parseJsonData(String jsonString) {

        try {
            final ArrayList<History_recipe> recipeList = History_recipe.getRecipesFromJSON(jsonString);

            // Create adapter
            History_adapter adapter = new History_adapter(this, recipeList);

            mListView = (ListView)findViewById(R.id.recipe_list_view);
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                //                holder = new ViewHolder();
//                holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.recipe_list_thumbnail);
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Fd_data = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    String url4 = "http://"+this.ip()+"/deleteFdService/"+AccessToken.getCurrentAccessToken().getUserId()+"/"+Fd_data;


                                    StringRequest request4 = new StringRequest(url4, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String string) {
                                            Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    RequestQueue rQueue = Volley.newRequestQueue(Tab4.this);
                                    rQueue.add(request4);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }

                        private String ip() {
                            return "10.5.50.77:8000";
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tab4.this);
                    builder.setMessage("Do you want delete data?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

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
            mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                //                holder = new ViewHolder();
//                holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.recipe_list_thumbnail);
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Ex_data = ((TextView)view.findViewById(R.id.recipe_list_title)).getText().toString();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    String url3 = "http://"+this.ip()+"/deleteExService/"+AccessToken.getCurrentAccessToken().getUserId()+"/"+Ex_data;


                                    StringRequest request3 = new StringRequest(url3, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String string) {
                                            Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    RequestQueue rQueue = Volley.newRequestQueue(Tab4.this);
                                    rQueue.add(request3);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }

                        private String ip() {
                            return "10.5.50.77:8000";
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tab4.this);
                    builder.setMessage("Do you want delete data?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.dismiss();


    }

}
