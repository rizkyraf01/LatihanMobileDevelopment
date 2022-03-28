package com.andan.simplenote

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "note.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_NOTE = "tbl_note"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val TEXT = "text"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTbl1Note = ("CREATE TABLE " + TBL_NOTE + "("
                + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT,"
                + TEXT + " TEXT" + ")")
        db?.execSQL(createTbl1Note)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_NOTE")
        onCreate(db)
    }

    fun updateNote(std: NoteModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(TITLE, std.title)
        contentValues.put(TEXT, std.text)

        val success = db.update(TBL_NOTE, contentValues, "id=" + std.id, null)
        db.close()

        return success
    }

    fun deleteNote(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_NOTE,"id=$id", null)
        db.close()

        return success
    }

    fun insertNote(std: NoteModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(TITLE, std.title)
        contentValues.put(TEXT, std.text)

        val success = db.insert(TBL_NOTE, null, contentValues)
        db.close()

        return success
    }

    @SuppressLint("Range")
    fun getAllNote(): ArrayList<NoteModel>{
        val stdList: ArrayList<NoteModel> = ArrayList()
        val selectQuery = "SELECT *FROM $TBL_NOTE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String
        var text: String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                text = cursor.getString(cursor.getColumnIndex("text"))

                val std = NoteModel(id = id, title = title, text = text)
                stdList.add(std)
            }while (cursor.moveToNext())
        }
        return stdList
    }

}