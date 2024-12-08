package edu.ufp.wellbeingtracker.database

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/*
* Extending Application:

WellBeingApp inherits from the Application class, which is a global singleton in Android.
*It's created before any activity or service and is used to initialize app-wide resources.*/
class WellBeingApp : Application() {
    override fun onCreate(){
        super.onCreate()
        AndroidThreeTen.init(this)
    }
    // No need to cancel this scope since it will be killed with the process
    val applicationScope = CoroutineScope(SupervisorJob()  + Dispatchers.IO)

    val database by lazy { AppDatabase.getInstance(this, applicationScope) }

    /*by lazy ensures the repository is created only when it is first accessed,
    saving memory and startup time.*/
    val appRepository by lazy {
        MainRepository(
            database.userDAO(),
            database.questionnaireDAO(),
            database.questionQuestionnaireDAO(),
            database.typeAnswerDAO(),
            database.answerQuestionnaireDAO()
        )
    }
}