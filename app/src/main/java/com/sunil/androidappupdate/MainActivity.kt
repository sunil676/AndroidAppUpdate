package com.sunil.androidappupdate

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var currentAppVersion: String
    private lateinit var playStoreAppVersion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_remote.setOnClickListener(View.OnClickListener {

            currentAppVersion = MainApplication.getCurrentAppVersion(this)
            playStoreAppVersion = FirebaseRemoteConfig.getInstance().getString(MainApplication.parameterKey)

            Toast.makeText(this, "App version: $currentAppVersion"+ " PlayStore version: $playStoreAppVersion", Toast.LENGTH_LONG).show()

            if (isUpdateAvailable()) {
                //stuff for redirecting user to play store

                val alertDialog = AlertDialog.Builder(this).setTitle("App update is available v: $playStoreAppVersion")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { p0, p1 -> finish() }.create()
                alertDialog.show()
            }
        })

        button_flex.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, FlexibleUpdateActivity::class.java))
        })
        button_imme.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ImmediateUpdateActivity::class.java))

        })

        button_theme.setOnClickListener(View.OnClickListener {
            // either dynamically can change the theme or static can be use by any one theme from the style.xml
            if(isNightModeOn()){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                recreate()
            }
        })
    }

    private fun isUpdateAvailable(): Boolean {
        return !playStoreAppVersion.equals(currentAppVersion, true)
    }

    private fun isNightModeOn() : Boolean{
        var mode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                Log.e("TAG","Config Changes No night")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                Log.e("TAG","Config Changes Yes Night")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                recreate()
            } // 0Night mode is active, we're using dark theme
        }
    }
}
