package com.capstone.ecosortbin.ui.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.capstone.ecosortbin.R

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val buttonChangeTheme = findViewById<Switch>(R.id.buttonChangeTheme)
        val buttonLanguage = findViewById<Button>(R.id.buttonLanguage)
        val buttonLogout = findViewById<Button>(R.id.buttonLogout)

        buttonChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                buttonChangeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                buttonChangeTheme.isChecked = false
            }
        }


        buttonLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
        }

        buttonLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            true
        }
    }
}