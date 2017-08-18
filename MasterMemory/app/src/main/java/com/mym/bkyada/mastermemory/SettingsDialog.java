package com.mym.bkyada.mastermemory;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Locale;

public class SettingsDialog extends DialogFragment {
    Button btn_language, btn_logout, btn_editprofile;
    Switch music;
    static String dialogBoxTitle;

    public SettingsDialog(){
    }

    public interface SettingsDialogListener {
        void onSettingsDialogComplete(String action);
    }

    public void setDialogTitle(String title) {
        this.dialogBoxTitle = title;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.settings_dialog, container);
        btn_editprofile = (Button) view.findViewById(R.id.btn_editprofile);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        music = (Switch) view.findViewById(R.id.music);
        if(isMyServiceRunning(BackgroundSoundService.class)) {
            music.setChecked(true);
        }
        final Intent objIntent = new Intent(getActivity(), BackgroundSoundService.class);
        music.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getActivity().startService(objIntent);
                } else {
                    getActivity().stopService(objIntent);
                }
            }
        });
        btn_editprofile.setOnClickListener(btnListener);
        btn_logout.setOnClickListener(btnListener);
        getDialog().setTitle(dialogBoxTitle);

        Spinner spinner = (Spinner) view.findViewById(R.id.language_spinner);
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("English");
        categories.add("Spanish");
        categories.add("Chinese");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 1) {
                    setLocale("en");
                } else if (pos == 2) {
                    setLocale("es");
                } else if (pos == 3) {
                    setLocale("zh");
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        return view;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(getContext(), Home.class);
        startActivity(refresh);
    }

    private View.OnClickListener btnListener = new View.OnClickListener() {
        public void onClick(View v) {
            SettingsDialogListener activity = (SettingsDialogListener) getActivity();
            String action = ((Button) v).getText().toString();
            activity.onSettingsDialogComplete(action.toLowerCase());
            dismiss();
        }
    };
}
