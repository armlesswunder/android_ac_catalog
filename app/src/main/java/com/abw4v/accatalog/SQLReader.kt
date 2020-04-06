package com.abw4v.accatalog

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

class SQLReader {

    fun getSQLData(context: Context, filename: String): String? {
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

}