package studio6ty9.gesamp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;

public class MusicPlayer extends AppCompatActivity {
    private int songId;
    private Song currentSong;
    private static MediaPlayer player;
    private TextView textViewTitle;
    private ToggleButton toggleButtonPlayStop;
    private static SeekBar seekBarVolume;
    private AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Bundle extras = getIntent().getExtras();
        songId = extras.getInt("songId");
        currentSong = SongList.getSongById(songId);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.append(currentSong.getSongTitle());


        firstStartMusic();
        seekBarVolume();

        toggleButtonPlayStop = (ToggleButton) findViewById(R.id.toggleButtonPlayStop);
        toggleButtonPlayStop.setChecked(true);
        toggleButtonPlayStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    resumeMusic();
                } else {
                    stopMusic();
                }
            }
        });

    }

    public void firstStartMusic() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
        player = MediaPlayer.create(this, Uri.fromFile(new File(currentSong.getSongPath())));
        player.start();
    }

    public void resumeMusic() {
        player.start();
    }

    public void stopMusic() {
        player.pause();
    }

    public void seekBarVolume() {
        seekBarVolume = (SeekBar) findViewById(R.id.seekBarVolume);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        seekBarVolume.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progressValue;
                    int progressVolume;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progressValue = progress;
                        progressVolume = progressValue;
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progressVolume, progressVolume);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }
}
