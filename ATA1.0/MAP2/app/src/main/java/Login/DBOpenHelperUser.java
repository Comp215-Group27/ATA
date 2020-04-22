package Login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Zhenyan Li and Haopeng Song, Group 27, Comp215, University of Liverpool, referred to some codes in github
 */

/** A database used for saving and querying on users */

public class DBOpenHelperUser extends SQLiteOpenHelper {

    private SQLiteDatabase db;


    public DBOpenHelperUser(Context context){
        super(context,"db_test",null,1);
        db = getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER ," +
                "IDNumber INTEGER PRIMARY KEY," +
                "name TEXT," +
                "password TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }


    public void add(int IDNumber, String name,String password){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER," +
                "IDNumber INTEGER PRIMARY KEY," +
                "name TEXT," +
                "password TEXT)");
        db.execSQL("INSERT INTO user (IDNumber,name,password) VALUES(?,?,?)",new Object[]{IDNumber,name,password});
    }

    public void updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }
    public void deleteAll(){
        db.execSQL("delete from user");
    }

    public void deleteTable(){
        db. execSQL("drop table if exists user");
    }

    public ArrayList<User> getAllData(){
        db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                "_id INTEGER," +
                "IDNumber INTEGER PRIMARY KEY," +
                "name TEXT," +
                "password TEXT)");
        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"IDNumber DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int IDNumber = cursor.getInt(cursor.getColumnIndex("IDNumber"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(IDNumber,name,password));
        }
        return list;
    }
}
