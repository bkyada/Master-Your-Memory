package com.cs442.apipalia.memorygame;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class LevelDialog extends DialogFragment {

    static String dialogBoxTitle;
    int unlockLevelCount = 1;
    String category;
    String difficulty;
    String[] levelArray;

    public LevelDialog(){
    }

    public interface LevelDialogListener {
        void onLevelDialogComplete(String category, String difficulty, int level);
    }

    public void setDialogTitle(String title) {
        this.dialogBoxTitle = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setUnLockedLevelCount(int unlockLevelCount) {
        this.unlockLevelCount = unlockLevelCount;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.level_dialog, container);

        levelArray = new String[unlockLevelCount];
        for(int i=1; i <= unlockLevelCount; i++){
            levelArray[i-1] = " LEVEL: "+i;
        }

        getDialog().setTitle(dialogBoxTitle);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.level_list_item, levelArray);

        ListView listView = (ListView) view.findViewById(R.id.list_level);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                LevelDialogListener activity = (LevelDialogListener) getActivity();
                activity.onLevelDialogComplete(category,difficulty, position+1);
                dismiss();
            }
        });

        return view;
    }

}
