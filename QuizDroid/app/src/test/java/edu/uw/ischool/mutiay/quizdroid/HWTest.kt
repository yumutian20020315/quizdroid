package edu.uw.ischool.mutiay.quizdroid
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HWTest {
    private lateinit var repository: MockTopicRepository

    @Before
    fun setUp() {
        // Initialize the repository before each test
        repository = MockTopicRepository()
    }

    @Test
    fun testSaveTopic() {
        // Given
        val newTopic = Topic("Geography", "Short Description", "Long Description", mutableListOf(),iconId = android.R.drawable.sym_def_app_icon)

        // When
        repository.saveTopic(newTopic)

        // Then
        val retrievedTopic = repository.findTopicbyName("Geography")
        assertNotNull(retrievedTopic)
        assertEquals("Geography", retrievedTopic?.title)
    }

    @Test
    fun testFindTopicByName() {
        // Given
        // Repository is initialized with topics in setUp method

        // When
        val topic = repository.findTopicbyName("Math")

        // Then
        assertNotNull(topic)
        assertEquals("Math", topic?.title)
    }

    @Test
    fun testSaveQuizToTopic() {
        // Given
        val newQuiz = Quiz(
            "What is 9 plus 10?",
            listOf("19", "21", "20", "18"),
            0
        )
        val topicTitle = "Math"

        // When
        repository.saveQuiztoTopic(topicTitle, newQuiz)

        // Then
        val topic = repository.findTopicbyName(topicTitle)
        assertNotNull(topic)
        assertTrue(topic!!.quizzes.any { it.questionText == "What is 9 plus 10?" })
    }

    @Test
    fun testGetTopicsNames() {
        // Given
        // Repository is initialized with topics in setUp method

        // When
        val topicNames = repository.getTopicsnames()

        // Then
        assertNotNull(topicNames)
        assertTrue(topicNames.containsAll(listOf("Math", "Physics", "Marvel Super Heroes", "ice cream")))
    }

    @Test
    fun testGetQuestionsNum() {
        // Given
        val topicTitle = "Math"

        // When
        val numQuestions = repository.getQuestionsnum(topicTitle)

        // Then
        assertEquals(3, numQuestions) // Assuming there are 3 quizzes initially in "Math" topic
    }

    @Test
    fun testFindQuizByIndex() {
        // Given
        val topicTitle = "Physics"
        val quizIndex = 1 // Index of the second quiz

        // When
        val quiz = repository.findQuizbyIndex(topicTitle, quizIndex)

        // Then
        assertNotNull(quiz)
        assertEquals("A car travels 150 km in 3 hours. What is its average speed?", quiz?.questionText)
    }
}
