package com.example.hinotes.html

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.chinalwb.are.Util
import com.chinalwb.are.styles.toolbar.ARE_Toolbar
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object DemoUtil {
    @SuppressLint("SimpleDateFormat")
    fun saveHtml(activity: Activity, html: String?) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                //申请授权
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    ARE_Toolbar.REQ_VIDEO
                )
                return
            }
            val filePath: String =
                Environment.getExternalStorageDirectory().toString() + File.separator.toString() + "ARE" + File.separator
            val dir = File(filePath)
            if (!dir.exists()) {
                dir.mkdir()
            }
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd_hh_mm_ss")
            val time: String = dateFormat.format(Date())
            val fileName = "$time.html"
            val file = File(filePath + fileName)
            if (!file.exists()) {
                val isCreated: Boolean = file.createNewFile()
                if (!isCreated) {
                    Util.toast(activity, "Cannot create file at: $filePath")
                    return
                }
            }
            val fileWriter : FileWriter = FileWriter(file)
            fileWriter.write(html)
            fileWriter.close()
            Util.toast(activity, "$fileName has been saved at $filePath")
        } catch (e: IOException) {
            e.printStackTrace()
            Util.toast(activity, "Run into error: " + e.message)
        }
    }
}