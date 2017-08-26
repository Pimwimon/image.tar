package pimwimon.applicationforhealthy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;


public class Exercise1Activity extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    private TextView txt_ex_name,txt_ex_id;
    private float total=0;
    private String a;
    private EditText E_Time,Ex_time;
    private Button btn;
    private String et_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);
        txt_ex_name = (TextView)findViewById(R.id.txt_ex_name);
        txt_ex_id =(TextView)findViewById(R.id.txt_ex_id);
        E_Time = (EditText)findViewById(R.id.Ex_recommended);
        Intent _getexercise = getIntent();
        txt_ex_name.setText(_getexercise.getStringExtra("exercice_name"));
        txt_ex_id.setText(_getexercise.getStringExtra("exercice_id"));
        et_id = _getexercise.getStringExtra("exercice_id");
        String url = "http://"+this.ip()+"/test17/"+AccessToken.getCurrentAccessToken().getUserId()+"/"+et_id;
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                total = Float.parseFloat(string);
                if (total < 0){
                    total = total * -1;

                    E_Time.setText(Float.toString(total));
                }
                else{
                    E_Time.setText("0");
                    Toast.makeText(getApplicationContext(), "คุณควรรับประทานอาหารให้มากกว่าที่ร่างกายต้องการ :)", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(Exercise1Activity.this);
        rQueue.add(request);
        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url2 = "http://"+this.ip()+"/test7/"+AccessToken.getCurrentAccessToken().getUserId()+"/"+et_id + "/";
                Ex_time = (EditText) findViewById(R.id.Ex_Time);
                url2 += Ex_time.getText().toString();
                StringRequest request = new StringRequest(url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        if (string.length() == 16){
                            Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(Exercise1Activity.this, MainActivity.class);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue rQueue = Volley.newRequestQueue(Exercise1Activity.this);
                rQueue.add(request);

            }

            public String ip(){
                return "10.5.50.77:8000";
            }
        });

    }
}
