package studio6ty9.gesamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MusicSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_settings);
        SongList.setMusicPath("Download");
    }

    public void ButtonSpeichern_Click (View view){
        finish();
    }
}
