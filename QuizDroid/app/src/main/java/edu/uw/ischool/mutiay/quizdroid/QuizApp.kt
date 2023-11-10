package edu.uw.ischool.mutiay.quizdroid

import android.app.Application
import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import java.io.File

class QuizApp: Application() {
    private lateinit var repository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        repository = MockTopicRepository(this)
    }

    fun accessRepo(): TopicRepository {
        return repository
    }
}



interface TopicRepository {
    // basic operations
    fun saveTopic(newTopic: Topic):Unit
    fun saveQuiztoTopic(title: String, newQuiz: Quiz):Unit
    fun findTopicbyName(title: String): Topic?
    fun findQuizbyIndex(title: String, index: Int): Quiz?
    fun getAlltopics():MutableList<Topic>

    // my operations
    fun getTopicsnames(): List<String>
    fun getTitleswithDes(): List<String>
    fun getQuestionsnum(title: String): Int


}


//class MockTopicRepository: TopicRepository {
//    val items:MutableList<Topic> = mutableListOf(
//        Topic(
//            title = "Math",
//            descriptionLong = "Questions about addition",
//            descriptionShort = "Some quick questions on calculation",
//            quizzes = mutableListOf(
//                Quiz(
//                    questionText = "2 plus 3 equals what?",
//                    answers = listOf("5", "6", "7", "8"),
//                    correctAnswerIndex = 0
//                ),
//                Quiz(
//                    questionText = "5 plus 6 equals what?",
//                    answers = listOf("11", "12", "13", "14"),
//                    correctAnswerIndex = 0
//                ),
//                Quiz(
//                    questionText = "7 plus 8 equals what?",
//                    answers = listOf("14", "15", "16", "17"),
//                    correctAnswerIndex = 1
//                )
//            ),
//            iconId = android.R.drawable.ic_dialog_email
//        ),
//        Topic(
//            title = "Physics",
//            descriptionLong = "Question about speed and simple machine",
//            descriptionShort = "Some quick questions on basic physics",
//            quizzes = mutableListOf(
//                Quiz(
//                    questionText = "A 10 kg object is pushed with a force of 20 N. What is its acceleration?",
//                    answers = listOf("2 meters per second squared", "5 meters per second squared", "10 meters per second squared", "20 meters per second squared"),
//                    correctAnswerIndex = 0
//                ),
//                Quiz(
//                    questionText = "A car travels 150 km in 3 hours. What is its average speed?",
//                    answers = listOf("50 kilometers per hour", "60 kilometers per hour", "70 kilometers per hour", "80 kilometers per hour"),
//                    correctAnswerIndex = 0
//                ),
//                Quiz(
//                    questionText = "What type of simple machine is a seesaw?",
//                    answers = listOf("Lever", "Pulley", "Wheel and Axle", "Inclined Plane"),
//                    correctAnswerIndex = 0
//                )
//            ),
//            iconId = android.R.drawable.sym_def_app_icon
//        ),
//        Topic(
//            title = "Marvel Super Heroes",
//            descriptionLong = "Question about Marval Super Heros the famous ones",
//            descriptionShort = "Some quick questions on Marval Super Heros",
//            quizzes = mutableListOf(
//                Quiz(
//                    questionText = "What is the source of the Hulk's strength?",
//                    answers = listOf("Gamma Radiation", "Super-Soldier Serum", "Alien Technology", "Mystical Enchantment"),
//                    correctAnswerIndex = 0
//                ),
//                Quiz(
//                    questionText = "Which group of heroes does Iron Man belong to?",
//                    answers = listOf("Justice League", "X-Men", "Avengers", "Teen Titans"),
//                    correctAnswerIndex = 2
//                )
//            ),
//            iconId = android.R.drawable.arrow_down_float
//        ),
//        Topic(
//            title = "ice cream",
//            descriptionLong = "Questions about ice cream falvors",
//            descriptionShort = "Some quick questions on ice cream",
//            quizzes = mutableListOf(
//                Quiz(
//                    questionText = "Which ice cream is white color",
//                    answers = listOf("Chocolate", "Strawberry", "Vanilla", "Mint Chocolate Chip"),
//                    correctAnswerIndex = 2
//                ),
//                Quiz(
//                    questionText = "Which ice cream is green color",
//                    answers = listOf("Vanilla", "Mint", "Orange", "Chocolate"),
//                    correctAnswerIndex = 1
//                )
//            ),
//            iconId = android.R.drawable.arrow_up_float
//        )
//    )
//
//    override fun saveTopic(newTopic: Topic) {
//        items.add(newTopic)
//    }
//
//    override fun saveQuiztoTopic(title: String, newQuiz: Quiz) {
//        items.find { it.title == title }?.quizzes?.add(newQuiz)
//    }
//
//    override fun getTopicsnames(): List<String> {
//        return items.map { it.title }
//    }
//
//    override fun getTitleswithDes(): List<String> {
//        return items.map { it.title + ": " + it.descriptionShort}
//    }
//
//    override fun getQuestionsnum(title: String): Int {
//        val topic = items.find { it.title == title }
//
//        return topic?.quizzes?.size ?: 0
//    }
//
//    override fun findTopicbyName(title: String): Topic? {
//        return items.find { it.title == title }
//    }
//
//    override fun findQuizbyIndex(title: String, index: Int): Quiz? {
//        return findTopicbyName(title)?.quizzes?.get(index)
//    }
//
//    override fun getAlltopics(): MutableList<Topic> {
//        return items
//    }
//}




class MockTopicRepository(private val context: Context): TopicRepository {
    val items:MutableList<Topic> = mutableListOf(
        Topic(
            title = "Math",
            descriptionLong = "Questions about addition",
            descriptionShort = "Some quick questions on calculation",
            quizzes = mutableListOf(
                Quiz(
                    questionText = "2 plus 3 equals what?",
                    answers = listOf("5", "6", "7", "8"),
                    correctAnswerIndex = 0
                ),
                Quiz(
                    questionText = "5 plus 6 equals what?",
                    answers = listOf("11", "12", "13", "14"),
                    correctAnswerIndex = 0
                ),
                Quiz(
                    questionText = "7 plus 8 equals what?",
                    answers = listOf("14", "15", "16", "17"),
                    correctAnswerIndex = 1
                )
            ),
            iconId = android.R.drawable.ic_dialog_email
        ),
        Topic(
            title = "Physics",
            descriptionLong = "Question about speed and simple machine",
            descriptionShort = "Some quick questions on basic physics",
            quizzes = mutableListOf(
                Quiz(
                    questionText = "A 10 kg object is pushed with a force of 20 N. What is its acceleration?",
                    answers = listOf("2 meters per second squared", "5 meters per second squared", "10 meters per second squared", "20 meters per second squared"),
                    correctAnswerIndex = 0
                ),
                Quiz(
                    questionText = "A car travels 150 km in 3 hours. What is its average speed?",
                    answers = listOf("50 kilometers per hour", "60 kilometers per hour", "70 kilometers per hour", "80 kilometers per hour"),
                    correctAnswerIndex = 0
                ),
                Quiz(
                    questionText = "What type of simple machine is a seesaw?",
                    answers = listOf("Lever", "Pulley", "Wheel and Axle", "Inclined Plane"),
                    correctAnswerIndex = 0
                )
            ),
            iconId = android.R.drawable.sym_def_app_icon
        ),
        Topic(
            title = "Marvel Super Heroes",
            descriptionLong = "Question about Marval Super Heros the famous ones",
            descriptionShort = "Some quick questions on Marval Super Heros",
            quizzes = mutableListOf(
                Quiz(
                    questionText = "What is the source of the Hulk's strength?",
                    answers = listOf("Gamma Radiation", "Super-Soldier Serum", "Alien Technology", "Mystical Enchantment"),
                    correctAnswerIndex = 0
                ),
                Quiz(
                    questionText = "Which group of heroes does Iron Man belong to?",
                    answers = listOf("Justice League", "X-Men", "Avengers", "Teen Titans"),
                    correctAnswerIndex = 2
                )
            ),
            iconId = android.R.drawable.arrow_down_float
        ),
        Topic(
            title = "ice cream",
            descriptionLong = "Questions about ice cream falvors",
            descriptionShort = "Some quick questions on ice cream",
            quizzes = mutableListOf(
                Quiz(
                    questionText = "Which ice cream is white color",
                    answers = listOf("Chocolate", "Strawberry", "Vanilla", "Mint Chocolate Chip"),
                    correctAnswerIndex = 2
                ),
                Quiz(
                    questionText = "Which ice cream is green color",
                    answers = listOf("Vanilla", "Mint", "Orange", "Chocolate"),
                    correctAnswerIndex = 1
                )
            ),
            iconId = android.R.drawable.arrow_up_float
        )
    )

    init {
        loadTopicsFromJsonFile("questions.json")
    }

    private fun loadTopicsFromJsonFile(fileName: String) {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            val jsonString = file.readText(Charsets.UTF_8)
            // Clear the hardcoded list
            items.clear()
            // Parse the JSON string and add to 'items'
            parseTopics(jsonString).let {
                items.addAll(it)
            }
        } else {
            Log.e("MockTopicRepository", "File does not exist: $fileName. Using hardcoded data.")
        }
    }

    private fun parseTopics(jsonString: String): List<Topic> {
        val jsonArray = JSONArray(jsonString)
        return List(jsonArray.length()) { index ->
            jsonArray.getJSONObject(index).let { jsonObject ->
                Topic(
                    title = jsonObject.getString("title"),
                    descriptionShort = jsonObject.getString("desc"),
                    descriptionLong = jsonObject.getString("desc"), // 如果您有长描述，应该使用它
                    quizzes = parseQuizzes(jsonObject.getJSONArray("questions")),
                    iconId = android.R.drawable.ic_dialog_email // 您需要为每个主题提供一个图标资源
                )
            }
        }
    }

    private fun parseQuizzes(jsonArray: JSONArray): MutableList<Quiz> {
        val quizzes = mutableListOf<Quiz>()
        for (i in 0 until jsonArray.length()) {
            val questionObject = jsonArray.getJSONObject(i)
            val questionText = questionObject.getString("text")
            val answerIndex = questionObject.getString("answer").toInt() // 确保 answer 字段是字符串类型的数字
            val answersJsonArray = questionObject.getJSONArray("answers")
            val answers = mutableListOf<String>()
            for (j in 0 until answersJsonArray.length()) {
                answers.add(answersJsonArray.getString(j))
            }
            quizzes.add(Quiz(questionText, answers, answerIndex))
        }
        return quizzes
    }



    // ... rest of the methods
    override fun saveTopic(newTopic: Topic) {
        items.add(newTopic)
    }

    override fun saveQuiztoTopic(title: String, newQuiz: Quiz) {
        items.find { it.title == title }?.quizzes?.add(newQuiz)
    }

    override fun getTopicsnames(): List<String> {
        return items.map { it.title }
    }

    override fun getTitleswithDes(): List<String> {
        return items.map { it.title + ": " + it.descriptionShort}
    }

    override fun getQuestionsnum(title: String): Int {
        val topic = items.find { it.title == title }

        return topic?.quizzes?.size ?: 0
    }

    override fun findTopicbyName(title: String): Topic? {
        return items.find { it.title == title }
    }

    override fun findQuizbyIndex(title: String, index: Int): Quiz? {
        return findTopicbyName(title)?.quizzes?.get(index)
    }

    override fun getAlltopics(): MutableList<Topic> {
        return items
    }
}


// Models for Quiz and Topic
data class Quiz(val questionText: String,
                val answers: List<String>,
                val correctAnswerIndex: Int)

data class Topic(val title: String,
                 val descriptionShort: String,
                 val descriptionLong: String,
                val quizzes: MutableList<Quiz>,
                val iconId: Int)