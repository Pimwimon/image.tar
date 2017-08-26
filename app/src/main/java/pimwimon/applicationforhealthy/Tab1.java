package pimwimon.applicationforhealthy;

/**
 * Created by Administrator on 28/6/2560.
 */

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import org.w3c.dom.Text;


public class Tab1 extends Activity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    String url = "http://"+this.ip()+"/test8/" + AccessToken.getCurrentAccessToken().getUserId();
    ProgressDialog dialog;
    Float total;
    TextView txt_suggestion,txt_calorie;
    Button loggout;
    String name;
    private Button btn_notifications;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);



        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        txt_calorie = (TextView) findViewById(R.id.txt_calorie);
        txt_suggestion = (TextView)findViewById(R.id.txt_suggestion);
//        show time notification
        btn_notifications = (Button)findViewById(R.id.btn_notifications);

        btn_notifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                PendingIntent pIntent = PendingIntent.getActivity(Tab1.this,0,intent,0);
                Notification notification = new Notification.Builder(Tab1.this)
                        .setTicker("TickerTitle")
                        .setContentTitle("เลือกรายการของคุณ")
                        .setContentText("มื้อเช้า มื้อเที่ยง มื้อเย็น")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent).getNotification();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0,notification);
            }
        });


        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                total = Float.parseFloat(string);
                if(total < -100)
                {
                    total = total * -1;
                    txt_calorie.setText(total.toString());
                    txt_suggestion.setText("คุณควรออกกำลังกายเพื่อเผาผลาญแคลลอรี่ส่วนเกิน");
                }
                else if(total > 100)
                {
                    txt_calorie.setText(total.toString());
                    txt_suggestion.setText("คุณควรรับประทานอาหารเพิ่มให้เพียงพอกับที่ร่างกายต้องการ");
                }
                else
                {
                    if(total < 0)
                    {
                        total = total * -1;
                    }
                    txt_calorie.setText(total.toString());
                    txt_suggestion.setText("วันนี้ร่างกายตได้รับแคลลอรี่ที่เพียงพอแล้ว :)");
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Tab1.this);
        rQueue.add(request);

//        loggout = (Button)findViewById(R.id.logout);
//        loggout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                String checking = "true";
//                intent.putExtra("checking",checking);
//                startActivity(intent);
//            }
//        });
    }


}
