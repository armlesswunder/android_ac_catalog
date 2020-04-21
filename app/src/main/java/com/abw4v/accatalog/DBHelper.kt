package com.abw4v.accatalog

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(private  val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        val DATABASE_NAME = "catalog.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLData(context, "create_db.sql")
        var sqlArr = sqlStr?.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr!!) {
            try {
                db.execSQL(str)
                lineNo++
            } catch(e: SQLiteException) {
                errNo++
            }
        }
    }

    fun recreate() {

        val db = this.writableDatabase
        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLData(context, "update1.sql")
        var sqlArr = sqlStr?.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr!!) {
            try {
                db.execSQL(str)
                lineNo++
            } catch(e: SQLiteException) {
                errNo++
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //execute each update.sql file depending on which version the user is updating from
        //version is defined in the constructor ^
        /*
        if (oldVersion < 2 && newVersion >= 2) { add version 2 updates }
        if (oldVersion < 3 && newVersion >= 3) { add version 3 updates }
         */
    }

    fun getCursorData(tableName: String): Cursor {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $tableName  order by \"Index\";", null)
        }
        catch(e: SQLiteException) {
            println(e)
        }
        return cursor!!
    }

    fun getCursorData(gameName: String, tableName: String, season: String): Cursor {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            if (season != "")
                cursor = db.rawQuery("select * from $gameName$tableName where \"Index\" in (select * from $gameName$season$tableName) order by \"Index\";", null)
            else
                cursor = db.rawQuery("select * from $gameName$tableName order by \"Index\";", null)
        }
        catch(e: SQLiteException) {
            println(e)
        }
        return cursor!!
    }

    fun getData(tableName: String): MutableList<MutableMap<String, String>> {
        val cursor = getCursorData(tableName)


        val myDataset = emptyList<MutableMap<String, String>>().toMutableList()
        cursor.moveToFirst()
        do {
            var map = emptyMap<String, String>().toMutableMap()
            for (column in cursor.columnNames) {
                map[column] = cursor.getString(cursor.getColumnIndex(column))
            }
            myDataset.add(map)
        } while (cursor.moveToNext())

        cursor.close()
        return myDataset
    }

    fun getSeasonData(gameName: String, tableName: String, season: String): MutableList<MutableMap<String, String>> {
        val cursor = getCursorData(gameName, tableName, season)
        val myDataset = emptyList<MutableMap<String, String>>().toMutableList()
        cursor.moveToFirst()
        do {
            var map = emptyMap<String, String>().toMutableMap()
            for (column in cursor.columnNames) {
                map[column] = cursor.getString(cursor.getColumnIndex(column))
            }
            myDataset.add(map)
        } while (cursor.moveToNext())

        cursor.close()
        return myDataset
    }

    fun getTableData(tableName: String): MutableList<String> {
        val cursor = getCursorData(tableName)
        val arrValues = emptyArray<String>().toMutableList()
        cursor.moveToFirst()
        do {

            arrValues.add(cursor.getString(0).replace("_", " "))
        } while (cursor.moveToNext())
        cursor.close()
        return arrValues
    }

    fun checkItem(index: String, tableName: String, value: String?) {

        if (value == null)
            return

        val db = this.writableDatabase
        val result = db.execSQL("update ${tableName} set Selected = ${value} where \"Index\" = ${index};")
        println(result)
    }
}