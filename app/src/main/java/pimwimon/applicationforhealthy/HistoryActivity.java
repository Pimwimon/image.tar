package pimwimon.applicationforhealthy;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoryActivity extends MainActivity {
    DateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd");
    Calendar date_history = Calendar.getInstance();
    private Button btn_history,btn_back;
    private String date;
    private TextView txt_date_history;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        btn_history= (Button) findViewById(R.id.btn_history);
        txt_date_history=(TextView)findViewById(R.id.txt_date_history);
        btn_history.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(HistoryActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void updateDate(){
        new DatePickerDialog(this, d, date_history.get(Calendar.YEAR),date_history.get(Calendar.MONTH),date_history.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date_history.set(Calendar.YEAR, year);
            date_history.set(Calendar.MONTH, monthOfYear);
            date_history.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };
    private void updateTextLabel(){
        date = (formatDateTime.format(date_history.getTime()));
//        txt_date_history.setText(formatDateTime.format(date_history.getTime()));
        txt_date_history.setText("วันที่คุณเลือก: "+date.toString());
        Intent i = new Intent(HistoryActivity.this,History2Activity.class);
        i.putExtra("date",date);
        startActivity(i);
    }
}
