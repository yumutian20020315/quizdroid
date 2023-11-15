package edu.uw.ischool.mutiay.quizdroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

const val ALARM_ACTION = "edu.uw.ischool.mutiay.ALARM"
class Setting : AppCompatActivity() {
    private lateinit var url:EditText
    private lateinit var minute_download:EditText
    private lateinit var confirm:Button
    var receiver : BroadcastReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        url = findViewById(R.id.urlInput)
        minute_download = findViewById(R.id.minutesInput)
        confirm = findViewById(R.id.confirm_button)


        confirm.setOnClickListener {
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

            if (receiver == null) {
                receiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        Toast.makeText(context, "Download attempt happen: $url_for_download", Toast.LENGTH_LONG).show()
                        Log.i("download", "start to download")

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

            var miliInterval = ((minutes.toInt() ?: 0) * 60000).toLong()
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+miliInterval, miliInterval, pendingIntent)
        }

    }
}