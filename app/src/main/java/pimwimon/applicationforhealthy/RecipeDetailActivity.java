package pimwimon.applicationforhealthy;

/**
 * Created by Pannawit on 21/7/2560.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;


public class RecipeDetailActivity extends AppCompatActivity {
    public static final String TAG = RecipeDetailActivity.class.getSimpleName();

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get recipe data passed from previous activity
        String title = this.getIntent().getExtras().getString("title");
        String url = this.getIntent().getExtras().getString("url");
        String description = this.getIntent().getExtras().getString("description");

        // Set title on action bar of this activity
        setTitle(title);



        // Create WebView and load web page
        mWebView = (WebView) findViewById(R.id.detail_web_view);
        mWebView.loadUrl(url);
    }
}
