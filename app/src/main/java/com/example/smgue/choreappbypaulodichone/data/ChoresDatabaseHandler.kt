package com.example.smgue.choreappbypaulodichone.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.smgue.choreappbypaulodichone.R.string.chore
import com.example.smgue.choreappbypaulodichone.model.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChoresDatabaseHandler(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createChoreTable ="CREATE TABLE " + TABLE_NAME + " ("+
                                KEY_ID + " INTEGER PRIMARY KEY ,"+
                                KEY_CHORE_NAME + " TEXT ,"+
                                KEY_CHORE_ASSIGNED_BY + " TEXT ,"+
                                KEY_CHORE_ASSIGNED_TO + " TEXT ,"+
                                KEY_CHORE_ASSIGNED_TIME + " LONG"+")"
        db?.execSQL(createChoreTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        //create table again
        onCreate(db)
    }

    /**
     * CRUD - Create, Read, Update, Delete
     * */
    fun createChore(chore: Chore){
        var db: SQLiteDatabase = writableDatabase
        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)

        Log.d("Data Inserted", "Success")
        db.close()
    }

    fun readAChore(id : Int): Chore{
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID,
                                KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY,
                                KEY_CHORE_ASSIGNED_TO, KEY_CHORE_ASSIGNED_TIME),
                                KEY_ID +"=?", arrayOf(id.toString() ), null, null, null,null)
        var chore = Chore()

        if (cursor != null){
            cursor.moveToFirst()
            chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
            chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
            chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
            chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

        }
        return chore;
    }

    fun readChores(): ArrayList<Chore>{

       var db: SQLiteDatabase = readableDatabase
        var list : ArrayList<Chore> = ArrayList()

        var selectAll = "SELECT * FROM "+ TABLE_NAME
        var cursor : Cursor = db.rawQuery(selectAll, null)

        if (cursor.moveToFirst()){
            do {
                var chore = Chore()

                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.assignedTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                list.add(chore)

            }while (cursor.moveToNext())
        }

        return list
    }

    fun showHumanDate(timeAssigned : Long): String{
        var dateFormat : DateFormat = DateFormat.getDateInstance()
       var formatedDate = dateFormat.format(Date(timeAssigned!!).time)

        return formatedDate
    }
}