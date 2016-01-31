package studio6ty9.gesamp;

/**
 * Created by Michael on 31.01.2016.
 */
public class Song {
    private String SongTitle;
    private String SongPath;

    public Song(String songTitle, String songPath) {
        SongTitle = songTitle;
        SongPath = songPath;
    }

    public String getSongTitle() {
        return SongTitle;
    }

    public void setSongTitle(String songTitle) {
        SongTitle = songTitle;
    }

    public String getSongPath() {
        return SongPath;
    }

    public void setSongPath(String songPath) {
        SongPath = songPath;
    }
    @Override
    public String toString() {
        return getSongTitle();
    }
}
