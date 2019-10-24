package com.example.coordinator;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private YouTubePlayerView youTubePlayerView;

    public static final String MUSICA = "musica";

    public DialogFragment() {
        // Required empty public constructor
    }

    public static DialogFragment newInstance(
            Musica musica) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(MUSICA, musica);

        DialogFragment dialog = new DialogFragment();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        initPictureInPicture(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "SQNtGoM3FVU";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
        return view;
    }


    private void initPictureInPicture(YouTubePlayerView youTubePlayerView) {
        ImageView pictureInPictureIcon = new ImageView(getContext());
        pictureInPictureIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_picture_in_picture));

        pictureInPictureIcon.setOnClickListener( view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                boolean supportsPIP = getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
                if(supportsPIP)
                    getActivity().enterPictureInPictureMode();

            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Can't enter picture in picture mode")
                        .setMessage("In order to enter picture in picture mode you need a SDK version >= N.")
                        .show();
            }
        });

        youTubePlayerView.getPlayerUiController().addView(pictureInPictureIcon);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        if(isInPictureInPictureMode) {
            youTubePlayerView.enterFullScreen();
            youTubePlayerView.getPlayerUiController().showUi(false);
        } else {
            youTubePlayerView.exitFullScreen();
            youTubePlayerView.getPlayerUiController().showUi(true);
        }
    }


}
