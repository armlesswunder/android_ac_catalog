package com.abw4v.accatalog

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(private  val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 4) {

    companion object {
        val DATABASE_NAME = "catalog.db"
        val ACNH_ALL_CLOTHING_TABLES = arrayOf("acnh_accessory", "acnh_bag", "acnh_bottom", "acnh_dress", "acnh_headwear", "acnh_shoe", "acnh_sock", "acnh_top")
        val ACNH_ALL_FURNITURE_TABLES = arrayOf("acnh_houseware", "acnh_misc", "acnh_wall_mounted")
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
            } catch(e: Throwable) {
                lineNo++
                errNo++
                println("Error #$errNo on line $lineNo using sql statement: $str")
            }
        }
    }

    //used exclusively by developer to fix sql bugs
    fun recreate() {

        val db = this.writableDatabase
        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLData(context, "dev.sql")
        var sqlArr = sqlStr?.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr!!) {
            try {
                db.execSQL(str)
                lineNo++
            } catch(e: Throwable) {
                lineNo++
                errNo++
                println("Error #$errNo on line $lineNo using sql statement: $str")
            }
        }
    }

    fun update1(db: SQLiteDatabase) {

        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLData(context, "update1.sql")
        var sqlArr = sqlStr?.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr!!) {
            try {
                db.execSQL(str)
                lineNo++
            } catch(e: Throwable) {
                lineNo++
                errNo++
                println("Error #$errNo on line $lineNo using sql statement: $str")
            }
        }
    }

    fun update2(db: SQLiteDatabase) {

        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLData(context, "update2.sql")
        var sqlArr = sqlStr?.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr!!) {
            try {
                db.execSQL(str)
                lineNo++
            } catch(e: Throwable) {
                lineNo++
                errNo++
                println("Error #$errNo on line $lineNo using sql statement: $str")
            }
        }
    }

    fun update3(db: SQLiteDatabase) {

        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLData(context, "update3.sql")
        var sqlArr = sqlStr?.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr!!) {
            try {
                db.execSQL(str)
                lineNo++
            } catch(e: Throwable) {
                lineNo++
                errNo++
                println("Error #$errNo on line $lineNo using sql statement: $str")
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //execute each update.sql file depending on which version the user is updating from
        //version is defined in the constructor ^

        if (oldVersion < 2) {
            update1(db)
        }
        if (oldVersion < 3) {
            update2(db)
        }
        if (oldVersion < 4) {
            update3(db)
        }
    }

    fun getCursorData(tableName: String): Cursor {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from $tableName  order by \"Index\";", null)
        }
        catch(e: Throwable) {
            println("Error getting cursor data: $e")
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
        catch(e: Throwable) {
            println("Error getting cursor data: $e")
        }
        return cursor!!
    }

    fun getData(tableName: String): MutableList<MutableMap<String, String>> {
        val myDataset = emptyList<MutableMap<String, String>>().toMutableList()

        if (tableName == "acnh_all_clothing") {

            for (table in ACNH_ALL_CLOTHING_TABLES) {
                val cursor = getCursorData(table)

                cursor.moveToFirst()
                do {
                    var map = emptyMap<String, String>().toMutableMap()
                    for (column in cursor.columnNames) {
                        map[column] = cursor.getString(cursor.getColumnIndex(column))
                    }
                    map["Type"] = table
                    myDataset.add(map)
                } while (cursor.moveToNext())

                cursor.close()
            }
            return myDataset.sortedWith(compareBy({ it["Name"]?.toLowerCase() })).toMutableList()
        }
        else if (tableName == "acnh_all_furniture") {

            for (table in ACNH_ALL_FURNITURE_TABLES) {
                val cursor = getCursorData(table)

                cursor.moveToFirst()
                do {
                    var map = emptyMap<String, String>().toMutableMap()
                    for (column in cursor.columnNames) {
                        map[column] = cursor.getString(cursor.getColumnIndex(column))
                    }
                    map["Type"] = table
                    myDataset.add(map)
                } while (cursor.moveToNext())

                cursor.close()
            }
            return myDataset.sortedWith(compareBy({ it["Name"]?.toLowerCase() })).toMutableList()
        }
        else {

            val cursor = getCursorData(tableName)

            cursor.moveToFirst()
            do {
                var map = emptyMap<String, String>().toMutableMap()
                for (column in cursor.columnNames) {
                    map[column] = cursor.getString(cursor.getColumnIndex(column))
                }
                map["Type"] = tableName
                myDataset.add(map)
            } while (cursor.moveToNext())

            cursor.close()
            return myDataset
        }
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
            map["Type"] = gameName + tableName
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
            //display tables without underscores to user. replace them here.
            arrValues.add(cursor.getString(0).replace("_", " "))
        } while (cursor.moveToNext())
        cursor.close()
        if (tableName == "acnh_table") {
            arrValues.add(0, ("all_clothing").replace("_", " "))
            arrValues.add(0, ("all_furniture").replace("_", " "))
        }
        return arrValues
    }

    fun checkItem(index: String, tableName: String, value: String?) {

        if (value == null)
            return

        val db = this.writableDatabase
        val result = db.execSQL("update ${tableName} set Selected = ${value} where \"Index\" = ${index};")
        println("Successfully updated table: $tableName item number: $index set to: $value. Result: $result")
    }
}