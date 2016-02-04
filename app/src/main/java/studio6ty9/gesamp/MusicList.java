package studio6ty9.gesamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MusicList extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private ArrayAdapter<Song> songAdapter;
    private ListView listViewSongs;
    private Intent startSong;
    private Button buttonSongCurrentPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music_list);

        listViewSongs = (ListView) findViewById(R.id.listViewSongs);
        buttonSongCurrentPlaying = (Button) findViewById(R.id.buttonSongCurrentPlaying);

        // create the SongAdapter with Songs from SongList and set it to the listView
        songAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SongList.getSongs());
        // Add Music Files to listView
        listViewSongs.setAdapter(songAdapter);

        // set listener to ListView
        listViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song clickedSong = songAdapter.getItem(position);
                openSong(clickedSong);
            }
        });
        buttonSongCurrentPlaying.postDelayed(UpdateCurrentSong, 1000);
    }

    public void openSong(Song song) {
        startSong = new Intent(this, MusicPlayer.class);
        startSong.putExtra("songId", song.getId());
        startActivity(startSong);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    public void action_settings_Click(MenuItem item) {
        Intent settings = new Intent(this, MusicSettings.class);
        startActivityForResult(settings, REQUEST_CODE);
    }

    public void refreshList() {
        songAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SongList.getSongs());
        // Add Music Files to listView
        listViewSongs.setAdapter(songAdapter);
        Song song = MusicPlayer.getCurrentSong();
        Toast.makeText(MusicList.this, song.getSongTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        refreshList();
    }

    public void currentPlaying_Click(View view) {
        startSong.putExtra("songId", MusicPlayer.getCurrentSong().getId());
        startActivity(startSong);
    }

    private Runnable UpdateCurrentSong = new Runnable() {
        public void run() {
            if (MusicPlayer.getCurrentSong() != null) {
                buttonSongCurrentPlaying.setEnabled(true);
                buttonSongCurrentPlaying.setText(MusicPlayer.getCurrentSong().getSongTitle() + " läuft gerade");
            } else {
                buttonSongCurrentPlaying.setEnabled(false);
            }
            buttonSongCurrentPlaying.postDelayed(UpdateCurrentSong, 1000);
        }
    };

}