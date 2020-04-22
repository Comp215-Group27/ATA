package Main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by Yixian Lu, Group 27, Comp215, Uiversity of Liverpool
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String dataBaseName = "Attractions";

    public static final String tableName1 = "Cambridge";
    public static final String tableName2 = "London";
    public static final String tableName3 = "Manchester";
    public static final String tableName4 = "York";

    public static final String tableCol1 = "attractionName";
    public static final String tableCol2 = "visitTime";
    public static final String tableCol3 = "ticketPrice";



    public DatabaseHelper(@Nullable Context context) {
        super(context, dataBaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    // Create four new tables storing data of four cities
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ tableName1 +"(attractionName TEXT PRIMARY KEY, visitTime TEXT, ticketPrice TEXT)");
        db.execSQL("create table "+ tableName2 +"(attractionName TEXT PRIMARY KEY, visitTime TEXT, ticketPrice TEXT)");
        db.execSQL("create table "+ tableName3 +"(attractionName TEXT PRIMARY KEY, visitTime TEXT, ticketPrice TEXT)");
        db.execSQL("create table "+ tableName4 +"(attractionName TEXT PRIMARY KEY, visitTime TEXT, ticketPrice TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dataBaseName);
    }

    public void deleteCambrige(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from Cambridge");
        db.close();
    }
    public void deleteLondon(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from London");
        db.close();
    }
    public void deleteManchester(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from Manchester");
        db.close();
    }
    public void deleteYork(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from York");

        db.close();
    }

    public void drop(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("drop Table " + tableName1);
        db.close();
    }

    // insert the data of Cambridge into SQlite database
    public boolean insertCambridgeData(String attraction, String visitTime, String ticketPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        // put the value into the ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(tableCol1, attraction);
        contentValues.put(tableCol2, visitTime);
        contentValues.put(tableCol3, ticketPrice);
        // insert value into the SQlite
        long result = db.insert(tableName1, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // insert the data of London into SQlite database
    public boolean insertLondonData(String attractionName, String visitTime, String ticketPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        // put the value into the ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(tableCol1, attractionName);
        contentValues.put(tableCol2, visitTime);
        contentValues.put(tableCol3, ticketPrice);
        // insert value into the SQlite
        long result = db.insert(tableName2, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // insert the data of Manchester into SQlite database
    public boolean insertManchesterData(String attractionName, String visitTime, String ticketPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        // put the value into the ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(tableCol1, attractionName);
        contentValues.put(tableCol2, visitTime);
        contentValues.put(tableCol3, ticketPrice);
        // insert value into the SQlite
        long result = db.insert(tableName3, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // insert the data of York into SQlite database
    public boolean insertYorkData(String attractionName, String visitTime, String ticketPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        // put the value into the ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put(tableCol1, attractionName);
        contentValues.put(tableCol2, visitTime);
        contentValues.put(tableCol3, ticketPrice);
        // insert value into the SQlite
        long result = db.insert(tableName4, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // select the attraction names of one city
    public ArrayList<String> getAttractionNames(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        // create a ArrayList to store the Attraction names
        ArrayList<String> AttractionNames= new ArrayList<String>();
        String[] columns={"attractionName"};
        // select one colume named attractionNames in one selected table
        Cursor crs=db.query(tableName, columns, null,null, null, null, null);
        int i =0;
        while(crs.moveToNext()){
            i++;
            // add the attractionName into the ArrayList
            AttractionNames.add(crs.getString(0));
        }
        return AttractionNames;
    }

    // select the attraction names of one city
    public int getvisitTime(String attractionName,String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        // query visit time  matching one spefific  name of one attraction in one selected table
        Cursor crs = db.query(tableName,null,tableCol1+"=?",new String[]{attractionName},null,null,null);
        StringBuffer buffer = new StringBuffer();
        int i=0;
        while (crs.moveToNext()){
            i++;
            // the String buffer appends the selected visit time
            buffer.append(crs.getString(1) + "\n");
        }
        // Convert String to double, double to integer
        return (int) Math.round(Double.parseDouble(buffer.toString()));
    }

    // select the attraction names of one city
    public int getticketPrice(String attractionName, String tName){
        SQLiteDatabase db = this.getWritableDatabase();
        // query ticket price  matching one spefific  name of one attraction in one selected table
        Cursor crs = db.query(tName,null,tableCol1+"=?",new String[]{attractionName},null,null,null);
        StringBuffer buffer = new StringBuffer();
        int i=0;
        while (crs.moveToNext()){
            i++;
            // the String buffer appends the selected ticket price
            buffer.append(crs.getString(2) + "\n");
        }
        // Convert String to double, double to integer
        return (int) Math.round(Double.parseDouble(buffer.toString()));

    }

}
