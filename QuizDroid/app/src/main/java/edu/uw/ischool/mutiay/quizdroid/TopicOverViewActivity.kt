package edu.uw.ischool.mutiay.quizdroid

import QuizData
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class TopicOverViewActivity : AppCompatActivity() {
    private lateinit var topicDescribe: TextView
    private lateinit var questionNum: TextView
    private lateinit var beginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_over_view)

        topicDescribe = findViewById(R.id.topicDescribe)
        questionNum = findViewById(R.id.questionNum)
        beginBtn = findViewById(R.id.begin)

        val topicName = intent.getStringExtra("TOPIC_NAME")

        val topicQuestionsCount = QuizData.topics.find { it.name == topicName }?.questions?.size ?: 0
        // Initialize the scoreStatus to all false
        QuizData.scoreStatus = MutableList(topicQuestionsCount) { false }

        topicDescribe.setText(QuizData.topics.find { it.name == topicName }?.description)
        questionNum.setText((QuizData.topics.find { it.name == topicName }?.questions?.size ?: 0).toString() + " questions")

        // deal with the test shown
//        when(topicName) {
//            "Math" -> topicDescribe.setText("Some quick questions on calculation")
//            "Physics" -> topicDescribe.setText("Some quick questions on basic physics")
//            "Marvel Super Heroes" -> topicDescribe.setText("Some quick questions on Marval Super Heros")
//            "ice cream" -> topicDescribe.setText("Some quick questions on ice cream")
//        }
//
//        when(topicName) {
//            "Math" -> questionNum.setText("3 questions")
//            "Physics" -> questionNum.setText("3 questions")
//            "Marvel Super Heroes" -> questionNum.setText("2 questions")
//            "ice cream" -> questionNum.setText("2 questions")
//        }

        // Direct to questions activity
        beginBtn.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra("TOPIC_NAME", topicName)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

            }
        }


    }
}