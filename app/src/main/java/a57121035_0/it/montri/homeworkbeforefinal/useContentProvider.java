package a57121035_0.it.montri.homeworkbeforefinal;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Bosschuse on 2/1/2017 AD.
 */

public class useContentProvider extends ContentProvider {
    useSQLiteOpenHelper Database;
    SQLiteDatabase db;
    long id = 0;

    @Override
    public boolean onCreate() {
        Database = new useSQLiteOpenHelper(getContext(),"myDataBase.db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String s, String[] selectionArgs, String sort) {
        Cursor myCursor = null;
        db = Database.getReadableDatabase();
        myCursor = db.query("mobileprice",projection,s,selectionArgs,null,null,sort);
        return myCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri myUri = null;
        db = Database.getWritableDatabase();
        id = db.insert("mobileprice",null,contentValues);
        myUri = ContentUris.withAppendedId(uri,id);
        return myUri;

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        db = Database.getWritableDatabase();
        db.execSQL("delete from mobileprice");
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        db = Database.getWritableDatabase();
        int update = db.update("mobileprice",contentValues,s,strings);
        return update;
    }

    public void deleteAll(){
        db = Database.getWritableDatabase();
        db.execSQL("delete from mobileprice");
    }

}
