package studio6ty9.gesamp;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 31.01.2016.
 */
public class SongList {
    private static List<Song> allSongs;

    static {
        allSongs = new ArrayList<Song>();
    }

    public SongList() {
        File[] musicFiles = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).listFiles(); //Default Songs Directory
        for (File musicFile : musicFiles) {
            allSongs.add(new Song(musicFile.getName(), musicFile.toString()));
        }
    }

    public static List<Song> getSongs() {
        return allSongs;
    }
}
