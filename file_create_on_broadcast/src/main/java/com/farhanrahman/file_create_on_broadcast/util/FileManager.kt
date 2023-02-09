package com.farhanrahman.file_create_on_broadcast.util

import android.app.Activity
import android.content.Context
import android.os.Environment
import com.farhanrahman.file_create_on_broadcast.R
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

object FileManager {
    var dir: File? =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    fun createFile(context: Context):File? {
        try {
            val file = File(dir, "finished_tasked_on"+DateUtility.getTimeInString(
                timesInMillis = Calendar.getInstance().timeInMillis).toString() +".txt")
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        } catch (e: IOException) {
            DialogueUtil.generalDialogue(context,
                context.getString(R.string.error),context.getString(R.string.cannot_create_file))
        }
        return null
    }
    fun writeFile(context: Context,file: File?){
        try {
            file?.let {
                FileWriter(file).use { fileWriter -> fileWriter.append("Writing to file!") }
            }

        } catch (e: IOException) {
            DialogueUtil.generalDialogue(context,
                context.getString(R.string.error),context.getString(R.string.cannot_write_file))
        }
    }

}