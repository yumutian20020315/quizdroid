package edu.uw.ischool.mutiay.quizdroid

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class Setting : AppCompatActivity() {
    private lateinit var url:EditText
    private lateinit var minute_download:EditText
    private lateinit var confirm:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        url = findViewById(R.id.urlInput)
        minute_download = findViewById(R.id.minutesInput)
        confirm = findViewById(R.id.confirm_button)


        confirm.setOnClickListener {
            var url_for_download = url.text.toString()
            var minutes = minute_download.text.toString()

            val prefs = this.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            val prefsEditor = prefs.edit()

            prefsEditor.putString("url_download", url_for_download)
            Log.i("test", url_for_download)
            Log.i("test", minutes)
            prefsEditor.putString("minutes_between_download", minutes)
            prefsEditor.apply()
        }

//        back_main.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//
//            try {
//                startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//
//            }
//        }











    }
}