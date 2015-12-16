package jobninja.eu.maythedroid4thbewithu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;

import jobninja.eu.apkgetter.Main;
import jobninja.eu.apksender.ApkSender;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Main.getPackages(false, new Main.Callback() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(List<Main.Package> packages) {
                for (Main.Package a : packages){
                    if (a.isThirdParty()){
                        ApkSender.getInstance().sendAPK(a, new AsyncHttpClient.StringCallback() {
                            @Override
                            public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
                                if (e != null){
                                    int i = 1;
                                }
                                Log.d("ScrollingActivity", "Upload completed");
                            }
                            // TODO: Progress doesn't work yet but in the future, it still will be the same code,
                            // I suspect that the string getter is not the appropriate object for such a task
                            @Override
                            public void onProgress(AsyncHttpResponse response, long downloaded, long total) {
                                Log.d("ScrollingActivity", (downloaded / total) * 100 + "%");
                            }
                        });
                        break;
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
