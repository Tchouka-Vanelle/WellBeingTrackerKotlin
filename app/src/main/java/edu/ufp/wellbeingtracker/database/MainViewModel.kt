package edu.ufp.wellbeingtracker.database

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.ufp.wellbeingtracker.activities.QuestionnaireWithQuestions
import edu.ufp.wellbeingtracker.database.entities.AnswerQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire
import edu.ufp.wellbeingtracker.database.entities.TypeAnswer
import edu.ufp.wellbeingtracker.database.entities.User
import edu.ufp.wellbeingtracker.utils.hashPassword
import edu.ufp.wellbeingtracker.utils.verifyPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlinx.coroutines.runBlocking
import java.sql.Date

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val questionnaireWithQuestions = MutableLiveData<List<QuestionnaireWithQuestions>>()

    fun fetchQuestionnaires() {
        viewModelScope.launch {
            val questionnaires = repository.getAllQuestionnaires()
            val questionnairesWithQuestions = mutableListOf<QuestionnaireWithQuestions>()
            for (questionnaire in questionnaires) {
                val questions = repository.getAllQuestionsForQuestionnaire(questionnaire.id)
                questionnairesWithQuestions.add(QuestionnaireWithQuestions(questionnaire, questions))
            }
            questionnaireWithQuestions.postValue(questionnairesWithQuestions)  // Set the LiveData value
        }
    }

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
            val currentDateTime = Date(System.currentTimeMillis())

            // Create AnswerQuestionnaire object
            val answer = AnswerQuestionnaire(
                idQuestionQuestionnaire = questionId,
                idUser = userId,
                idTypeAnswer = typeAnswerId,
                dateTimeAnswer = currentDateTime // Store the formatted date-time
            )

            // Insert answer into database
            repository.insertAnswer(answer)

        }
    }



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