package edu.uw.ischool.mutiay.quizdroid

import Question
import QuizData
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.window.OnBackInvokedDispatcher
import kotlinx.coroutines.selects.select
import java.lang.Integer.max


class QuestionsActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
//    private var currentScore = 0
    private var questionSet = ""
//    private val hasAnsweredCorrectly = BooleanArray(3) { false }




    //Find elements
    private lateinit var question_text:TextView
    private lateinit var answer_one: RadioButton
    private lateinit var answer_two:RadioButton
    private lateinit var answer_three:RadioButton
    private lateinit var answer_four:RadioButton
    private lateinit var submit: Button
//    private lateinit var back: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        // find the items
        question_text = findViewById(R.id.question_text)
        answer_one = findViewById(R.id.answer_one)
        answer_two = findViewById(R.id.answer_two)
        answer_three = findViewById(R.id.answer_three)
        answer_four = findViewById(R.id.answer_four)
        submit = findViewById(R.id.button_submit)
//        back = findViewById(R.id.button_back)



        questionSet = intent.getStringExtra("TOPIC_NAME")?: ""
        currentQuestionIndex = intent.getIntExtra("CURRENT_INDEX", 0)
//        currentScore = intent.getIntExtra("CURRENT_SCORE",0)




        onAnswerSubmit(questionSet)
    }

    // Handles the back button
    override fun onBackPressed() {
        super.onBackPressed()
        if (currentQuestionIndex == 0) {
            val intent = Intent(this, MainActivity::class.java)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

            }
        } else {
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra("TOPIC_NAME", questionSet)

                putExtra("CURRENT_INDEX", currentQuestionIndex - 1)
                QuizData.scoreStatus[currentQuestionIndex - 1] = false
            }
            startActivity(intent)
        }
    }

    private fun onAnswerSubmit(questionSet:String?) {
        var answer = -1


        displaySelects(questionSet)



        // handle choice
        answer_one.setOnClickListener {
            answer = 0
            submit.isEnabled = true
        }
        answer_two.setOnClickListener {
            answer = 1
            submit.isEnabled = true
        }
        answer_three.setOnClickListener {
            answer = 2
            submit.isEnabled = true
        }
        answer_four.setOnClickListener {
            answer = 3
            submit.isEnabled = true
        }


        submit.setOnClickListener {

            val correctIndex = QuizData.topics.find{it.name == questionSet}?.questions?.get(currentQuestionIndex)?.correctAnswerIndex
            val userAnswerText = QuizData.topics.find{it.name == questionSet}?.questions?.get(currentQuestionIndex)?.options?.get(answer)
            val correctAnswerText = correctIndex?.let { it1 ->
                QuizData.topics.find{it.name == questionSet}?.questions?.get(currentQuestionIndex)?.options?.get(
                      it1
                  )
              }

            // Change the answer sheet
            if(answer == correctIndex) {
//            currentScore++
                QuizData.scoreStatus[currentQuestionIndex] = true

            }


            val intent = Intent(this, AnswersActivity::class.java).apply {
                putExtra("TOPIC_NAME", questionSet)
                putExtra("USER_ANSWER_TEXT", userAnswerText)
                putExtra("CORRECT_ANSWER_TEXT", correctAnswerText)
//                putExtra("CURRENT_SCORE", currentScore)
                putExtra("CURRENT_INDEX", currentQuestionIndex)

            }

            startActivity(intent)
        }

    }

    private fun displaySelects(questionSet:String?) {


        question_text.setText( QuizData.topics.find{it.name == questionSet}?.questions?.get(currentQuestionIndex)?.questionText)


        val listOfselects = QuizData.topics.find{it.name == questionSet}?.questions?.get(currentQuestionIndex)?.options
        if (listOfselects != null) {
            answer_one.setText(listOfselects.get(0))
            answer_two.setText(listOfselects.get(1))
            answer_three.setText(listOfselects.get(2))
            answer_four.setText(listOfselects.get(3))
        }

    }





}