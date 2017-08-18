package com.mym.bkyada.mastermemory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "memorygame.db";
    public static final String SCORE_TABLE_NAME = "score";
    public static final String SCORE_COLUMN_ID = "id";

    public static final String SCORE_USER_ID = "user_id";
    public static final String SCORE_LEVEL = "level";
    public static final String USERS_EASY_LEVEL = "easy_level";
    public static final String USERS_MEDIUM_LEVEL = "medium_level";
    public static final String USERS_HARD_LEVEL = "hard_level";

    public static final String SCORE_COLUMN_USER = "user";
    public static final String SCORE_COLUMN_SCORE = "score";
    public static final String SCORE_COLUMN_DIFFICULTY = "difficulty";
    public static final String USERS_TABLE_NAME = "users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_PASS = "password";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+ USERS_TABLE_NAME +
                        "( "+ USERS_COLUMN_ID + " INTEGER PRIMARY KEY , " +
                        USERS_COLUMN_NAME + " VARCHAR(200)," +
                        USERS_COLUMN_EMAIL + " VARCHAR(200)," +
                        USERS_COLUMN_PASS + " VARCHAR(200), " +

                        USERS_EASY_LEVEL + " INTEGER DEFAULT 1," +
                        USERS_MEDIUM_LEVEL + " INTEGER DEFAULT 1," +
                        USERS_HARD_LEVEL + " INTEGER DEFAULT 1 " +
                        ")"
        );
        db.execSQL(
                "create table "+ SCORE_TABLE_NAME +
                        "( "+ SCORE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        SCORE_COLUMN_USER + " VARCHAR(200)," +

                        SCORE_USER_ID + " INTEGER, " +
                        SCORE_LEVEL + " INTEGER, " +

                        SCORE_COLUMN_SCORE + " INTEGER," +
                        SCORE_COLUMN_DIFFICULTY + " VARCHAR(200) " +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade: ", "onUpgrade: "+oldVersion+"   "+newVersion);
        if(newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SCORE_TABLE_NAME);
            onCreate(db);
        }
    }

    public String getMyScore(String userName, String difficulty) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + SCORE_TABLE_NAME +
                " where " + SCORE_COLUMN_USER + " = ? and " + SCORE_COLUMN_DIFFICULTY + " = ?" +
                " order by " + SCORE_COLUMN_SCORE + " desc limit 1", new String[] {userName, difficulty});
        res.moveToFirst();
        return res.isAfterLast() ? "0" : res.getString(res.getColumnIndex(SCORE_COLUMN_SCORE));
    }

    public ArrayList<Item> getTopScores(String difficulty) {
        ArrayList<Item> list = new ArrayList<Item>();
        difficulty = difficulty.toLowerCase();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + SCORE_TABLE_NAME +
                " where " + SCORE_COLUMN_DIFFICULTY + " = ?" +
                " order by " + SCORE_COLUMN_SCORE + " desc limit 5", new String[] {difficulty});
        res.moveToFirst();
        while(!res.isAfterLast()){
            list.add(new Item(res.getString(res.getColumnIndex(SCORE_COLUMN_USER)), res.getString(res.getColumnIndex(SCORE_COLUMN_SCORE))));
            res.moveToNext();
        }
        return list;
    }

    public void insertUserScore (int id,int level, String name, int score, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userScoreValues = new ContentValues();
        userScoreValues.put(SCORE_USER_ID, id);
        userScoreValues.put(SCORE_LEVEL, level);
        userScoreValues.put(SCORE_COLUMN_USER, name);
        userScoreValues.put(SCORE_COLUMN_SCORE, score);
        userScoreValues.put(SCORE_COLUMN_DIFFICULTY, difficulty);
        db.insert(SCORE_TABLE_NAME, null, userScoreValues);
    }

    public boolean insertUser (String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(USERS_COLUMN_NAME, name);
        userValues.put(USERS_COLUMN_EMAIL, email);
        userValues.put(USERS_COLUMN_PASS, pass);
        db.insert(USERS_TABLE_NAME, null, userValues);
        return true;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USERS_TABLE_NAME+" where "+USERS_COLUMN_EMAIL+" = ? ", new String[] {email});
        User user = null;
        if (res.moveToFirst()) {
            user = new User(res.getInt(res.getColumnIndex(USERS_COLUMN_ID)),
                    res.getString(res.getColumnIndex(USERS_COLUMN_NAME)),
                    res.getString(res.getColumnIndex(USERS_COLUMN_EMAIL)),
                    res.getString(res.getColumnIndex(USERS_COLUMN_PASS)));
            user.setEasyUnlockLevel(res.getInt(res.getColumnIndex(USERS_EASY_LEVEL)));
            user.setMediumUnlockLevel(res.getInt(res.getColumnIndex(USERS_MEDIUM_LEVEL)));
            user.setHardUnlockLevel(res.getInt(res.getColumnIndex(USERS_HARD_LEVEL)));
        }
        return user;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser (Integer id, String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(USERS_COLUMN_NAME, name);
        userValues.put(USERS_COLUMN_EMAIL, email);
        userValues.put(USERS_COLUMN_PASS, pass);
        db.update(USERS_TABLE_NAME, userValues, USERS_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public boolean updateUserUnlockLevel (Integer id, String difficulty, int unlockLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        if ("Easy".equals(difficulty)) {
            userValues.put(USERS_EASY_LEVEL, unlockLevel);
        } else if ("Medium".equals(difficulty)) {
            userValues.put(USERS_MEDIUM_LEVEL, unlockLevel);
        } else if ("Hard".equals(difficulty)) {
            userValues.put(USERS_HARD_LEVEL, unlockLevel);
        } else {
            return false;
        }

        db.update(USERS_TABLE_NAME, userValues, USERS_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteUser (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USERS_TABLE_NAME,
                USERS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USERS_TABLE_NAME, null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex(USERS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public Result validateUserDetails(String username, String email, String password, String confirm_password){
        boolean isSuccess = false;
        String msg = "Passed Validations.";
        User userFromDb = getUserByEmail(email.trim());
        Result r = validateUserEmailPassword(email, password);
        if(r.isSuccess()) {
            if(username.trim().isEmpty()) {
                msg = "Please enter username.";
            } else if(!password.trim().equals(confirm_password.trim())) {
                msg = "Password mismatch!";
            } else if(userFromDb != null && userFromDb.getId() > 0) {
                msg = "User already registered.\nUse different email address.";
            } else {
                isSuccess = true;
            }
            r = new Result(isSuccess, msg);
        }
        return r;
    }

    public Result validateUserEmailPassword(String email, String password){
        boolean isSuccess = false;
        String msg = "Passed Validations.";
        if (email.trim().isEmpty()) {
            msg = "Please enter email address.";
        } else if (!validateEmail(email)) {
            msg = "Invalid email address.\nPlease enter proper email address.";
        } else if (password.trim().length() < 4) {
            msg = "Password should be more than 4 characters.";
        }   else {
            isSuccess = true;
        }
        return new Result(isSuccess, msg);
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public boolean updateName (String email, String newname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(USERS_COLUMN_NAME, newname);
        db.update(USERS_TABLE_NAME, userValues, USERS_COLUMN_EMAIL + " = ? ", new String[] { email } );
        return true;
    }

    public boolean updateEmail (String email, String newemail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(USERS_COLUMN_EMAIL, newemail);
        db.update(USERS_TABLE_NAME, userValues, USERS_COLUMN_EMAIL + " = ? ", new String[] { email } );
        return true;
    }

    public boolean updatePassword (String email, String newpass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(USERS_COLUMN_PASS, newpass);
        db.update(USERS_TABLE_NAME, userValues, USERS_COLUMN_EMAIL + " = ? ", new String[] { email } );
        return true;
    }
}
