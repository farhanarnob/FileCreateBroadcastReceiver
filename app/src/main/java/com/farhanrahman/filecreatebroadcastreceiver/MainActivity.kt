package com.farhanrahman.filecreatebroadcastreceiver

import android.content.IntentFilter
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.farhanrahman.file_create_on_broadcast.service.CustomBroadcastReceiverName
import com.farhanrahman.file_create_on_broadcast.service.FileBroadcastReceiver
import com.farhanrahman.file_create_on_broadcast.util.FileManager
import com.farhanrahman.file_create_on_broadcast.util.PermissionUtil
import com.farhanrahman.filecreatebroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val fileBroadcastReceiver = FileBroadcastReceiver()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

//        Check permission
        PermissionUtil.requestPermission(this)
        if(PermissionUtil.permissions.isEmpty()){
            FileManager.writeFile(this, FileManager.createFile(this))
        }

        registerReceiver(fileBroadcastReceiver, IntentFilter(CustomBroadcastReceiverName.com_context_FINISH_TESTING.stringName))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode != RESULT_CANCELED) {
            PermissionUtil.checkPermissionResult(this, requestCode, permissions, grantResults)
        }
    }

    override fun onStop() {
        super.onStop()
        if( PermissionUtil.permissionToWriteAccepted){
            FileManager.createFile(this)?.let {
                FileManager.writeFile(this,it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(fileBroadcastReceiver)
    }
}