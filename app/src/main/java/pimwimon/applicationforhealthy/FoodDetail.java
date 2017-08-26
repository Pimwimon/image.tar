package pimwimon.applicationforhealthy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

public class FoodDetail extends AppCompatActivity {

    private TextView txt_food;
    private TextView txt_food_name;
    private TextView txt_food_cal;
    private EditText edit_unit;
    private Spinner spin;
    private Button confirm;
    private String food_id,fid;;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        txt_food = (TextView)findViewById(R.id.textView25);
        txt_food_name = (TextView)findViewById(R.id.txt_food_name);
        txt_food_cal = (TextView)findViewById(R.id.txt_food_cal);
        Intent _getfoodid = getIntent();
        txt_food.setText(_getfoodid.getStringExtra("food_name"));
        txt_food_name.setText(_getfoodid.getStringExtra("food_name"));
        txt_food_cal.setText(_getfoodid.getStringExtra("food_cal"));
        food_id = _getfoodid.getStringExtra("food_id") + "/";
        fid = AccessToken.getCurrentAccessToken().getUserId();

        confirm = (Button)findViewById(R.id.button4);
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                spin = (Spinner) findViewById(R.id.meal);
                edit_unit = (EditText) findViewById(R.id.editText7);
                String a = spin.getSelectedItem().toString();
                String url = "http://"+this.ip()+"/addfood/" + fid + "/";

                url += food_id;
                url += a + "/";
                url += edit_unit.getText().toString();



                StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {

                        if (string.length() == 16){
                            Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(FoodDetail.this, MainActivity.class);
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

                RequestQueue rQueue = Volley.newRequestQueue(FoodDetail.this);
                rQueue.add(request);

            }

            public String ip(){
                return "10.5.50.77:8000";
            }

        });

//        txt_food_name.setText(fid);
//        url += _getfoodtype.getStringExtra("food_type");
    }
}
