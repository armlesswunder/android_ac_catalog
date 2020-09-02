package com.abw4v.accatalog

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(private  val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 6) {

    companion object {
        val DATABASE_NAME = "catalog.db"
        val ACNH_ALL_CLOTHING_TABLES = arrayOf("acnh_accessory", "acnh_bag", "acnh_bottom", "acnh_dress", "acnh_headwear", "acnh_shoe", "acnh_sock", "acnh_top", "acnh_other_clothing")
        val ACNH_ALL_FURNITURE_TABLES = arrayOf("acnh_houseware", "acnh_misc", "acnh_wall_mounted")
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "create_db.sql")
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

        setupPreferences(db)
    }

    //used exclusively by developer to fix sql bugs
    fun recreate() {
        val db = this.writableDatabase
        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "dev.sql")
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
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "update1.sql")
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
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "update2.sql")
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
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "update3.sql")
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

    fun update4(db: SQLiteDatabase) {

        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "update4.sql")
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

    fun update5(db: SQLiteDatabase) {

        val sqlReader = SQLReader()
        var sqlStr = sqlReader.getSQLDataFromAssets(context, "update5.sql")
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

    fun executeSQLFromFile(sqlStr: String) {

        val db = this.writableDatabase
        val newStr = sqlStr.replace(";", ";\n")
        val sqlArr = newStr.split("\n")
        var lineNo = 1
        var errNo = 0

        for (str in sqlArr) {
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
        if (oldVersion < 5) {
            update4(db)
        }
        if (oldVersion < 6) {
            update5(db)
        }

        setupPreferences(db)
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

    fun getCursorData(tableName: String, db: SQLiteDatabase): Cursor {
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
            return myDataset.sortedWith(compareBy { it["Name"]?.toLowerCase()!!.replace("-", " ") }).toMutableList()
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
            return myDataset.sortedWith(compareBy { it["Name"]?.toLowerCase()!!.replace("-", " ") }).toMutableList()
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
            if (tableName.contains("acnh_") && !useSeasonData())
                return myDataset.sortedWith(compareBy { it["Name"]?.toLowerCase()!!.replace("-", " ") }).toMutableList()
            else
                return myDataset
        }
    }

    fun getFromData(tableName: String): List<String> {
        val db = this.readableDatabase
        val myDataset = emptySet<String>().toMutableSet()

        if (tableName == "acnh_all_clothing") {

            for (table in ACNH_ALL_CLOTHING_TABLES) {
                val cursor = getFromCursorData(table, db)

                cursor.moveToFirst()
                do {
                    for (column in cursor.columnNames) {
                        myDataset.add(cursor.getString(cursor.getColumnIndex(column)))
                    }
                } while (cursor.moveToNext())

                cursor.close()
            }
        }
        else if (tableName == "acnh_all_furniture") {

            for (table in ACNH_ALL_FURNITURE_TABLES) {
                val cursor = getFromCursorData(table, db)

                cursor.moveToFirst()
                do {
                    for (column in cursor.columnNames) {
                        myDataset.add(cursor.getString(cursor.getColumnIndex(column)))
                    }
                } while (cursor.moveToNext())

                cursor.close()
            }
        }
        else {
            val cursor = getFromCursorData(tableName, db)

            cursor.moveToFirst()
            do {
                for (column in cursor.columnNames) {
                     myDataset.add(cursor.getString(cursor.getColumnIndex(column)))
                }
            } while (cursor.moveToNext())

            cursor.close()
        }

        return myDataset.toMutableList().sortedWith(compareBy { it.toLowerCase().replace("-", " ") })
    }

    fun getFromCursorData(tableName: String, db: SQLiteDatabase): Cursor {
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select distinct \"From\" from $tableName;", null)
        }
        catch(e: Throwable) {
            println("Error getting cursor data: $e")
        }
        return cursor!!
    }

    fun getSeasonData(gameName: String, tableName: String, season: String): MutableList<MutableMap<String, String>> {
        val cursor = getCursorData(gameName, tableName, season)
        val myDataset = emptyList<MutableMap<String, String>>().toMutableList()

        var nextMonthIndex = MainActivity.selectedSeasonIndex + 1
        var previousMonthIndex = MainActivity.selectedSeasonIndex - 1

        if (nextMonthIndex >= MainActivity.seasonValues.size) {
            nextMonthIndex = 1
        }
        if (previousMonthIndex < 1) {
            previousMonthIndex = MainActivity.seasonValues.size - 1
        }

        val nextMonthIndexes = getMonthIndexes(gameName, tableName, MainActivity.seasonValues[nextMonthIndex])
        val previousMonthIndexes = getMonthIndexes(gameName, tableName, MainActivity.seasonValues[previousMonthIndex])

        cursor.moveToFirst()
        do {
            var map = emptyMap<String, String>().toMutableMap()
            for (column in cursor.columnNames) {
                map[column] = cursor.getString(cursor.getColumnIndex(column))
            }
            map["Type"] = gameName + tableName
            map["GoneNextMonth"] = if (!nextMonthIndexes.contains(map["Index"])) "true" else "false"
            map["GonePreviousMonth"] = if (!previousMonthIndexes.contains(map["Index"])) "true" else "false"
            myDataset.add(map)
        } while (cursor.moveToNext())

        cursor.close()
        return myDataset
    }

    fun getMonthIndexes(gameName: String, tableName: String, season: String) : MutableList<String> {
        val cursor = getCursorData(gameName + season + tableName)
        val myDataset = emptyList<String>().toMutableList()
        cursor.moveToFirst()
        do {
            for (column in cursor.columnNames) {
                myDataset.add(cursor.getString(cursor.getColumnIndex(column)))
            }
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

    fun getTableData(tableName: String, db: SQLiteDatabase): MutableList<String> {
        val cursor = getCursorData(tableName, db)
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

    fun backupData(): String {
        val db = this.readableDatabase
        var buffer = StringBuffer()

        //acgc backup
        for (table in MainActivity.tableDisplayACGC) {
            val tableName = ("acgc_" + table).replace(" ", "_")
            if (tableName.contains("_all_"))
                continue
            val backupList = getBackupData(tableName)

            for (writeData in backupList) {
                buffer.append("update $tableName set \"Selected\" = 1 where \"Index\" = $writeData;\n")
            }
        }
        //acww backup
        for (table in MainActivity.tableDisplayACWW) {
            val tableName = ("acww_" + table).replace(" ", "_")
            if (tableName.contains("_all_"))
                continue
            val backupList = getBackupData(tableName)

            for (writeData in backupList) {
                buffer.append("update $tableName set \"Selected\" = 1 where \"Index\" = $writeData;\n")
            }
        }
        //accf backup
        for (table in MainActivity.tableDisplayACCF) {
            val tableName = ("accf_" + table).replace(" ", "_")
            if (tableName.contains("_all_"))
                continue
            val backupList = getBackupData(tableName)

            for (writeData in backupList) {
                buffer.append("update $tableName set \"Selected\" = 1 where \"Index\" = $writeData;\n")
            }
        }
        //acnl backup
        for (table in MainActivity.tableDisplayACNL) {
            val tableName = ("acnl_" + table).replace(" ", "_")
            if (tableName.contains("_all_"))
                continue
            val backupList = getBackupData(tableName)

            for (writeData in backupList) {
                buffer.append("update $tableName set \"Selected\" = 1 where \"Index\" = $writeData;\n")
            }
        }
        //acnh backup
        for (table in MainActivity.tableDisplayACNH) {
            val tableName = ("acnh_" + table).replace(" ", "_")
            if (tableName.contains("_all_"))
                continue
            val backupList = getBackupData(tableName)

            for (writeData in backupList) {
                buffer.append("update $tableName set \"Selected\" = 1 where \"Index\" = $writeData;\n")
            }
        }
        return buffer.toString()
    }

    fun getBackupData(tableName: String): MutableList<String> {
        val myDataset = emptyList<String>().toMutableList()
        val cursor = getBackupCursorData(tableName)

        cursor.moveToFirst()
        do {
            if (cursor.count > 0) {
                myDataset.add(cursor.getString(0))
            }
        } while (cursor.moveToNext())

        cursor.close()
        return myDataset
    }

    fun getBackupCursorData(tableName: String): Cursor {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select \"Index\" from $tableName where \"Selected\" = 1;", null)
        }
        catch(e: Throwable) {
            println("Error getting cursor data: $e")
        }
        return cursor!!
    }

    private fun setupPreferences(db: SQLiteDatabase) {
        val sqlStrList = emptyList<String>().toMutableList()
        val existingPreferencesTables = getPreferencesTables(db)
        val allTables = getAllTables(db)

        for (table in allTables) {
            if (!existingPreferencesTables.contains(table))
                sqlStrList.add("insert into preferences values ('$table', 0, '');")
        }

        if (sqlStrList.isNotEmpty()) {
            for (sqlStr in sqlStrList)
                db.execSQL(sqlStr)
        }
    }

    fun getPreferencesData(): MutableList<MutableMap<String, String>> {
        val db = this.readableDatabase
        val myDataset = emptyList<MutableMap<String, String>>().toMutableList()
        val cursor = getPreferencesCursorData(db)

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

    fun getPreferencesTables(db: SQLiteDatabase): MutableList<String> {
        val myDataset = emptyList<String>().toMutableList()
        val cursor = getPreferencesCursorData(db)

        cursor.moveToFirst()
        do {
            if (cursor.count == 0)
                break
            val tableName = cursor.getString(0)
            myDataset.add(tableName)
        } while (cursor.moveToNext())

        cursor.close()
        return myDataset
    }

    fun getPreferencesCursorData(db: SQLiteDatabase): Cursor {
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from preferences;", null)
        }
        catch(e: Throwable) {
            println("Error getting cursor data: $e")
        }
        return cursor!!
    }

    fun setPrefs(tableName: String, from: String, selectedFilter: String): MutableMap<String, String>? {
        val db = this.writableDatabase
        val sqlStr = ("update preferences set \"from\" = '${from.replace("'", "''")}', selected_filter = $selectedFilter where table_name = '$tableName';")
        db.execSQL(sqlStr)

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from preferences where table_name = '$tableName';", null)
        }
        catch(e: Throwable) {
            println("Error getting cursor data: $e")
        }

        val map = emptyMap<String, String>().toMutableMap()

        cursor?.moveToFirst()
        if (cursor == null || cursor.count == 0)
            return null

        for (column in cursor.columnNames) {
            map[column] = cursor.getString(cursor.getColumnIndex(column))
        }

        cursor.close()
        return map
    }

    fun getAllTables(db: SQLiteDatabase): MutableList<String> {
        val tableList = emptyList<String>().toMutableList()

        val tableDisplayACGC = getTableData(MainActivity.gameValues[0] + "table", db).toTypedArray()
        val tableDisplayACWW = getTableData(MainActivity.gameValues[1] + "table", db).toTypedArray()
        val tableDisplayACCF = getTableData(MainActivity.gameValues[2] + "table", db).toTypedArray()
        val tableDisplayACNL = getTableData(MainActivity.gameValues[3] + "table", db).toTypedArray()
        val tableDisplayACNH = getTableData(MainActivity.gameValues[4] + "table", db).toTypedArray()

        //acgc
        for (table in tableDisplayACGC) {
            val tableName = ("acgc_" + table).replace(" ", "_")
            tableList.add(tableName)
        }
        //acww
        for (table in tableDisplayACWW) {
            val tableName = ("acww_" + table).replace(" ", "_")
            tableList.add(tableName)
        }
        //accf
        for (table in tableDisplayACCF) {
            val tableName = ("accf_" + table).replace(" ", "_")
            tableList.add(tableName)
        }
        //acnl
        for (table in tableDisplayACNL) {
            val tableName = ("acnl_" + table).replace(" ", "_")
            tableList.add(tableName)
        }
        //acnh
        for (table in tableDisplayACNH) {
            val tableName = ("acnh_" + table).replace(" ", "_")
            tableList.add(tableName)
        }
        return tableList
    }
}