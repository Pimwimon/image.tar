package pimwimon.applicationforhealthy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

public class NoteEdit2 extends AppCompatActivity {
    public String ip(){
        return "10.5.50.77:8000";
    }
    private String N_datetime,N_round;
    private EditText E_title,E_description;
    private TextView txt_round;
    private String fid;
    private Button btn_confirm;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit2);

        Intent _getNote = getIntent();
        N_datetime =_getNote.getStringExtra("N_datetime");
        N_round = _getNote.getStringExtra("N_round");
        fid = AccessToken.getCurrentAccessToken().getUserId();
        E_title = (EditText)findViewById(R.id.E_title);
        E_description = (EditText)findViewById(R.id.E_description);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                editNote();

            }
        });
    }
    private void editNote(){
        fid = AccessToken.getCurrentAccessToken().getUserId();
        String url = "http://"+this.ip()+"/editnote/" + fid.toString() + "/";
        url += N_datetime + "/";
        url += N_round + "/";
        url += E_title.getText().toString() + "/";
        url += E_description.getText().toString() ;

        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(NoteEdit2.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(NoteEdit2.this);
        rQueue.add(request);
    }

}
