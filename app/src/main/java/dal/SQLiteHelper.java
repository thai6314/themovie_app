package dal;


import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



import java.util.ArrayList;
import java.util.List;

import models.Favorite;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE favorite(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT," +
                "movie_id INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Favorite> getFavorite(String username) {
        List<Favorite> list=new ArrayList<>();
        SQLiteDatabase st=getReadableDatabase();
        String selectFavorite = "Select * from  favorite where username="+username;

        Cursor cursor = st.rawQuery(selectFavorite,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()== false){
            Favorite favorite = new Favorite(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            list.add(favorite);
            cursor.moveToNext();
        }
        return list;
    }

    public void addFavorite(Favorite favorite){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",favorite.getUsername());
        contentValues.put("movie_id",favorite.getMovieId());
        db.insert("favorite",null,contentValues);
    }


}

