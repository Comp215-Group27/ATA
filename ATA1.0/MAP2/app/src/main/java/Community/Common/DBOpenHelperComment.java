package Community.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool
 */

/** A database used for saving and querying on comments */

public class DBOpenHelperComment extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public DBOpenHelperComment(Context context){
        super(context,"db_test",null,1);
        db = getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE IF NOT EXISTS Comment(" +
                "_id INTEGER," +
                "randomATA TEXT PRIMARY KEY,"+
                "userID INTEGER," +
                "userName TEXT," +
                "places TEXT," +
                "comment TEXT)");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Comment");
        onCreate(db);
    }


    public void add(String randomATA,int userID, String userName,  String places,String comment){
        db.execSQL("CREATE TABLE IF NOT EXISTS Comment(" +
                "_id INTEGER," +
                "randomATA TEXT PRIMARY KEY,"+
                "userID INTEGER," +
                "userName TEXT," +
                "places TEXT," +
                "comment TEXT)");

        db.execSQL("INSERT INTO Comment (randomATA,userID,userName,places,comment) VALUES(?,?,?,?,?)",new Object[]{randomATA,userID,userName,places,comment});
    }
    public void changeComment(String randomATA, String comment){
        ContentValues values = new ContentValues();
        values.put("comment",comment);
        db.update("Comment", values, "randomATA = ?", new String[]{randomATA});
    };

    public void changePlaces(String randomATA, String places){
        ContentValues values = new ContentValues();
        values.put("places",places);
        db.update("Comment", values, "randomATA = ?", new String[]{randomATA});
    };

    public void deleteRow(String s){
        db.execSQL("DELETE FROM Comment WHERE randomATA = "+" '"+s+"'");
    }

    public void deleteAll(){
        db.execSQL("delete from Comment");
    }

    public void deleteTable(){
        db. execSQL("drop table if exists Comment");
    }

    public ArrayList<commentFormat> getAllData(){

        db.execSQL("CREATE TABLE IF NOT EXISTS Comment(" +
                "_id INTEGER," +
                "randomATA TEXT PRIMARY KEY,"+
                "userID INTEGER," +
                "userName TEXT," +
                "places TEXT," +
                "comment TEXT)");


        ArrayList<commentFormat> list= new ArrayList<commentFormat>();
        Cursor cursor = db.query("Comment",null,null,null,null,null,"randomATA DESC");
        while(cursor.moveToNext()){
            int userID=cursor.getInt(cursor.getColumnIndex("userID"));
            String randomATA = cursor.getString(cursor.getColumnIndex("randomATA"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String places = cursor.getString(cursor.getColumnIndex("places"));
            String comment = cursor.getString(cursor.getColumnIndex("comment"));
            list.add(new commentFormat(randomATA,userID,userName,places,comment));
        }
        return list;
    }


}
