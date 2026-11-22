package dev.tomco.a27a_10208_l05.utilities

import android.content.Context
import java.io.File

object FileManager {
    fun writeText(context: Context, fileName: String, text: String) {
        context.openFileOutput(
            fileName,
            Context.MODE_PRIVATE
        ).use {
            stream -> stream.write(text.toByteArray())
        }
    }

    fun readText(context: Context, fileName: String): String? {
        val file = File(context.filesDir,fileName)
        if (!file.exists())
            return null
        return context.openFileInput(fileName)
            .bufferedReader().use{
                reader -> reader.readText()
            }
    }
}