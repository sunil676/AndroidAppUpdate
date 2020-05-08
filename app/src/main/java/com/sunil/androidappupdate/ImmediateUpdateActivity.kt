package com.sunil.androidappupdate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.android.synthetic.main.activity_flex.*

class ImmediateUpdateActivity : AppCompatActivity(){

    private var inAppUpdateManager: AppUpdateManager? = null
    private var IMMEDIATE_UPDATE_REQUEST = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flex)
        setInAppUpdateListeners()
    }

    private fun setInAppUpdateListeners() {
        inAppUpdateManager = AppUpdateManagerFactory.create(this)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoIntent = inAppUpdateManager?.appUpdateInfo
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoIntent?.addOnSuccessListener { appUpdateInfo ->
            // checks if the update is available or not
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)
            ) {
                inAppUpdateManager?.startUpdateFlowForResult(
                    appUpdateInfo, IMMEDIATE,
                    this, IMMEDIATE_UPDATE_REQUEST
                )
            }else {
                textView.text = resources.getString(R.string.no_udate_available)
            }
        }
        appUpdateInfoIntent?.addOnFailureListener {
            textView.text = "Error " +it.localizedMessage

            // Please check the app you are testing has the same package name which is available on the play store.
            // You have an app on the play store with package name com.example.app but you are testing your app with
            // package name com.example.app.debug. You will get this error: ERROR_API_NOT_AVAILABLE
        }
    }

    override fun onResume() {
        super.onResume()
        inAppUpdateManager
            ?.appUpdateInfo
            ?.addOnSuccessListener { appUpdateInfo ->
                Log.e("appUpdateInfo", "OnResume")
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    inAppUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo,
                        IMMEDIATE,
                        this,
                        IMMEDIATE_UPDATE_REQUEST
                    )
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMMEDIATE_UPDATE_REQUEST) {
            if (resultCode != RESULT_OK) {
                finish()
                // update dialog is cancelled by user or fails, it should be handled as per as requirement
                // you can close the app or you can request to start the update again.
            }
        }
    }
}