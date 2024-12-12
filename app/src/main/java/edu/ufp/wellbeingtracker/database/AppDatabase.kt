package edu.ufp.wellbeingtracker.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.ufp.wellbeingtracker.database.entities.AnswerQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.QuestionQuestionnaire
import edu.ufp.wellbeingtracker.database.entities.Questionnaire
import edu.ufp.wellbeingtracker.database.entities.TypeAnswer
import edu.ufp.wellbeingtracker.database.entities.User
import edu.ufp.wellbeingtracker.database.entities_dao.AnswerQuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.QuestionQuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.QuestionnaireDAO
import edu.ufp.wellbeingtracker.database.entities_dao.TypeAnswerDAO
import edu.ufp.wellbeingtracker.database.entities_dao.UserDAO
import edu.ufp.wellbeingtracker.utils.functions.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        User::class,
        Questionnaire::class,
        QuestionQuestionnaire::class,
        TypeAnswer::class,
        AnswerQuestionnaire::class,
               ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun questionnaireDAO(): QuestionnaireDAO
    abstract fun questionQuestionnaireDAO(): QuestionQuestionnaireDAO
    abstract fun typeAnswerDAO(): TypeAnswerDAO
    abstract fun answerQuestionnaireDAO(): AnswerQuestionnaireDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppDatabase {
            //Singleton impl: if instance not null then return it, else create new instance
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wellbeing.db"
                )
                    .fallbackToDestructiveMigration() //to delete and recreate the bdd when version change
                    .addCallback(WellBeingDatabaseCallback(scope))

                    .build().also { INSTANCE = it }

            }

        }
    }
    private class WellBeingDatabaseCallback(private val scope: CoroutineScope) :
        Callback()
    {

        /** Override onOpen() to clear and populate DB every time app is started. */
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // To keep DB data through app restarts comment coroutine exec:
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    Log.d("DatabaseCallback", "Starting database seeding...")
                    DatabaseSeeder.preFillDatabase(
                        database.questionnaireDAO(),
                        database.questionQuestionnaireDAO(),
                        database.typeAnswerDAO()
                    )
                    Log.d("DatabaseCallback", "Database seeding completed.")
                }
            }
        }

        /** overrite onCreate() to populate DB only first time app is launched. */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //To clear and repopulate DB every time app is started comment coroutine exec:
            /*INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    Log.d("DatabaseCallback", "Starting database seeding...")
                    DatabaseSeeder.preFillDatabase(
                        database.questionnaireDAO(),
                        database.questionQuestionnaireDAO(),
                        database.typeAnswerDAO()
                    )
                    Log.d("DatabaseCallback", "Database seeding completed.")
                }
            }*/
        }
    }
}

