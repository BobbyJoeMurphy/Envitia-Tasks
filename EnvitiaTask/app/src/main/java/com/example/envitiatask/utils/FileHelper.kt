package com.example.envitiatask.utils

import java.io.File
import android.content.Context

object FileHelper {
    private const val fileName = "log.txt"

    fun writeToFile(context: Context, content: String) {
        val file = File(context.filesDir, fileName)
        file.appendText("$content\n")
    }

    fun readFromFile(context: Context): String {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            file.readText()
        } else {
            ""
        }
    }

    fun clearFile(context: Context) {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            file.writeText("")
        }
    }
}
