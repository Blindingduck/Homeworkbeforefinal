package a57121035_0.it.montri.homeworkbeforefinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bosschuse on 2/1/2017 AD.
 */

public class useSQLiteOpenHelper extends SQLiteOpenHelper {
    public useSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "";
        sql = "CREATE TABLE 'mobileprice' (id INTEGER PRIMARY KEY AUTOINCREMENT , " +
                                            "name TEXT , " +
                                            "price DECIMAL);";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL("INSERT INTO 'mobileprice' (name,price) VALUES ('Sumsung','102');");
        sqLiteDatabase.execSQL("INSERT INTO 'mobileprice' (name,price) VALUES ('HTC One','123');");
        sqLiteDatabase.execSQL("INSERT INTO 'mobileprice' (name,price) VALUES ('Huawei','5932');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE 'mobileprice'");
        onCreate(sqLiteDatabase);
    }
}
