package com.example.sse.customlistview_sse;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.vd_kahn;

        VideoView videoView = (VideoView) findViewById(R.id.vvPlayer);
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();

    }
}
