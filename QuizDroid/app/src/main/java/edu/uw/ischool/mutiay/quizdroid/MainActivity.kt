package edu.uw.ischool.mutiay.quizdroid

import QuizData
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var quizs: ListView
    private val values = listOf("Math", "Physics", "Marvel Super Heroes", "ice cream")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        quizs= findViewById(R.id.quizListview)
        val adapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, values)
        quizs.adapter = adapter

        quizs.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedTopic = values[position]



            val intent = Intent(this, TopicOverViewActivity::class.java)
            intent.putExtra("TOPIC_NAME", selectedTopic)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

            }
        }

    }
}