package studio6ty9.gesamp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicSettings extends AppCompatActivity {

    private File currentFilePath;
    private List<String> folder;
    private EditText editTextSelectedPath;
    private EditText editTextCurrentPath;
    private Dialog dialog;
    private ListView ListViewFolders;
    private ArrayAdapter<String> FoldersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_settings);
        editTextSelectedPath = (EditText) findViewById(R.id.editTextSelectedPath);
        currentFilePath = SongList.getMusicPath();
        editTextSelectedPath.setText(currentFilePath.toString());

    }

    public void ButtonSelectFolder_Click (View view){
        currentFilePath = SongList.getMusicPath();
        dialog = new Dialog(MusicSettings.this);
        dialog.setContentView(R.layout.select_folder_dialog_layout);
        dialog.show();
        ListViewFolders = (ListView) dialog.findViewById(R.id.listViewFolder);
        editTextCurrentPath = (EditText) dialog.findViewById(R.id.editTextCurrentPath);

        showFiles();
        ListViewFolders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (new File(currentFilePath + "/" + FoldersAdapter.getItem(position)).isFile()) {
                    Toast.makeText(MusicSettings.this, "This is no Folder", Toast.LENGTH_LONG).show();
                } else {
                    currentFilePath = new File(currentFilePath + "/" + FoldersAdapter.getItem(position));
                    showFiles();
                    editTextCurrentPath.setText(currentFilePath.toString());
                }
            }
        });
        editTextCurrentPath.setText(currentFilePath.toString());
    }

    public void ButtonBack_Click (View view){
        if (currentFilePath.getParent() !=null) {
            currentFilePath = new File(currentFilePath.getParent());
            showFiles();
            editTextCurrentPath.setText(currentFilePath.toString());
        }else
        {
            Toast.makeText(MusicSettings.this,"No Parent", Toast.LENGTH_LONG).show();
        }
    }

    public void showFiles() {
        folder = new ArrayList<String>();
        if (!currentFilePath.canRead()) {
            Toast.makeText(MusicSettings.this, "is no readable", Toast.LENGTH_LONG).show();
        }
        String[] list = currentFilePath.list();
        if (list != null) {
                for (String file : list) {
                    if (!file.startsWith(".")) {
                        folder.add(file);
                    }
                }
            }

        FoldersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, folder);
        ListViewFolders.setAdapter(FoldersAdapter);
    }

    public void button_SelectFolder_Click(View view){
        SongList.setMusicPath(currentFilePath);
        dialog.cancel();
        editTextSelectedPath.setText(currentFilePath.toString());
    }

}
