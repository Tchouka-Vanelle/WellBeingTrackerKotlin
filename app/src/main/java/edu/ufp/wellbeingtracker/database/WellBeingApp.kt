package edu.ufp.wellbeingtracker.database

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/*
* Extending Application:
*
* WellBeingApp inherits from the Application class, which is a global singleton in Android.
* It's created before any activity or service and is used to initialize app-wide resources.
*/
class WellBeingApp : Application() {

    // CoroutineScope for database operations
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Lazy initialization for the database and repository
    val database: AppDatabase by lazy {
        AppDatabase.getInstance(this, applicationScope)
    }
    val appRepository: MainRepository by lazy {
        MainRepository(
            database.userDAO(),
            database.questionnaireDAO(),
            database.questionQuestionnaireDAO(),
            database.typeAnswerDAO(),
            database.answerQuestionnaireDAO()
        )
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize ThreeTenABP for date/time support
        AndroidThreeTen.init(this)

    }
}
