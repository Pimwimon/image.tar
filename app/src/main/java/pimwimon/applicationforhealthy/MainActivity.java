package pimwimon.applicationforhealthy;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LocalActivityManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;


public class MainActivity extends AppCompatActivity {


    TextView username;
    private PendingIntent pIntent;
    private AlarmManager alarm;
    private int time;
    LocalActivityManager mLocalActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        if(AccessToken.getCurrentAccessToken() == null){
            goLoginscreen();
        }
        mLocalActivityManager = new LocalActivityManager(this,false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);


        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup(mLocalActivityManager);
        Drawable h = ContextCompat.getDrawable(this,R.mipmap.home1);
        Drawable s = ContextCompat.getDrawable(this,R.mipmap.summary1);
        Drawable f = ContextCompat.getDrawable(this,R.mipmap.food);
        Drawable e = ContextCompat.getDrawable(this,R.mipmap.exerice1);

        /////ส่งชื่อ username
        Intent _getUserName = getIntent();
        String name;
        name = _getUserName.getStringExtra("firstName")+_getUserName.getStringExtra("lastName");

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
            tabSpec.setIndicator("",h);
            tabSpec.setContent(new Intent(this, Tab1.class));

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2");
            tabSpec2.setIndicator("",f);
            tabSpec2.setContent(new Intent(this, Tab2.class));

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3");
               tabSpec3.setIndicator("",e);
                tabSpec3.setContent(new Intent(this, Tab3.class));

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec("tab4");
                tabSpec4.setIndicator("",s);
                tabSpec4.setContent(new Intent(this, Tab4.class));

        tabHost.addTab(tabSpec);
        tabHost.addTab(tabSpec2);
        tabHost.addTab(tabSpec3);
        tabHost.addTab(tabSpec4);


    }
    @Override
    protected void onPause(){
        super.onPause();
        mLocalActivityManager.dispatchPause(!isFinishing());
    }
    @Override
    protected void onResume(){
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }

    private void goLoginscreen(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
     //เมนู
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
       getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.item_profile){
            Toast.makeText(this,"แก้ไขข้อมูลส่วนตัว",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ProfileActivity.class));
        }
        else if (id == R.id.item_history){
            Toast.makeText(this,"ดูประวัติแคลอรี่ย้อนหลัง",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HistoryActivity.class));
        }
        else if (id == R.id.item_notification){
            Toast.makeText(this,"จัดการเวลาแจ้งเตือน",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Notification_Activity.class));
        }
        else if (id == R.id.item_note){
            Toast.makeText(this,"บันทึกประจำวัน",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Note1Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }




}
