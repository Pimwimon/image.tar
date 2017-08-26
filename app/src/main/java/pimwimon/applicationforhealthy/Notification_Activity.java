package pimwimon.applicationforhealthy;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
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


public class Notification_Activity extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    private TextView txt_TextTime;

    private Button btn_time_notification,btn_edit_notification,btn_del_notification;
    ProgressDialog dialog;
    private Button btn_back;
    private String fid,deleteNotification;
    DateFormat formatDateTime = new SimpleDateFormat("HH:mm:ss");
    Calendar Time  = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        btn_time_notification = (Button)findViewById(R.id.btn_time_Picker);
        btn_edit_notification = (Button)findViewById(R.id.btn_edit_notification);
        btn_del_notification= (Button)findViewById(R.id.btn_del_notification);
        txt_TextTime = (TextView)findViewById(R.id.txt_TextTime);

        btn_time_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
        btn_edit_notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Notification_Activity.this,Notification_Listview.class);
                startActivity(i);
            }
        });
        btn_del_notification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteNotification = "True";
                Intent i = new Intent(Notification_Activity.this, Notification_Listview.class);
                i.putExtra("deleteNotification",deleteNotification);
                startActivity(i);
            }
        });
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Notification_Activity.this,MainActivity.class);
                startActivity(i);
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
            addNotification();

        }
    };

    private void updateText(){
        txt_TextTime.setText(formatDateTime.format(Time.getTime()));
    }
    private void addNotification(){
        fid = AccessToken.getCurrentAccessToken().getUserId();
        String url = "http://"+this.ip()+"/addnotification/" + fid.toString() + "/";
        url += txt_TextTime.getText() + "/";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(Notification_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(Notification_Activity.this);
        rQueue.add(request);
    }

    



}
