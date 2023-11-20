package edu.uw.ischool.mutiay.quizdroid

import android.app.AlarmManager
import android.app.DownloadManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

const val ALARM_ACTION = "edu.uw.ischool.mutiay.ALARM"
class Setting : AppCompatActivity() {
    private lateinit var url:EditText
    private lateinit var minute_download:EditText
    private lateinit var confirm:Button
    private lateinit var error:TextView
    private lateinit var airmode:Button
    var receiver : BroadcastReceiver? = null
    private var isDownloading = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        url = findViewById(R.id.urlInput)
        minute_download = findViewById(R.id.minutesInput)
        confirm = findViewById(R.id.confirm_button)


        confirm.setOnClickListener {
            if (isDownloading) {
                Toast.makeText(this, "Waiting for current download to finish...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Unregister the previous one
            if(receiver !== null) {
                unregisterReceiver(receiver)
                receiver = null
            }


            var url_for_download = url.text.toString()
            var minutes = minute_download.text.toString()

            val prefs = this.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            val prefsEditor = prefs.edit()

            prefsEditor.putString("url_download", url_for_download)
            Log.i("test", url_for_download)
            Log.i("test", minutes)
            prefsEditor.putString("minutes_between_download", minutes)
            prefsEditor.apply()

            // Check the status of user Internet connection
            val status = checkStatus()
            if (status) {
                download()
            }
        }
    }

    private fun checkStatus():Boolean {
        error = findViewById(R.id.error)
        airmode = findViewById(R.id.airmode_button)

        // Check internet
        val context: Context = this
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isConnected = networkInfo?.isConnectedOrConnecting == true

        // Check airplane
        val isAirplaneModeOn = Settings.Global.getInt(context.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0

        if (!isConnected) {
            if (isAirplaneModeOn) {
                error.setText("Do you want to turn airplane mode off?")
                airmode.isEnabled = true
                airmode.setOnClickListener {
                    // 显示消息并提供打开设置的选项
                    val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                    context.startActivity(intent)
                }


            } else {
                // 显示无信号的错误消息
                error.setText("No Internet connection available")
                Toast.makeText(context, "No Internet connection available", Toast.LENGTH_LONG).show()
            }
            return false
        }
        Toast.makeText(context, "All status check good", Toast.LENGTH_LONG).show()
        return true
    }


    private fun download() {

        // Get shared preference
        val prefs = this.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)

        val url = prefs.getString("url_download", "Default URL")
        val interval = prefs.getString("minutes_between_download", "Default Minutes")


        if (receiver == null) {
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    startDownload(url)

                }
            }
            val filter = IntentFilter(ALARM_ACTION)
            registerReceiver(receiver, filter)
        }

        // Create the PendingIntent
        val intent = Intent(ALARM_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Get the Alarm Manager
        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var miliInterval = ((interval?.toInt() ?: 0) * 60000).toLong()
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+miliInterval, miliInterval, pendingIntent)

    }

    fun startDownload(url:String?) {
        if (isDownloading) {
            Toast.makeText(this, "Download in progress: $url, please wait...", Toast.LENGTH_SHORT).show()
            return
        }
        isDownloading = true

        val context: Context = this
        runOnUiThread {
            Toast.makeText(context, "Download attempt happen: $url", Toast.LENGTH_LONG).show()
            Log.i("download", "start to download")
        }

        val executor : Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            // Everything in here is now on a different (non-UI)
            // thread and therefore safe to hit the network
            try {
                val urll = URL(url)
                val connection = urll.openConnection() as HttpURLConnection
                connection.connect()

                val inputStream = connection.inputStream
                val file = File(context.filesDir, "questions.json")

                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                inputStream.close()
                runOnUiThread {

                    error.setText("Download succeed")
                    Log.i("download", "download finish")
                    confirm.setText("Download")
                }
                isDownloading = false
            } catch (e: Exception) {
                Log.e("DownloadError", "Error during download", e)
                runOnUiThread {

                    error.setText("Download failed. Do your want to retry or quit the application and try again later?")
                    Log.i("download", "fail to download")
                    confirm.setText("Retry")
                }
                isDownloading = false
            }
        }

    }



}