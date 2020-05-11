package com.sunil.androidappupdate

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val remoteConfigMap = mutableMapOf<String, String>()
        remoteConfigMap[parameterKey] = getCurrentAppVersion(this)

        remoteConfig.setDefaults(remoteConfigMap as Map<String, Any>?)
        remoteConfig.fetch()
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    remoteConfig.activateFetched()
            }
    }

    companion object {
        val parameterKey = "app_version"
        fun getCurrentAppVersion(context: Context): String {
            try {
                return context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}