package pimwimon.applicationforhealthy;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Note2Activity extends AppCompatActivity {
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String ip(){
        return "10.5.50.77:8000";
    }

    Calendar dateTime = Calendar.getInstance();
    private TextView txt_datetime,txt_round;
    private EditText E_title,E_description;
    private Button btn_confirm,btn_date,btn_time;
    private String fid;
    private String count;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note2);
        txt_datetime = (TextView)findViewById(R.id.S_datetime);

        E_title = (EditText)findViewById(R.id.E_title);
        E_description = (EditText)findViewById(R.id.E_description);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);
        btn_date = (Button)findViewById(R.id.btn_datePicker);
        btn_time = (Button)findViewById(R.id.btn_timePicker);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
        updateTextLabel();

        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                    addNote();

            }
        });

    }
    private void updateDate(){
        // สร้าง DatePickerDialog
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        //สร้าง TimePickerDialog
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), true).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // เมื่อผู้ใช้กด set date
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute ) {
            dateTime.set(Calendar.HOUR, hour);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel();

        }
    };

    private void updateTextLabel(){

        txt_datetime.setText(formatDateTime.format(dateTime.getTime()));

    }
    private void addNote(){

        fid = AccessToken.getCurrentAccessToken().getUserId();
        String url = "http://"+this.ip()+"/addnote/" + fid.toString() + "/";
        url += txt_datetime.getText() + "/";
        url += E_title.getText().toString() + "/";
        url += E_description.getText().toString() ;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(Note2Activity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(Note2Activity.this);
        rQueue.add(request);
    }


}
