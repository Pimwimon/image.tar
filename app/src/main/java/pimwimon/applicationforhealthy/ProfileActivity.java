package pimwimon.applicationforhealthy;

import android.app.DatePickerDialog;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

public class ProfileActivity extends MainActivity  {

    private Button btn_back,btn_enter;
    private Button mDisplayDate;
    private TextView txt_date,txt_weight,txt_height;
    private Spinner nature;
    private RadioButton rb, rb2;
    private String fid ,birthday;
    ProgressDialog dialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fid = AccessToken.getCurrentAccessToken().getUserId();

        txt_date  = (TextView)findViewById(R.id.txt_TextDate);
        mDisplayDate = (Button)findViewById(R.id.btn_birthday);
        btn_enter= (Button) findViewById(R.id.btn_enter);
        btn_back = (Button) findViewById(R.id.btn_back);
        rb = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        txt_weight = (EditText) findViewById(R.id.editText3);
        txt_height = (EditText) findViewById(R.id.editText);

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ProfileActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthday = year + "-" + month + "-" + dayOfMonth;
                txt_date.setText("วันเกิดของคุณ : "+birthday);
            }
        };

        nature = (Spinner) findViewById(R.id.S_nature);

        btn_enter.setOnClickListener(new View.OnClickListener(){
            public String ip(){
                return "10.5.50.77:8000";
            }

            String url2 = "http://"+this.ip()+"/updateMember/"+ fid.toString() +"/";



            ;
            @Override
            public void onClick(View v) {
                if(rb.isChecked()) {
                    url2 += "male/";
                }
                else if(rb2.isChecked()){
                    url2 += "female/";
                }
                url2 += birthday +"/";
                url2 += txt_weight.getText().toString() + "/";
                url2 += txt_height.getText().toString() +"/";

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
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                RequestQueue rQueue = Volley.newRequestQueue(ProfileActivity.this);
                rQueue.add(request);
            }
        });
    }


}
