package edu.ufp.wellbeingtracker.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.ufp.wellbeingtracker.database.entities.AnswerQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire
import edu.ufp.wellbeingtracker.database.entities.TypeAnswer
import edu.ufp.wellbeingtracker.database.entities.User
import edu.ufp.wellbeingtracker.utils.hashPassword
import edu.ufp.wellbeingtracker.utils.verifyPassword
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun registerUser(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val hashedPassword = hashPassword(password)
            val existingUser = repository.getUserByName(username)

            if(existingUser == null) {
                val newUser = User(name = username, hashedPassword =  hashedPassword)
                repository.insertUser(newUser)
                onResult(true) // registration successful
            } else {
                onResult(false) //Username already exists
            }
        }
    }

    fun loginUser(username: String, password: String, onLoginResult: (Int) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByName(username)
            val userId = user?.let {
                if (verifyPassword(password, it.hashedPassword)) it.id else 0
            } ?: 0
            onLoginResult(userId)
        }
    }

    fun saveAnswer(userId: Int, questionId: Int, typeAnswerId: Int) {
        viewModelScope.launch {
            /*val currentDateTime = LocalDateTime.now()

            // Create AnswerQuestionnaire object
            val answer = AnswerQuestionnaire(
                id = 0, // Let Room auto-generate
                idQuestionQuestionnaire = questionId,
                idUser = userId,
                idTypeAnswer = typeAnswerId,
                dateTimeAnswer = currentDateTime // Store the formatted date-time
            )

            // Insert answer into database
            repository.insertAnswer(answer)*/

        }
    }

    /*fun preFillDatabase() {
        viewModelScope.launch {

            AppDatabase.INSTANCE?.runInTransaction {
                // Pre-fill questionnaires
                repository.insertQuestionnaires(
                    Questionnaire(1, "SLEEP", true, false),
                    Questionnaire(2, "WELLBEING1", true, false),
                    Questionnaire(3, "WELLBEING2", true, true)
                )

                // Pre-fill questions
                repository.insertQuestions(
                    QuestionQuestionnaire(
                        1,
                        1,
                        "I slept very well and feel that my sleep was totally restorative. "
                    ),
                    QuestionQuestionnaire(2, 1, "I feel totally rested after this night's sleep."),
                    QuestionQuestionnaire(3, 2, "I related easily to the people around me."),
                    QuestionQuestionnaire(
                        4,
                        2,
                        "I was able to face difficult situations in a positive way."
                    ),
                    QuestionQuestionnaire(5, 2, "I felt that others liked me and appreciated me."),
                    QuestionQuestionnaire(
                        6,
                        2,
                        "I felt satisfied with what I was able to achieve, I felt proud of myself."
                    ),
                    QuestionQuestionnaire(
                        7,
                        2,
                        "My life was well balanced between my family, personal and academic activities."
                    ),
                    QuestionQuestionnaire(8, 3, "I felt emotionally balanced."),
                    QuestionQuestionnaire(9, 3, "I felt good, at peace with myself"),
                    QuestionQuestionnaire(10, 3, "I felt confident.")
                )

                // Pre-fill type answers
                repository.insertTypeAnswers(
                    TypeAnswer(1, 1, "DISAGREE"),
                    TypeAnswer(2, 2, "EITHER DISAGREE NOR AGREE"),
                    TypeAnswer(3, 3, "AGREE")
                )
            }
        }
    }*/
}

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}