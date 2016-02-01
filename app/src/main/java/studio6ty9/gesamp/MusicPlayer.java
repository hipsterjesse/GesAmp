package studio6ty9.gesamp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

public class MusicPlayer extends AppCompatActivity {
    private int songId;
    private Song currentSong;
    private static MediaPlayer player;
    private TextView textViewTitle;
    private ToggleButton toggleButtonPlayStop;
    private static SeekBar seekBarVolume;
    private AudioManager audioManager;
    private double startTime;
    private double finalTime;
    private TextView textViewCurrent;
    private TextView textViewDuration;
    private Handler myHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Bundle extras = getIntent().getExtras();
        songId = extras.getInt("songId");
        currentSong = SongList.getSongById(songId);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewCurrent= (TextView) findViewById(R.id.textViewCurrent);
        textViewDuration= (TextView) findViewById(R.id.textViewDuration);
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

        startTime = player.getCurrentPosition();
        finalTime  = player.getDuration();

        textViewDuration.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        textViewCurrent.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );
        textViewCurrent.postDelayed(UpdateSongTime, 100);

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = player.getCurrentPosition();
            textViewCurrent.setText(String.format("%d:%d",

                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
            );
            textViewCurrent.postDelayed(UpdateSongTime,100);
            //seekbar.setProgress((int)startTime);
            //myHandler.postDelayed(this, 100);
        }
    };

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

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
            seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
