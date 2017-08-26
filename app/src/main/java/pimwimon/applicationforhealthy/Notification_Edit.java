package pimwimon.applicationforhealthy;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Notification_Edit extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    private TextView txt_set_time;
    private Button btn_time_edit_notification;
    ProgressDialog dialog;
    DateFormat formatDateTime = new SimpleDateFormat("HH:mm:ss");
    String Nt_no,fid;
    Calendar Time  = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__edit);
        Intent getNotification = getIntent();
        Nt_no =getNotification.getStringExtra("Nno");
        txt_set_time = (TextView)findViewById(R.id.txt_TextTime);
        btn_time_edit_notification = (Button)findViewById(R.id.btn_edit_time_Picker);
        btn_time_edit_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
    }
    private void updateTime(){
        new TimePickerDialog(this, tmo, Time.get(Calendar.HOUR_OF_DAY), Time.get(Calendar.MINUTE), true).show();
    }


    TimePickerDialog.OnTimeSetListener tmo = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Time.set(Calendar.HOUR_OF_DAY, hourOfDay);
            Time.set(Calendar.MINUTE, minute);
            updateText();
            editNotification();


        }
    };

    private void updateText(){
        txt_set_time.setText(formatDateTime.format(Time.getTime()));
    }
    private void editNotification(){

        fid = AccessToken.getCurrentAccessToken().getUserId();
        String url = "http://"+this.ip()+"/updateNotification/" + fid.toString() + "/";
        url += Nt_no + "/";
        url += txt_set_time.getText();

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(Notification_Edit.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(Notification_Edit.this);
        rQueue.add(request);
    }
}
