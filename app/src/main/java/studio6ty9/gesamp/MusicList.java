package studio6ty9.gesamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MusicList extends AppCompatActivity {

    private ArrayAdapter<Song> songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        ListView listViewSongs = (ListView) findViewById(R.id.listViewSongs);

        // create the SongAdapter with Songs from SongList and set it to the listView
        songAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, SongList.getSongs());
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

}
