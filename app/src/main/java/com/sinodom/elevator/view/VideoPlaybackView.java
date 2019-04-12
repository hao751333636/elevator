package com.sinodom.elevator.view;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.MediaController;
import android.widget.VideoView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;

/**
 * Created by 安卓 on 2017/11/23.
 */

public class VideoPlaybackView extends Dialog {

    private Context context;
    private String url;


    public VideoPlaybackView(@NonNull Context context, int theme, String url) {
        super(context, theme);
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_video_playback);

        String str = url.substring(2);
        Logger.d("url截取= " + str);
        String myUrl = BuildConfig.SERVER + str;

        VideoView vv = (VideoView) findViewById(R.id.videoView);
        Logger.d("myUrl= " + myUrl);
        vv.setMediaController(new MediaController(context));
        vv.setVideoURI(Uri.parse(myUrl));
        vv.start();

//        File file = new File(myUrl);
//        vv.setVideoPath(file.getAbsolutePath());

    }
}
