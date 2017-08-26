package pimwimon.applicationforhealthy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Pannawit on 29/7/2560.
 */

public class History_detail extends AppCompatActivity {
    public static final String TAG = History_detail.class.getSimpleName();

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get recipe data passed from previous activity
        String title = this.getIntent().getExtras().getString("Food_id");
        String description = this.getIntent().getExtras().getString("Food_name");
        String label = this.getIntent().getExtras().getString("Food_cal");

        String url = this.getIntent().getExtras().getString("url");

        // Set title on action bar of this activity
        setTitle(title);



        // Create WebView and load web page
        mWebView = (WebView) findViewById(R.id.detail_web_view);
        mWebView.loadUrl(url);
    }
}
