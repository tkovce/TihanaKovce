package com.example.tikovce.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tikovce on 1/25/17.
 */
public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAZIV = "naziv_kolaca";
    static final String KEY_VRSTA = "vrsta";
    static final String KEY_GLAVNI_SASTOJAK = "glavni_sastojak";
    static final String KEY_ID_SASTOJKA = "_id_sastojka";
    static final String KEY_CIJENA = "cijena";
    static final String KEY_ID = "id";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "DatabaseName";
    static final String DATABASE_TABLE = "Kolaci";
    static final String DATABASE_TABLE2 = "Cijene";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table Kolaci (_id integer primary key autoincrement, "
                    + "naziv_kolaca text not null, vrsta text not null, glavni_sastojak text not null);";
    static final String DATABASE_CREATE2 =
            "create table Cijene (_id_sastojka integer primary key autoincrement, "
                    + "cijena text not null, id text not null);";

    static final String DATABASE_DELETE =
            "delete * from Kolaci";

    static final String DATABASE_DELETE2 =
            "delete * from Cijene";


    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public void delete()
    {

        db.delete(DATABASE_TABLE, null, null);
    }

    public void delete2()
    {
        db.delete(DATABASE_TABLE2, null, null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert price into the database---
    public long insertPrice(String cijena, String id)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CIJENA, cijena);
        initialValues.put(KEY_ID, id);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    //---deletes a particular price---
    public boolean deletePrice(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_ID_SASTOJKA + "=" + rowId, null) > 0;
    }

    //---retrieves all prices---
    public Cursor getAllPrices()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_ID_SASTOJKA, KEY_CIJENA,
                KEY_ID}, null, null, null, null, null);
    }

    //---retrieves a particular price---
    public Cursor getPrice(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_ID_SASTOJKA,
                                KEY_CIJENA, KEY_ID}, KEY_ID_SASTOJKA + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a price---
    public boolean updatePrice(long rowId, String cijena, String id)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_CIJENA, cijena);
        args.put(KEY_ID, id);
        return db.update(DATABASE_TABLE2, args, KEY_ID_SASTOJKA + "=" + rowId, null) > 0;
    }


    /////////////////////CAKE!!!!!!/////////////////////

    //---insert cake into the database---!
    public long insertCake(String naziv, String vrsta, String glavni_sastojak)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAZIV, naziv);
        initialValues.put(KEY_VRSTA, vrsta);
        initialValues.put(KEY_GLAVNI_SASTOJAK, glavni_sastojak);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular cake---
    public boolean deleteCake(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all cakes---
    public Cursor getAllCakes()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAZIV,
                KEY_VRSTA, KEY_GLAVNI_SASTOJAK}, null, null, null, null, null);
    }

    //---retrieves a particular cake---
    public Cursor getCake(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_NAZIV, KEY_VRSTA, KEY_GLAVNI_SASTOJAK}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a cake---
    public boolean updateContact(long rowId, String naziv, String vrsta, String glavni_sastojak)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAZIV, naziv);
        args.put(KEY_VRSTA, vrsta);
        args.put(KEY_GLAVNI_SASTOJAK, glavni_sastojak);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }



}