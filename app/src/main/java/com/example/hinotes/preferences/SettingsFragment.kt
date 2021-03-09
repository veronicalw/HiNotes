package com.example.hinotes.preferences

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.hinotes.R
import com.example.hinotes.alarmmanager.AlarmService
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var preference: Preference
    lateinit var alarmService: AlarmService
    private val PREFS_NAME = "kotlinAlarm"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        preference = findPreference("alarm")!!
        preference.setOnPreferenceChangeListener { buttonView, isChecked ->
            if (isChecked as Boolean){
                alarmService = AlarmService(context as Activity)
                val alertDialog = AlertDialog.Builder(context as Activity).setTitle("Schedule your alarm")
                        .setMessage("You're going to set alarm for daily writing notes")
                        .setPositiveButton("Set Alarm", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                preference.setDefaultValue(true)
                                showTimePicker {
                                    alarmService.setRepetitiveAlarm(it)
                                }
                            }

                        })
                        .setNegativeButton("Cancel", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                preference.setDefaultValue(false)
                                p0?.dismiss()
                            }
                        })
                alertDialog.show()
            } else {
                val alertDialogSwitch = AlertDialog.Builder(context as Activity).setTitle("Turn off Alarm")
                        .setMessage("are you sure ?")
                        .setPositiveButton("Yes", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                preference.setDefaultValue(true)
                                val alarmManager: AlarmManager = (context as Activity).getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                val intent = Intent(context as Activity, AlarmService::class.java)
                                var pendingIntent: PendingIntent = PendingIntent.getService(context as Activity,0,intent,0)
                                alarmManager.cancel(pendingIntent)
                            }
                        })
                        .setNegativeButton("No", object : DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                preference.setDefaultValue(false)
                                p0?.dismiss()
                            }
                        })
                alertDialogSwitch.show()
            }
            true
        }}

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
        }
    }
}