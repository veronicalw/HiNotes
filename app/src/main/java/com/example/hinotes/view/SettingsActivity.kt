package com.example.hinotes.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.hinotes.R
import com.example.hinotes.alarmmanager.AlarmService
import com.example.hinotes.preferences.SettingsFragment
import java.util.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout

    lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = "Settings"

        alarmService = AlarmService(this)

        frameLayout = findViewById(R.id.frameLayoutPref)

        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment())
            .commit()

    }
}