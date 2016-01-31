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
    private static String musicPath;

    static {
        allSongs = new ArrayList<>();
        musicPath = Environment.DIRECTORY_MUSIC;
    }

    public SongList() {
        File[] musicFiles = Environment.getExternalStoragePublicDirectory(musicPath).listFiles(); //Default Songs Directory
        for (File musicFile : musicFiles) {
            allSongs.add(new Song(musicFile.getName(), musicFile.toString()));
        }
    }

    public static List<Song> getSongs() {
        return allSongs;
    }

    public static String getMusicPath() {
        return musicPath;
    }

    public static void setMusicPath(String musicPath) {
        SongList.musicPath = musicPath;
        new SongList();
    }
}
