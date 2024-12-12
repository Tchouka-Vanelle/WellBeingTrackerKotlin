package edu.ufp.wellbeingtracker.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.ufp.wellbeingtracker.utils.models.QuestionnaireWithQuestions
import edu.ufp.wellbeingtracker.database.entities.AnswerQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.User
import edu.ufp.wellbeingtracker.utils.functions.hashPassword
import edu.ufp.wellbeingtracker.utils.functions.verifyPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Date

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val questionnaireWithQuestions = MutableLiveData<List<QuestionnaireWithQuestions>>()


    fun fetchQuestionnaires() {
        viewModelScope.launch(Dispatchers.IO) {
            val questionnaires = repository.getAllQuestionnaires()
            Log.d("MainViewModel", "Fetched questionnaires: $questionnaires")

            val questionnairesWithQuestions = mutableListOf<QuestionnaireWithQuestions>()
            for (questionnaire in questionnaires) {
                val questions = repository.getAllQuestionsForQuestionnaire(questionnaire.id)
                Log.d("MainViewModel", "Questions for questionnaire ${questionnaire.id}: $questions")
                questionnairesWithQuestions.add(QuestionnaireWithQuestions(questionnaire, questions))
            }

            if (questionnairesWithQuestions.isEmpty()) {
                Log.d("MainViewModel", "No questionnaires with questions found")
            }

            questionnaireWithQuestions.postValue(questionnairesWithQuestions)
        }
    }


    fun registerUser(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUserByName(username)
            val userId = user?.let {
                if (verifyPassword(password, it.hashedPassword)) it.id else 0
            } ?: 0
            onLoginResult(userId)
        }
    }

    fun saveAnswer(userId: Int, questionId: Int, typeAnswerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.insertAnswer(userId, questionId, typeAnswerId)
        }
    }

    fun loadMeanings(onResult: (List<String>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val meanings = repository.getAllMeanings()
            onResult(meanings)
        }
    }

    fun countAllQuestionsForQuestionnaire(questionnaireId: Int, onResult: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.countAllQuestionsForQuestionnaire(questionnaireId)
            onResult(result)
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