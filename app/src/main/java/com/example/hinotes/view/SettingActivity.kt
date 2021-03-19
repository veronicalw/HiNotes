package com.example.hinotes.view

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.hinotes.R
import com.example.hinotes.alarmmanager.AlarmService
import java.sql.Time
import java.util.*


class SettingActivity : AppCompatActivity() {
    private lateinit var switch: Switch
    private lateinit var textAlarm: TextView
    lateinit var alarmService: AlarmService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        switch = findViewById(R.id.switchWidget)

        switch.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked){
                alarmService = AlarmService(this)
                val alertDialog = AlertDialog.Builder(this).setTitle("Schedule your alarm")
                    .setMessage("You're going to set alarm for daily writing notes")
                    .setPositiveButton("Set Alarm", object : DialogInterface.OnClickListener{
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            showTimePicker {
                                alarmService.setRepetitiveAlarm(it)
                            }
                            switch.isChecked = true
                        }
                    })
                alertDialog.show()
            } else if (isChecked == true){

                val alertDialogSwitch = androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Turn off Alarm")
                    .setMessage("are you sure ?")
                    .setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            switch.isChecked = false
                            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            val intent = Intent(this@SettingActivity, AlarmService::class.java)
                            var pendingIntent: PendingIntent = PendingIntent.getService(this@SettingActivity, 0, intent, 0)
                            alarmManager.cancel(pendingIntent)
                            alarmService.cancelAlarm(pendingIntent)
                        }
                    })
                    .setNegativeButton("No", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            switch.isChecked = true
                            p0?.dismiss()
                        }
                    })
                alertDialogSwitch.show()
            }
            true
        }
    }

    private fun showTimePicker(callback: (Long) -> Unit) {
        var calendar = Calendar.getInstance()
        textAlarm = findViewById(R.id.txtAlarm)
        val sharedPref: SharedPreferences = this?.getSharedPreferences("ALARM SET", Context.MODE_PRIVATE)!!
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        var minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(timepicker: TimePicker?, hourSelected: Int, minuteSelected: Int) {
                calendar.set(Calendar.HOUR_OF_DAY, hourSelected)
                calendar.set(Calendar.MINUTE, minuteSelected)
                var editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putInt("hours", hourSelected)
                editor.putInt("minutes", minuteSelected)
                Log.d("ALARM SET ","Hour " + hourSelected + " Minute " + minuteSelected)
                editor.commit()
                textAlarm.setText("Alarm is set in : " + hourSelected + " : " + minuteSelected)
            }
        }, hour, minute, false)
        timePickerDialog.show()
    }
}