package com.example.hinotes.preferences

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import com.example.hinotes.R
import com.example.hinotes.alarmmanager.AlarmService
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var pref: SwitchPreferenceCompat
//    private lateinit var preferenceInfo: Preference
    lateinit var alarmService: AlarmService
//    private val PREFS_NAME = "kotlinAlarm"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        pref = findPreference("alarm")!!
        alarmService = AlarmService(context as Activity)

        pref.setOnPreferenceChangeListener(Preference.OnPreferenceChangeListener { preference, newValue ->

            if (newValue as Boolean) {
                showTimePicker {
                    alarmService.setRepetitiveAlarm(it)
                    pref.isChecked = true
                }
                Toast.makeText(activity, "Alarm Hidup", Toast.LENGTH_SHORT).show()
            } else {
                pref.isChecked = false
                val alarmManager: AlarmManager = (context as Activity).getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context as Activity, AlarmService::class.java)
                var pendingIntent: PendingIntent = PendingIntent.getService(context as Activity, 0, intent, 0)
                alarmManager.cancel(pendingIntent)
                Toast.makeText(activity, "Alarm Mati", Toast.LENGTH_SHORT).show()
            }
            true
        })

    }
    private fun showTimePicker(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                context as Activity,
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        context as Activity,
                        0,
                        { _, hour, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
//            preferenceInfo = findPreference("alarm_time")!!
//            val alarmManager: AlarmManager = (context as Activity).getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            var timedText = "Alarm set for: " + alarmManager.nextAlarmClock
//            preferenceInfo.setTitle(timedText)
        }
    }
}