package pimwimon.applicationforhealthy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

public class IndexActivity extends AppCompatActivity  {
    public String ip(){
        return "10.5.50.77:8000";
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button mDisplayDate;
    private TextView txt_date;
    private TextView txt_next;
    private EditText txt_height;
    private EditText txt_weight;
    private String date;
    ProgressDialog dialog;
    private Spinner nature;
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        txt_next = (TextView) findViewById(R.id.txt_next);
        String url = "http://"+this.ip()+"/test15/" + AccessToken.getCurrentAccessToken().getUserId();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                if (string.length() == 11){
                    finish();
                    Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(IndexActivity.this);
        rQueue.add(request);

        txt_date  = (TextView)findViewById(R.id.txt_TextDate);
        mDisplayDate = (Button)findViewById(R.id.btn_birthday);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(IndexActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = year + "-" + month + "-" + dayOfMonth;
                txt_date.setText(date);
            }
        };



        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fid,Fname,Lname;
                Intent _getUserfacebook = getIntent();
                Fid = _getUserfacebook.getStringExtra("UserId");
                Fname = _getUserfacebook.getStringExtra("firstName");
                Lname = _getUserfacebook.getStringExtra("lastName");
                String url2 = "http://"+this.ip()+"/addmember/" + Fid +"/"+ Fname +"/"+ Lname +"/";;
                RadioButton rb;
                RadioButton rb2;
                rb = (RadioButton) findViewById(R.id.radioButton);
                rb2 = (RadioButton) findViewById(R.id.radioButton2);
                if(rb.isChecked()) {
                    url2 += "male/";
                }
                else if(rb2.isChecked()){
                    url2 += "female/";
                }
//                url2 +=Gender+"/";
                url2 += date +"/";
                txt_weight = (EditText) findViewById(R.id.editText3);
                url2 += txt_weight.getText().toString() + "/";
                txt_height = (EditText) findViewById(R.id.editText);
                url2 += txt_height.getText().toString() +"/";




//                nature.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        }
//
//                    }
//                });
                nature = (Spinner) findViewById(R.id.S_nature);
//                String Na = nature.getSelectedItem().toString();
                if (nature.getSelectedItemPosition() == 0){
                    url2 += "1.2";
                }
                else if (nature.getSelectedItemPosition() == 1){
                    url2 += "1.375";
                }
                else if (nature.getSelectedItemPosition() == 2){
                    url2 += "1.55";
                }
                else if (nature.getSelectedItemPosition() == 3){
                    url2 += "1.725";
                }
                else if (nature.getSelectedItemPosition() == 4){
                    url2 += "1.9";
                }


                StringRequest request = new StringRequest(url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String string) {
                        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(IndexActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error !!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                RequestQueue rQueue = Volley.newRequestQueue(IndexActivity.this);
                rQueue.add(request);

            }

            public String ip(){
                return "10.5.50.77:8000";
            }
        });

    }

}
