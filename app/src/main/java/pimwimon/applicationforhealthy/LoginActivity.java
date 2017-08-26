package pimwimon.applicationforhealthy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.AccessToken;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {


    TextView txtuser;
    private String fid ;
    private String fname,lname;
    LoginButton login_button;
    CallbackManager callbackManager;
//    String Test = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(AccessToken.getCurrentAccessToken() != null){
            goMainscreen();
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        initializeControls();
        loginWithFB();

    }

    private void initializeControls(){
        txtuser = (TextView) findViewById(R.id.txtuser);
        callbackManager = CallbackManager.Factory.create();
        login_button=(LoginButton) findViewById(R.id.login_button);
    }
    private String TAG = "LoginActivity";
    private void loginWithFB(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>(){
            @Override
             public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e(TAG,object.toString());
                                Log.e(TAG,response.toString());
                                try {
                                    fid = object.getString("id");
//
                                    if(object.has("first_name"))
                                        fname = object.getString("first_name");
                                    if(object.has("last_name"))
                                        lname = object.getString("last_name");

                                    Intent index = new Intent(LoginActivity.this,IndexActivity.class);
                                    index.putExtra("UserId",fid);
                                    index.putExtra("firstName",fname);
                                    index.putExtra("lastName",lname);
//                                    Intent getboolean = getIntent();
//                                    Test = getboolean.getStringExtra("checking");
//                                    if( Test != "true") {
//
//                                    }
                                    startActivity(index);

                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name");
                request.setParameters(parameters);
                request.executeAsync();

                    fid = loginResult.getAccessToken().getUserId();

                    goMainscreen();


            }
            @Override
            public void onCancel() {
                txtuser.setText("Login Cancelled.");
            }

            @Override
            public void onError(FacebookException error) {
                txtuser.setText("Login Error: "+error.getMessage());
            }
        });
    }

    private void goMainscreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
