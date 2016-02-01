package studio6ty9.gesamp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

public class MusicPlayer extends AppCompatActivity {
    private int songId;
    private Song currentSong;
    private MediaPlayer player;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Bundle extras = getIntent().getExtras();
        songId = extras.getInt("songId");
        currentSong = SongList.getSongById(songId);

        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.append(currentSong.getSongTitle());

        player = MediaPlayer.create(this, Uri.fromFile(new File(currentSong.getSongPath())));
        player.start();


    }
}
