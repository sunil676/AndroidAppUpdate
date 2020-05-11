package com.sunil.androidappupdate

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
