package com.farhanrahman.file_create_on_broadcast.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.CreationExtras
import com.farhanrahman.file_create_on_broadcast.R


object PermissionUtil {
    const val REQUEST_WRITE_PERMISSION = 200
    // Requesting permission to RECORD_AUDIO
    var permissionToWriteAccepted = false
    var REQUEST_TIME = 2
    val permissions: Array<String?> =
        mutableListOf(
            if (Build.VERSION.SDK_INT
                <= Build.VERSION_CODES.Q
            ) {
                Manifest.permission.WRITE_EXTERNAL_STORAGE} else { null}
        ).filter { !it.isNullOrEmpty()}.toTypedArray()

    fun requestPermission(activity: Activity) {
        if(permissions.isNotEmpty()){
            ActivityCompat.requestPermissions(
                activity, permissions,
                REQUEST_WRITE_PERMISSION
            )
        }

    }

    fun checkPermissionResult(
        activity: Activity,
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_WRITE_PERMISSION) {
            checkAppPermission(activity, requestCode, permissions, grantResults)
        }
    }


    private fun checkAppPermission(
        activity: Activity,
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionToWriteAccepted = if (requestCode == REQUEST_WRITE_PERMISSION
            && grantResults.isNotEmpty()
        ) {
            grantResults.all {
                it == PackageManager.PERMISSION_GRANTED
            }
        } else {
            false
        }
        if (!permissionToWriteAccepted) {
            if (REQUEST_TIME <= 0) {
                DialogueUtil.generalDialogue(
                    activity,
                    activity.getString(R.string.permission),
                    activity.getString(R.string.provide_permission)
                ) { _, _ ->
                    ActivityCompat.requestPermissions(
                        activity, permissions,
                        REQUEST_WRITE_PERMISSION
                    )
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", activity.packageName, null)
                    intent.data = uri
//                      This will take the user to a page where they have to click twice to drill down to grant the permission
                    activity.startActivity(intent)
                }
            } else {
                REQUEST_TIME -= 1
                ActivityCompat.requestPermissions(
                    activity, permissions,
                    REQUEST_WRITE_PERMISSION
                )
            }
        }else{
            FileManager.writeFile(activity,FileManager.createFile(activity))
        }

    }

}