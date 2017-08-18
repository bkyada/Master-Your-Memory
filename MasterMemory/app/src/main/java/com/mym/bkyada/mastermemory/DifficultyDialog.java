package com.mym.bkyada.mastermemory;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DifficultyDialog extends DialogFragment {
    Button btn_easy,btn_medium, btn_hard;
    static String dialogBoxTitle;
    static String category;

    public DifficultyDialog(){
    }

    public interface DifficultyDialogListener {
        void onDifficultyDialogComplete(String category, String difficulty);
    }

    public void setDialogTitle(String title) {
        this.dialogBoxTitle = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.difficulty_dialog, container);
        btn_easy = (Button) view.findViewById(R.id.btn_easy);
        btn_medium = (Button) view.findViewById(R.id.btn_medium);
        btn_hard = (Button) view.findViewById(R.id.btn_hard);

        btn_easy.setOnClickListener(btnListener);
        btn_medium.setOnClickListener(btnListener);
        btn_hard.setOnClickListener(btnListener);

        getDialog().setTitle(dialogBoxTitle);
        return view;
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            DifficultyDialogListener activity = (DifficultyDialogListener) getActivity();
            String difficulty = ((Button) v).getText().toString();
            activity.onDifficultyDialogComplete(category, difficulty);
            dismiss();
        }
    };
}
