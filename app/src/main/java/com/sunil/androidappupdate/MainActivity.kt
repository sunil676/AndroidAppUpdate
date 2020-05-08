package com.sunil.androidappupdate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    }
}
