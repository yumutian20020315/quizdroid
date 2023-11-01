object QuizData {
    val topics = listOf(
        Topic(
            name = "Math",
            description = "Some quick questions on calculation",
            questions = listOf(
                Question(
                    questionText = "2 plus 3 equals what?",
                    options = listOf("5", "6", "7", "8"),
                    correctAnswerIndex = 0
                ),
                Question(
                    questionText = "5 plus 6 equals what?",
                    options = listOf("11", "12", "13", "14"),
                    correctAnswerIndex = 0
                ),
                Question(
                    questionText = "7 plus 8 equals what?",
                    options = listOf("14", "15", "16", "17"),
                    correctAnswerIndex = 1
                )
            )
        ),
        Topic(
            name = "Physics",
            description = "Some quick questions on basic physics",
            questions = listOf(
                Question(
                    questionText = "A 10 kg object is pushed with a force of 20 N. What is its acceleration?",
                    options = listOf("2 meters per second squared", "5 meters per second squared", "10 meters per second squared", "20 meters per second squared"),
                    correctAnswerIndex = 0
                ),
                Question(
                    questionText = "A car travels 150 km in 3 hours. What is its average speed?",
                    options = listOf("50 kilometers per hour", "60 kilometers per hour", "70 kilometers per hour", "80 kilometers per hour"),
                    correctAnswerIndex = 0
                ),
                Question(
                    questionText = "What type of simple machine is a seesaw?",
                    options = listOf("Lever", "Pulley", "Wheel and Axle", "Inclined Plane"),
                    correctAnswerIndex = 0
                )
            )
        ),
        Topic(
            name = "Marvel Super Heroes",
            description = "Some quick questions on Marval Super Heros",
            questions = listOf(
                Question(
                    questionText = "What is the source of the Hulk's strength?",
                    options = listOf("Gamma Radiation", "Super-Soldier Serum", "Alien Technology", "Mystical Enchantment"),
                    correctAnswerIndex = 0
                ),
                Question(
                    questionText = "Which group of heroes does Iron Man belong to?",
                    options = listOf("Justice League", "X-Men", "Avengers", "Teen Titans"),
                    correctAnswerIndex = 2
                )
            )
        ),
        Topic(
            name = "ice cream",
            description = "Some quick questions on ice cream",
            questions = listOf(
                Question(
                    questionText = "Which ice cream is white color",
                    options = listOf("Chocolate", "Strawberry", "Vanilla", "Mint Chocolate Chip"),
                    correctAnswerIndex = 2
                ),
                Question(
                    questionText = "Which ice cream is green color",
                    options = listOf("Vanilla", "Mint", "Orange", "Chocolate"),
                    correctAnswerIndex = 1
                )
            )
        )
    )

    var scoreStatus = mutableListOf(false, false, false)
}