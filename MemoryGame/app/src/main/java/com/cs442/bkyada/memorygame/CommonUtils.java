package com.cs442.bkyada.memorygame;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class CommonUtils {
    Context ctx;
    public static final String MyPREFERENCES = "MySession" ;
    public static final String Id = "idKey";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String easyUnlockLevel = "easyUnlockLevelKey";
    public static final String mediumUnlockLevel = "mediumUnlockLevelKey";
    public static final String hardUnlockLevel = "hardUnlockLevelKey";
    SharedPreferences sharedpreferences;

    public CommonUtils(Context ctx) {
        this.ctx = ctx;
    }

    public void logout(){
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void saveSessionDetails(int userid, String name, String email, int easyUnlockLvl, int mediumUnlockLvl, int hardUnlockLvl){
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Id, userid);
        editor.putString(Name, name);
        editor.putString(Email, email);
        editor.putInt(easyUnlockLevel, easyUnlockLvl);
        editor.putInt(mediumUnlockLevel, mediumUnlockLvl);
        editor.putInt(hardUnlockLevel, hardUnlockLvl);
        editor.commit();

    }

    public boolean updateSessionDetails(String difficulty, int unlockLevel){
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Log.d("", "updateSessionDetails: "+unlockLevel+"ddd"+difficulty);
        if ("Easy".equals(difficulty)) {
            editor.putInt(easyUnlockLevel, unlockLevel);
        } else if ("Medium".equals(difficulty)) {
            editor.putInt(mediumUnlockLevel, unlockLevel);
        } else if ("Hard".equals(difficulty)) {
            editor.putInt(hardUnlockLevel, unlockLevel);
        } else {
            return false;
        }
        editor.commit();
        return true;
    }

    public User getUserSessionDetails(){
        sharedpreferences = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        int id = sharedpreferences.getInt(Id, 0);
        String name = sharedpreferences.getString(Name, "");
        String email = sharedpreferences.getString(Email, "");

        User user = new User(id,name,email);
        user.setEasyUnlockLevel(sharedpreferences.getInt(easyUnlockLevel, 1));
        user.setMediumUnlockLevel(sharedpreferences.getInt(mediumUnlockLevel, 1));
        user.setHardUnlockLevel(sharedpreferences.getInt(hardUnlockLevel, 1));
        return user;
    }

    public boolean isUserLoggedIn(){
        User u = getUserSessionDetails();
        if(u != null && !(u.getEmail()).isEmpty() && !(u.getEmail()).isEmpty()) {
            return true;
        }
        return false;
    }

}
