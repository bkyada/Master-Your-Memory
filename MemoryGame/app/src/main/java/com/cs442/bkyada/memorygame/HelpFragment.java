package com.cs442.bkyada.memorygame;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;


public class HelpFragment extends Fragment {

    VideoView videoView;
    MediaController mc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoView = (VideoView) getView().findViewById(R.id.videoView2);
        mc = new MediaController(getActivity());
        String videopath = "android.resource://com.cs442.bkyada.memorygame/"+R.raw.game;
        Uri uri = Uri.parse(videopath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mc);
        mc.setAnchorView(videoView);
        videoView.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
