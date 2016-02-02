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
    private static File musicPath;

    static {
        allSongs = new ArrayList<>();
        musicPath = new File("/storage/emulated/0/Music");
        File[] musicFiles = musicPath.listFiles(); //Default Songs Directory
        for (File musicFile : musicFiles) {
            allSongs.add(new Song(musicFile.getName(), musicFile.toString()));
        }
    }

    public SongList() {
        File[] musicFiles = musicPath.listFiles(); //Default Songs Directory
        for (File musicFile : musicFiles) {
            allSongs.add(new Song(musicFile.getName(), musicFile.toString()));
        }
    }

    public static List<Song> getSongs() {
        return allSongs;
    }

    public static File getMusicPath() {
        return musicPath;
    }

    public static Song getSongById(int id) {
        for (Song song : allSongs) {
            if (song.getId() == id) {
                return song;
            }
        }
        return null;
    }

    public static Song getNextSong(Song currentSong) {
        for (Song song : allSongs) {
            if (song == currentSong) {
                return getSongById(song.getId() + 1);
            }
        }
        return null;
    }

    public static Song getLastSong(Song currentSong) {
        for (Song song : allSongs) {
            if (song == currentSong) {
                return getSongById(song.getId() - 1);
            }
        }
        return null;
    }


    public static void setMusicPath(File musicPath) {
        allSongs = new ArrayList<>();
        SongList.musicPath = musicPath;
        new SongList();
    }

    public boolean deleteSong(Song song) {
        File deletePath = new File(song.getSongPath());
        return deletePath.delete();
    }
}
