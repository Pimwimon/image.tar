package pimwimon.applicationforhealthy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;



public class Note1Activity extends AppCompatActivity {

    String deleteNote;

    private TextView text;
    private Button btn_edit,btn_next,btn_delete,btn_back;
    private String count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note1);
        btn_next = (Button) findViewById(R.id.btn_next_note2);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Note1Activity.this, Note2Activity.class);
                startActivity(i);
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Note1Activity.this, NoteEditeActivity.class);
                startActivity(i);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteNote = "delete note";
                Intent i = new Intent(Note1Activity.this, NoteEditeActivity.class);
                i.putExtra("deleteNote",deleteNote);
                startActivity(i);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Note1Activity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


}
