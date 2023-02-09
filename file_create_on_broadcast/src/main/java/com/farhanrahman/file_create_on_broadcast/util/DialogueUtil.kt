package com.farhanrahman.file_create_on_broadcast.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.farhanrahman.file_create_on_broadcast.R

object DialogueUtil {
    fun generalDialogue(
        context: Context,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener? = null
    ): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.okay), listener)
            .setCancelable(false)
            .show()
    }
}