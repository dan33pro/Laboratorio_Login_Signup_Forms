package unipiloto.edu.co;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Usuarios.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Register_user_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FULL_NAME";
    public static final String COL_3 = "USERNAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "BORN_DATE";
    public static final String COL_7 = "LATITUDE";
    public static final String COL_8 = "LENGTH";
    public static final String COL_9 = "ALTITUDE";
    public static final String COL_10 = "PRECISION";
    public static final String COL_11 = "ROL";
    public static final String COL_12 = "GENDER";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+"FULL_NAME TEXT, USERNAME TEXT, EMAIL TEXT, PASSWORD TEXT, BORN_DATE NUMERIC, LATITUDE REAL, LENGTH REAL, ALTITUDE REAL, PRECISION REAL, ROL TEXT, GENDER INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void initData() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, 1, 1);
    }

    public boolean insertData(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, user.getFULL_NAME());
        contentValues.put(COL_3, user.getUSERNAME());
        contentValues.put(COL_4, user.getEMAIL());
        contentValues.put(COL_5, user.getPASSWORD());
        contentValues.put(COL_6, user.getBORN_DATE());
        contentValues.put(COL_7, user.getLATITUDE());
        contentValues.put(COL_8, user.getLENGTH());
        contentValues.put(COL_9, user.getALTITUDE());
        contentValues.put(COL_10, user.getPRECISION());
        contentValues.put(COL_11, user.getROL());
        contentValues.put(COL_12, user.getGENDER());

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME, null);
        return result;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where id="+id+"", null);
        return res;
    }

    public Cursor findDataByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from DRINK where "+COL_4+"= '"+email+"'", null);
        return res;
    }
}
