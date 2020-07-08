package com.abw4v.accatalog

import android.content.Context
import java.io.*
import java.nio.charset.Charset
import java.lang.Exception


class SQLReader {

    fun getSQLDataFromAssets(context: Context, filename: String): String? {
        var str: String? = null

        try {
            val `is` = context.assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            str = String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return str
    }

    fun getSQLDataFromStorage(context: Context, filename: String): String? {
        var str: String? = null

        try {

            val file = File(filename)
            val fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                sb.append(line + '\n')
                line = bufferedReader.readLine()
            }
            str = sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

        return str
    }

}