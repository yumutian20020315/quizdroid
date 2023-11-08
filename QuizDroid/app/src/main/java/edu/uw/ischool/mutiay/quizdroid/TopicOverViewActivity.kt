package edu.uw.ischool.mutiay.quizdroid

import QuizData
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class TopicOverViewActivity : AppCompatActivity() {


    // Find UI
    private lateinit var topicDescribe: TextView
    private lateinit var questionNum: TextView
    private lateinit var beginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        // Get Repo in
         val repo = (application as QuizApp).accessRepo()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_over_view)

        topicDescribe = findViewById(R.id.topicDescribe)
        questionNum = findViewById(R.id.questionNum)
        beginBtn = findViewById(R.id.begin)

        val topicName = intent.getStringExtra("TOPIC_NAME")

//        val topicQuestionsCount = QuizData.topics.find { it.name == topicName }?.questions?.size ?: 0
        val topicQuestionsCount = topicName?.let { repo.getQuestionsnum(it) }


        // Initialize the scoreStatus to all false
        QuizData.scoreStatus = topicQuestionsCount?.let { MutableList(it) { false } }!!


//        topicDescribe.setText(QuizData.topics.find { it.name == topicName }?.description)
//        questionNum.setText((QuizData.topics.find { it.name == topicName }?.questions?.size ?: 0).toString() + " questions")

        topicDescribe.setText(repo.findTopicbyName(topicName)?.descriptionLong ?: "")
        questionNum.setText(topicQuestionsCount.toString() + " questions")

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