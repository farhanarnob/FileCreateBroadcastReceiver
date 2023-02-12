package com.farhanrahman.file_create_on_broadcast.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.farhanrahman.file_create_on_broadcast.R
import java.io.File
import java.io.IOException
import java.util.*

object FileManager {
    fun createFile(context: Context):File? {
        try {
            val dir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "")
            dir.mkdir()
            val file = File(dir,
                "Coverage"+".ec")
            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        } catch (e: IOException) {
            DialogueUtil.generalDialogue(context,
                context.getString(R.string.cannot_create_file),e.message.toString())
        }
        return null
    }
    fun writeFile(context: Context,file: File?){
        try {
            file?.let {

                val emmaRTClass = Class.forName("com.vladium.emma.rt.RT")
                val dumpCoverageMethod = emmaRTClass.getMethod(
                    "dumpCoverageData", file.javaClass,
                    Boolean::class.javaPrimitiveType,
                    Boolean::class.javaPrimitiveType
                )
                dumpCoverageMethod.invoke(null, file, true, false)
                Toast.makeText(context, "File created", Toast.LENGTH_SHORT).show()
            }

        } catch (e: IOException) {
            DialogueUtil.generalDialogue(context,
                context.getString(R.string.cannot_write_file),e.message.toString())
        }
    }

}