package studio6ty9.gesamp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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
    private Song currentSong;
    private static MediaPlayer player;
    private TextView textViewTitle;
    private static SeekBar seekBarVolume;
    private static SeekBar seekBarSongStatus;
    private AudioManager audioManager;
    private double startTime;
    private double finalTime;
    private TextView textViewCurrent;
    private TextView textViewDuration;
    private ToggleButton toggleButtonPlayStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Bundle extras = getIntent().getExtras();
        currentSong = SongList.getSongById(extras.getInt("songId"));

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewCurrent = (TextView) findViewById(R.id.textViewCurrent);
        textViewDuration = (TextView) findViewById(R.id.textViewDuration);
        seekBarSongStatus = (SeekBar) findViewById(R.id.seekBarSongStatus);

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

        firstStartMusic();
        seekBarVolume();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = player.getCurrentPosition();
            textViewCurrent.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );
            finalTime = player.getDuration() - player.getCurrentPosition();
            textViewDuration.setText(String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
            );

            seekBarSongStatus.setMax(player.getDuration());
            seekBarSongStatus.setProgress(player.getCurrentPosition());

            seekBarSongStatus.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (player.getCurrentPosition() - progress < -1000 || player.getCurrentPosition() - progress > 1000) {
                                player.seekTo(progress);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );

            textViewCurrent.postDelayed(UpdateSongTime, 100);
        }
    };

    public void firstStartMusic() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
        player = MediaPlayer.create(this, Uri.fromFile(new File(currentSong.getSongPath())));
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                startNextSong();
            }

        });

        toggleButtonPlayStop.setChecked(true);
        startTime = player.getCurrentPosition();
        finalTime = player.getDuration();
        textViewTitle.setText(currentSong.getSongTitle());
        seekBarSongStatus.setProgress(0);
        textViewCurrent.postDelayed(UpdateSongTime, 100);
    }

    public void resumeMusic() {
        player.start();
    }

    public void stopMusic() {
        player.pause();
    }


    public void imageButtonForward_Click(View view) {
        startNextSong();
    }

    public void imageButtonBackward_Click(View view) {
        Song song = SongList.getLastSong(currentSong);
        if (song != null) {
            currentSong = song;
            firstStartMusic();
        } else {
            Toast.makeText(MusicPlayer.this, "Es gibt kein vorheriges Lied!", Toast.LENGTH_SHORT).show();
        }
    }

    public void startNextSong() {
        Song song = SongList.getNextSong(currentSong);
        if (song != null) {
            currentSong = song;
            firstStartMusic();
        } else {
            Toast.makeText(MusicPlayer.this, "Es gibt kein nächstes Lied!", Toast.LENGTH_SHORT).show();
        }
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
