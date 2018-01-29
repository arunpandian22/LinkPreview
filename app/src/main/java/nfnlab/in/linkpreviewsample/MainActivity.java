package nfnlab.in.linkpreviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nfnlab.in.linkpreview.LinkPreview;

public class MainActivity extends AppCompatActivity {
LinkPreview linkPreview,linkPreview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkPreview=(LinkPreview)findViewById(R.id.lp);
        linkPreview1=(LinkPreview)findViewById(R.id.lp1);
//      linkPreview.setLink("https://stackoverflow.com");
        linkPreview.setLink("https://stackoverflow.com/questions/27213381/how-to-create-circular-progressbar-in-android");
        linkPreview1.setLink("https://twitter.com/levelsio/status/956176482958639105");
//        linkPreview.setLink("https://medium.com");
    }
}
