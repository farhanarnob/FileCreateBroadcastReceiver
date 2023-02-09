package com.farhanrahman.file_create_on_broadcast.service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.farhanrahman.file_create_on_broadcast.util.FileManager

class FileBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(CustomBroadcastReceiverName.com_context_FINISH_TESTING.stringName)){
            if (context != null) {
                FileManager.createFile(context)?.let {
                    FileManager.writeFile(context,it)
                }
            }
        }
    }


}