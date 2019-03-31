package basicandroid.com.kadanerischoolreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Kadaneri.db";
    public static final String TABLE_NAME = "Firsttable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "IMAGE";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ( ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String name,byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,image);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getAllData(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+TABLE_NAME,null);
        return result;
    }

    public int deleteData(int s){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(s)});
        return result;
    }


    public boolean updateData(Pojo pojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,pojo.getName());
        contentValues.put(COL_3,pojo.getImage());
        long result = db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(pojo.getId())});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
