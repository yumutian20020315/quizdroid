package edu.uw.ischool.mutiay.quizdroid

import QuizData
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AnswersActivity : AppCompatActivity() {


    private lateinit var answerMaybe:TextView
    private lateinit var correctAnswer:TextView
    private lateinit var summary:TextView
    private lateinit var btn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)

        // Get Repo in
        val repo = (application as QuizApp).accessRepo()

        answerMaybe = findViewById(R.id.answerMaybe_text)
        correctAnswer = findViewById(R.id.correctAnswer_text)
        summary = findViewById(R.id.summary_text)
        btn = findViewById(R.id.nextOrfinish)




        val topic = intent.getStringExtra("TOPIC_NAME")
        val userAnswer = intent.getStringExtra("USER_ANSWER_TEXT")
        val correct = intent.getStringExtra("CORRECT_ANSWER_TEXT")
        val currentIndex = intent.getIntExtra("CURRENT_INDEX",0)




        //Deal with the change of btn
//        val isLastQuestion = currentIndex == (QuizData.topics.find { it.name == topic }?.questions?.size?.minus(
//            1
//        ))
        val questionNums = topic?.let { repo.getQuestionsnum(it) }

        val isLastQuestion = currentIndex == questionNums?.minus(1) ?: 0



        if (isLastQuestion) {

            btn.setText("Finish")
        } else {

            btn.setText("Next")
        }



        answerMaybe.setText("Your answer: " + userAnswer)
        correctAnswer.setText("Correct Answer: " + correct)

        // count score in answer sheet
        var score = 0;
        for (i in QuizData.scoreStatus.indices) {
            if (i > currentIndex) break


            val status = QuizData.scoreStatus[i]

            if (status) {
                score++
            }
        }

        summary.setText("You have " + score + " out of " + questionNums + " correct")

        btn.setOnClickListener {

            if (isLastQuestion) {

                val intent = Intent(this, MainActivity::class.java)
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {

                }
            } else {

                val intent = Intent(this, QuestionsActivity::class.java).apply {
                    putExtra("TOPIC_NAME", topic)
                    putExtra("CURRENT_INDEX", currentIndex + 1)

                }

                startActivity(intent)
            }
        }
    }
}