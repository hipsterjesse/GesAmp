package studio6ty9.gesamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MusicList extends AppCompatActivity {

    private ArrayAdapter<Song> songAdapter;
    private ListView listViewSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        listViewSongs = (ListView) findViewById(R.id.listViewSongs);

        // create the SongAdapter with Songs from SongList and set it to the listView
        SongList songList = new SongList();
        songAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList.getSongs());
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
    }
    public void openSong(Song song) {
        Intent startSong = new Intent(this, MusicPlayer.class);
        startSong.putExtra("path", song.getSongPath());
        startSong.putExtra("title", song.getSongTitle());
        startActivity(startSong);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }


    public void action_settings_Click(MenuItem item) {
        Intent i = new Intent(this, MusicSettings.class);
        startActivity(i);
    }
}
