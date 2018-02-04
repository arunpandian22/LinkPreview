package nfnlab.in.linkpreviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import nfnlab.in.linkpreview.LinkPreview;
import nfnlab.in.linkpreview.StatusListener;

public class MainActivity extends AppCompatActivity {
LinkPreview linkPreview,linkPreview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkPreview=(LinkPreview)findViewById(R.id.lp);
        linkPreview1=(LinkPreview)findViewById(R.id.lp1);
//      linkPreview.setLink("https://stackoverflow.com");
        linkPreview.setLink("https://stackoverflow.com/questions/27213381/how-to-create-circular-progressbar-in-android", new StatusListener() {
            @Override
            public void sucess() {
                Toast.makeText(MainActivity.this, "sucesses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(String message) {

                Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
        linkPreview1.setLink("https://twitter.com/levelsio/status/956176482958639105", new StatusListener() {
            @Override
            public void sucess() {
                Toast.makeText(MainActivity.this, "sucesses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(String message) {
                Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
//        linkPreview.setLink("https://medium.com");
    }
}
